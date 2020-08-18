package schedule.service;

import schedule.Timer;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 每个*执行调度状态
 *
 * @author fyy
 */
class RecurringAbstractScheduleState extends AbstractScheduleState<Timer> {

    private DateNumber lastTriggerTime;

    /**
     * 100w
     */
    private static final int ONE_MILLION = 1000000;

    @Override
    protected void onRun(LocalDateTime now) {
    }

    @Override
    protected DateNumber adjustTime(DateNumber time) {

        DateNumber result;

        if (lastTriggerTime == null) {
            result = time;
            if (result.nano > 0 && result.nano < ONE_MILLION) {
                result = result.withNano(1000000);
            }
        } else {
            result = lastTriggerTime;
            result.def = time.def;

            if (!result.toLocalDateTime().isAfter(time.toLocalDateTime())) {

                Duration d = Duration.between(result.toLocalDateTime(), time.toLocalDateTime());
                if (d.getNano() < ONE_MILLION) {
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
