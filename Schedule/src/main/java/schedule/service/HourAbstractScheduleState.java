package schedule.service;


import schedule.Hour;

/**
 * 小时调度状态
 *
 * @author fyy
 */
class HourAbstractScheduleState extends AbstractScheduleState<Hour> {

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        int hour = time.hour;

        DateNumber result;

        if (hour < schedule.getFrom()) {
            result = time.withHour(schedule.getFrom()).withMinute(0).withSecond(0).withNano(0);
        } else if (hour > schedule.getTo()) {
            result = time.plusDays(1).withHour(schedule.getFrom()).withMinute(0).withSecond(0).withNano(0);
        } else {
            result = time;
        }

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
        def.hour = new FiniteNumberSet(schedule.getFrom(), schedule.getTo());
    }
}
