package schedule.service;

import schedule.Job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ExecutorLoop {

    private static int POLL_TIME = 10 * 60 * 1000;


    private ExecutorService loopExecutor;
    private ExecutorService workersExecutor;

    private Object locker = new Object();


    public ExecutorLoop(ScheduleContext scheduleContext) {
        this.scheduleContext = scheduleContext;
    }


    private ScheduleContext scheduleContext;


    public void start() {

        setStarted(true);

        loopExecutor = Executors.newSingleThreadExecutor();
        workersExecutor = Executors.newCachedThreadPool();

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

    private ArrayList<ScheduleState<?>> schedules;


    private boolean isWaitingInvalidated;


    private void performExecutorLoop() {

        while (isStarted()) {

            ArrayList<ScheduleState<?>> schedules;

            synchronized (locker) {

                while (true) {

                    while (isWaitingInvalidated) {

                        isWaitingInvalidated = false;


                        long sleepTime;

                        if (wakeupTime == null) {
                            sleepTime = POLL_TIME;
                        } else {
                            LocalDateTime now = LocalDateTime.now();
                            if (now.isBefore(wakeupTime)) {
                                sleepTime = Math.min(Helper.getMilliseconds(Duration.between(now, wakeupTime)), POLL_TIME);
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

                    if (!LocalDateTime.now().isBefore(wakeupTime)) {
                        break;
                    }
                }

                schedules = this.schedules;
            }

            runActions(schedules);

            scheduleContext.updateNextJob();

            wakeupTime = scheduleContext.getNextTime();
            this.schedules = scheduleContext.getSchedules();

            isWaitingInvalidated = true;
        }
    }

    private void runActions(ArrayList<ScheduleState<?>> schedules) {

        LocalDateTime now = LocalDateTime.now();

        for (ScheduleState<?> schedule : schedules) {
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
                action = (Runnable) job.getActionClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return action;
    }


    public void scheduleNextTime(LocalDateTime time, ArrayList<ScheduleState<?>> schedules) {

        synchronized (locker) {

            wakeupTime = time;
            this.schedules = schedules;

            isWaitingInvalidated = true;
            locker.notify();
        }
    }
}
