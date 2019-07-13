package schedule.service;

import schedule.Timer;

import java.time.Duration;
import java.time.LocalDateTime;

class RecurringScheduleState extends ScheduleState<Timer> {


    private DateNumber lastTriggerTime;


    @Override
    protected void onRun(LocalDateTime now) {
    }

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        DateNumber result;

        if (lastTriggerTime == null) {
            result = time;
            if (result.nano > 0 && result.nano < 1000000) {
                result = result.withNano(1000000);
            }
        } else {
            result = lastTriggerTime;
            result.def = time.def;

            if (!result.toLocalDateTime().isAfter(time.toLocalDateTime())) {

                Duration d = Duration.between(result.toLocalDateTime(), time.toLocalDateTime());
                if (d.getNano() < 1000000) {
                    d = d.withNanos(1000000);
                }

                double count = Math.ceil((double) Helper.getMilliseconds(d) / Helper.getMilliseconds(schedule.getInterval()));

                Duration d1 = schedule.getInterval().multipliedBy((long) count);
                result = result.plusSeconds((int) d1.getSeconds()).plusNanos(d1.getNano());
            }
        }

        lastTriggerTime = result;

        return result;
    }

    @Override
    public void setDataNumberDefComponent(DateNumberDef def) {
    }
}
