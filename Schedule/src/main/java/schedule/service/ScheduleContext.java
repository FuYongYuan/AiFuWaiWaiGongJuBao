package schedule.service;

import schedule.Job;
import schedule.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

class ScheduleContext {


    private ArrayList<Job> jobs = new ArrayList<>();

    public void addJob(Job job) {

        jobs.add(job);

        for (Schedule s : job.getSchedules()) {
            ScheduleState<?> state = ScheduleState.create(s, job);
            scheduleStateMapping.put(s, state);
            scheduleStates.add(state);
        }

        updateNextJob();
    }

    public void removeJob(Job job) {

        jobs.remove(job);

        for (Schedule s : job.getSchedules()) {
            ScheduleState<?> state = scheduleStateMapping.get(s);
            scheduleStateMapping.remove(s);
            scheduleStates.remove(state);
        }

        updateNextJob();
    }

    public void clearJobs() {

        jobs.clear();

        scheduleStateMapping.clear();
        scheduleStates.clear();


        updateNextJob();
    }


    private LocalDateTime nextTime;

    public LocalDateTime getNextTime() {
        return nextTime;
    }

    private ArrayList<ScheduleState<?>> schedules;

    public ArrayList<ScheduleState<?>> getSchedules() {
        return schedules;
    }


    private HashMap<Schedule, ScheduleState<?>> scheduleStateMapping = new HashMap<>();

    private ArrayList<ScheduleState<?>> scheduleStates = new ArrayList<>();


    public void updateNextJob() {

        LocalDateTime finalTime = null;
        LocalDateTime nowDate = LocalDateTime.now().plusNanos(1000000);

        for (Job agenda : jobs) {

            DateNumber now = DateNumber.fromLocalDateTime(nowDate);

            for (Schedule s : agenda.getSchedules()) {

                ScheduleState<?> state = scheduleStateMapping.get(s);

                LocalDateTime time = state.getNextTime(now).toLocalDateTime();
                state.desireTime = time;

                if (time != null && (finalTime == null || finalTime.isAfter(time))) {
                    finalTime = time;
                }
            }
        }


        ArrayList<ScheduleState<?>> schedules = new ArrayList<>();

        if (finalTime != null) {

            for (Job job : jobs) {
                for (Schedule s : job.getSchedules()) {

                    ScheduleState<?> state = scheduleStateMapping.get(s);

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
