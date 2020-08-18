package schedule.service;

import schedule.MonthDay;

/**
 * 月份中*日调度状态
 *
 * @author fyy
 */
class MonthDayAbstractScheduleState extends AbstractScheduleState<MonthDay> {

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        int day = time.day;

        DateNumber result;

        if (day < schedule.getFrom()) {
            result = time.withDayOfMonth(schedule.getFrom()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (day > schedule.getTo()) {
            result = time.plusMonths(1).withDayOfMonth(schedule.getFrom()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            result = time;
        }

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
        def.day = new FiniteNumberSet(schedule.getTo(), schedule.getTo());
    }
}
