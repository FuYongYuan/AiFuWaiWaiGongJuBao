package schedule.service;

import schedule.AbstractSchedule;
import schedule.Job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 调度内容
 *
 * @author fyy
 */
class ScheduleContext {


    private final ArrayList<Job> jobs = new ArrayList<>();

    public void addJob(Job job) {

        jobs.add(job);

        for (AbstractSchedule s : job.getSchedules()) {
            AbstractScheduleState<?> state = AbstractScheduleState.create(s, job);
            scheduleStateMapping.put(s, state);
            abstractScheduleStates.add(state);
        }

        updateNextJob();
    }

    public void removeJob(Job job) {

        jobs.remove(job);

        for (AbstractSchedule s : job.getSchedules()) {
            AbstractScheduleState<?> state = scheduleStateMapping.get(s);
            scheduleStateMapping.remove(s);
            abstractScheduleStates.remove(state);
        }

        updateNextJob();
    }

    public void clearJobs() {

        jobs.clear();

        scheduleStateMapping.clear();
        abstractScheduleStates.clear();


        updateNextJob();
    }


    private LocalDateTime nextTime;

    public LocalDateTime getNextTime() {
        return nextTime;
    }

    private ArrayList<AbstractScheduleState<?>> schedules;

    public ArrayList<AbstractScheduleState<?>> getSchedules() {
        return schedules;
    }


    private final HashMap<AbstractSchedule, AbstractScheduleState<?>> scheduleStateMapping = new HashMap<>();

    private final ArrayList<AbstractScheduleState<?>> abstractScheduleStates = new ArrayList<>();


    public void updateNextJob() {

        LocalDateTime finalTime = null;
        LocalDateTime nowDate = LocalDateTime.now().plusNanos(1000000);

        for (Job agenda : jobs) {

            DateNumber now = DateNumber.fromLocalDateTime(nowDate);

            for (AbstractSchedule s : agenda.getSchedules()) {

                AbstractScheduleState<?> state = scheduleStateMapping.get(s);

                LocalDateTime time = state.getNextTime(now).toLocalDateTime();
                state.desireTime = time;

                boolean exist = time != null && (finalTime == null || finalTime.isAfter(time));
                if (exist) {
                    finalTime = time;
                }
            }
        }


        ArrayList<AbstractScheduleState<?>> schedules = new ArrayList<>();

        if (finalTime != null) {

            for (Job job : jobs) {
                for (AbstractSchedule s : job.getSchedules()) {

                    AbstractScheduleState<?> state = scheduleStateMapping.get(s);

                    if (finalTime.equals(state.desireTime)) {
                        schedules.add(state);
                    }
                }
            }
        }


        nextTime = finalTime;
        this.schedules = schedules;
    }
}
