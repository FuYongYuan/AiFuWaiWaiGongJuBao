package schedule.service;

import schedule.Second;

class SecondScheduleState extends ScheduleState<Second> {

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        int second = time.second;


        DateNumber result;

        if (second < schedule.getFrom()) {
            result = time.withSecond(schedule.getFrom()).withNano(0);
        } else if (second > schedule.getTo()) {
            result = time.plusMinutes(1).withSecond(schedule.getFrom()).withNano(0);
        } else if (time.nano > 0) {
            result = time.plusSeconds(1).withNano(0);
        } else {
            result = time;
        }

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
        def.second = new FiniteNumberSet(schedule.getFrom(), schedule.getTo());
    }
}
