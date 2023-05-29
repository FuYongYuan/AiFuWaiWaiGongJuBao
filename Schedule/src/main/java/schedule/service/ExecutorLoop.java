package schedule.service;

import schedule.Job;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 核心执行类
 *
 * @author fyy
 */
class ExecutorLoop {


    private ExecutorService loopExecutor;
    private ExecutorService workersExecutor;

    private final Object locker = new Object();


    public ExecutorLoop(ScheduleContext scheduleContext) {
        this.scheduleContext = scheduleContext;
    }


    private final ScheduleContext scheduleContext;


    public void start() {

        setStarted(true);

        loopExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        workersExecutor = new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        loopExecutor.execute(() -> {
            performExecutorLoop();
            loopExecutor.shutdown();
            workersExecutor.shutdown();
        });
    }

    public void stop() {
        setStarted(false);
    }


    private boolean isStarted;

    private synchronized boolean isStarted() {
        return isStarted;
    }

    public synchronized void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }


    private LocalDateTime wakeupTime;

    private ArrayList<AbstractScheduleState<?>> schedules;


    private boolean isWaitingInvalidated;


    private void performExecutorLoop() {

        while (isStarted()) {

            ArrayList<AbstractScheduleState<?>> schedules;

            synchronized (locker) {

                do {

                    while (isWaitingInvalidated) {

                        isWaitingInvalidated = false;


                        long sleepTime;

                        int pollTime = 10 * 60 * 1000;
                        if (wakeupTime == null) {
                            sleepTime = pollTime;
                        } else {
                            LocalDateTime now = LocalDateTime.now();
                            if (now.isBefore(wakeupTime)) {
                                sleepTime = Math.min(Helper.getMilliseconds(Duration.between(now, wakeupTime)), pollTime);
                            } else {
                                sleepTime = 0;
                            }
                        }

                        if (sleepTime > 0 && sleepTime < 100) {
                            sleepTime = 100;
                        }

                        if (sleepTime > 0) {
                            try {
                                locker.wait(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } while (LocalDateTime.now().isBefore(wakeupTime));

                schedules = this.schedules;
            }

            runActions(schedules);

            scheduleContext.updateNextJob();

            wakeupTime = scheduleContext.getNextTime();
            this.schedules = scheduleContext.getSchedules();

            isWaitingInvalidated = true;
        }
    }

    private void runActions(ArrayList<AbstractScheduleState<?>> schedules) {

        LocalDateTime now = LocalDateTime.now();

        for (AbstractScheduleState<?> schedule : schedules) {
            schedule.notifyRun(now);
            workersExecutor.execute(getAction(schedule.job));
        }
    }


    private Runnable getAction(Job job) {

        Runnable action = null;

        if (job.getAction() != null) {
            action = job.getAction();
        } else {
            try {
                action = (Runnable) job.getActionClass().getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                     IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return action;
    }


    public void scheduleNextTime(LocalDateTime time, ArrayList<AbstractScheduleState<?>> schedules) {

        synchronized (locker) {

            wakeupTime = time;
            this.schedules = schedules;

            isWaitingInvalidated = true;
            locker.notify();
        }
    }
}
