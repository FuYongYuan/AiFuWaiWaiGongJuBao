package schedule.service;

import schedule.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 抽象计划状态类
 *
 * @param <T> AbstractSchedule
 */
abstract class AbstractScheduleState<T extends AbstractSchedule> {

    public DateNumber getNextTime(DateNumber time) {

        setDataNumberDefComponent(time.def);

        if (states.size() == 0) {
            return adjustTime(time);
        }


        DateNumber result = null;

        for (AbstractScheduleState<?> s : states) {
            DateNumber t = adjustTime(time);
            DateNumber desireTime = s.getNextTime(t);
            boolean exist = result == null || (desireTime != null && desireTime.toLocalDateTime().isBefore(result.toLocalDateTime()));
            if (exist) {
                result = desireTime;
            }
        }

        return result;
    }

    /**
     * 调整时间
     *
     * @param time 时间
     * @return 调整后时间
     */
    protected abstract DateNumber adjustTime(DateNumber time);

    /**
     * 设置默认时间范围
     *
     * @param def 默认时间范围
     */
    public abstract void setDataNumberDefComponent(DateNumberDef def);


    protected void onRun(LocalDateTime now) {
    }


    public void notifyRun(LocalDateTime now) {

        onRun(now);

        for (AbstractScheduleState<?> s : states) {
            s.notifyRun(now);
        }
    }


    public T schedule;

    public LocalDateTime desireTime;

    public Job job;


    private final ArrayList<AbstractScheduleState<?>> states = new ArrayList<>();

    public void addState(AbstractScheduleState<?> state) {
        states.add(state);
    }


    @SuppressWarnings("unchecked")
    private void setSchedule(AbstractSchedule abstractSchedule) {
        this.schedule = (T) abstractSchedule;
    }


    public static AbstractScheduleState<?> create(AbstractSchedule abstractSchedule, Job job) {

        AbstractScheduleState<?> state = null;

        if (abstractSchedule instanceof Second) {
            state = new SecondAbstractScheduleState();
        } else if (abstractSchedule instanceof Minute) {
            state = new MinuteAbstractScheduleState();
        } else if (abstractSchedule instanceof Hour) {
            state = new HourAbstractScheduleState();
        } else if (abstractSchedule instanceof WeekDay) {
            state = new WeekDayAbstractScheduleState();
        } else if (abstractSchedule instanceof MonthDay) {
            state = new MonthDayAbstractScheduleState();
        } else if (abstractSchedule instanceof Month) {
            state = new MonthAbstractScheduleState();
        } else if (abstractSchedule instanceof Timer) {
            state = new RecurringAbstractScheduleState();
        }

        if (state == null) {
            throw new NullPointerException("AbstractScheduleState Is Null!");
        }

        for (AbstractSchedule s : abstractSchedule.getSchedules()) {
            state.addState(create(s, job));
        }

        state.setSchedule(abstractSchedule);
        state.job = job;

        return state;
    }
}
