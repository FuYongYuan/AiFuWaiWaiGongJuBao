package schedule;

import java.time.DayOfWeek;

public class WeekDay extends Schedule {

    private WeekDay() {
    }

    public static WeekDay at(DayOfWeek dayOfWeek) {
        return at(dayOfWeek, dayOfWeek);
    }

    public static WeekDay at(DayOfWeek from, DayOfWeek to) {
        WeekDay instance = new WeekDay();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private DayOfWeek from;

    public DayOfWeek getFrom() {
        return from;
    }

    private DayOfWeek to;

    public DayOfWeek getTo() {
        return to;
    }
}
