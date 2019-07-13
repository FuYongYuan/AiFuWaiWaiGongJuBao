package schedule.service;

import schedule.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

abstract class ScheduleState<T extends Schedule> {

    public DateNumber getNextTime(DateNumber time) {

        setDataNumberDefComponent(time.def);

        if (states.size() == 0) {
            return adjustTime(time);
        }


        DateNumber result = null;

        for (ScheduleState<?> s : states) {
            DateNumber t = adjustTime(time);
            DateNumber desireTime = s.getNextTime(t);
            if (result == null || (desireTime != null && desireTime.toLocalDateTime().isBefore(result.toLocalDateTime()))) {
                result = desireTime;
            }
        }

        return result;
    }


    protected abstract DateNumber adjustTime(DateNumber time);

    public abstract void setDataNumberDefComponent(DateNumberDef def);


    protected void onRun(LocalDateTime now) {
    }


    public void notifyRun(LocalDateTime now) {

        onRun(now);

        for (ScheduleState<?> s : states) {
            s.notifyRun(now);
        }
    }


    public T schedule;

    public LocalDateTime desireTime;

    public Job job;


    private ArrayList<ScheduleState<?>> states = new ArrayList<>();

    public void addState(ScheduleState<?> state) {
        states.add(state);
    }


    @SuppressWarnings("unchecked")
    private void setSchedule(Schedule schedule) {
        this.schedule = (T) schedule;
    }


    public static ScheduleState<?> create(Schedule schedule, Job job) {

        ScheduleState<?> state = null;

        if (schedule instanceof Second) {
            state = new SecondScheduleState();
        } else if (schedule instanceof Minute) {
            state = new MinuteScheduleState();
        } else if (schedule instanceof Hour) {
            state = new HourScheduleState();
        } else if (schedule instanceof WeekDay) {
            state = new WeekDayScheduleState();
        } else if (schedule instanceof MonthDay) {
            state = new MonthDayScheduleState();
        } else if (schedule instanceof Month) {
            state = new MonthScheduleState();
        } else if (schedule instanceof Timer) {
            state = new RecurringScheduleState();
        }


        for (Schedule s : schedule.getSchedules()) {
            state.addState(create(s, job));
        }


        state.setSchedule(schedule);
        state.job = job;

        return state;
    }
}
