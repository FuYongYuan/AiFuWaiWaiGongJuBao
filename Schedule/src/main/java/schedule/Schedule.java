package schedule;

import java.util.Arrays;

public abstract class Schedule {

    private Schedule[] schedules = new Schedule[]{};

    public Schedule[] getSchedules() {
        return Arrays.copyOf(schedules, schedules.length);
    }

    public Schedule with(Schedule... schedules) {

        for (Schedule s : schedules) {

            if (ScheduleCrosscut.getOrder(this) <= ScheduleCrosscut.getOrder(s)) {
                throw new IllegalArgumentException(String.format("Cannot add %s to %s.", s.getClass(), getClass()));
            }
        }

        this.schedules = schedules;

        return this;
    }
}
