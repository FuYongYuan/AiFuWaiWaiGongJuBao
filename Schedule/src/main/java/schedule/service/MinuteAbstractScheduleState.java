package schedule.service;

import schedule.Minute;

/**
 * 分钟调度状态
 *
 * @author fyy
 */
class MinuteAbstractScheduleState extends AbstractScheduleState<Minute> {

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        int minute = time.minute;

        DateNumber result;

        if (minute < schedule.getFrom()) {
            result = time.withMinute(schedule.getFrom()).withSecond(0).withNano(0);
        } else if (minute > schedule.getTo()) {
            result = time.plusHours(1).withMinute(schedule.getFrom()).withSecond(0).withNano(0);
        } else {
            result = time;
        }

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
        def.minute = new FiniteNumberSet(schedule.getFrom(), schedule.getTo());
    }
}
