package schedule.service;

import schedule.WeekDay;

class WeekDayScheduleState extends ScheduleState<WeekDay> {

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        int day = time.weekDay;

        int from = schedule.getFrom().getValue();
        int to = schedule.getTo().getValue();


        DateNumber result;

        if (day < from) {
            result = time.plusDays(from - day).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (day > to) {
            result = time.plusDays(from - day + 7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            result = time;
        }

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
        def.week = new FiniteNumberSet(schedule.getFrom().getValue(), schedule.getTo().getValue());
    }
}
