package schedule.service;

import schedule.Month;

/**
 * 月调度状态
 *
 * @author fyy
 */
class MonthAbstractScheduleState extends AbstractScheduleState<Month> {

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        int month = time.month;

        DateNumber result;

        if (month < schedule.getFrom()) {
            result = time.withMonth(schedule.getFrom()).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (month > schedule.getTo()) {
            result = time.plusYears(1).withMonth(schedule.getFrom()).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            result = time;
        }

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
        def.month = new FiniteNumberSet(schedule.getFrom(), schedule.getTo());
    }
}
