package schedule.service;

import schedule.Job;

/**
 * 调度任务
 *
 * @author fyy
 */
public class JobService {

    private boolean isStarted;

    private final ScheduleContext scheduleContext;

    private ExecutorLoop loop;


    public JobService() {
        scheduleContext = new ScheduleContext();
    }


    public void start() {

        synchronized (this) {

            if (isStarted) {
                return;
            }


            isStarted = true;

            loop = new ExecutorLoop(scheduleContext);
            loop.start();

            setNextJobs();
        }
    }

    public void stop() {

        synchronized (this) {
            isStarted = false;
            loop.stop();
        }
    }

    public void addJob(Job job) {
        synchronized (this) {
            scheduleContext.addJob(job);
            setNextJobs();
        }
    }

    public void removeJob(Job job) {
        synchronized (this) {
            scheduleContext.removeJob(job);
            setNextJobs();
        }
    }

    public void clearJobs() {
        synchronized (this) {
            scheduleContext.clearJobs();
            setNextJobs();
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    private void setNextJobs() {
        if (isStarted) {
            loop.scheduleNextTime(scheduleContext.getNextTime(), scheduleContext.getSchedules());
        }
    }
}
