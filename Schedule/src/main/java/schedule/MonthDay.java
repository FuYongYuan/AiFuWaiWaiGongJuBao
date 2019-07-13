package schedule;

public class MonthDay extends Schedule {

    private MonthDay() {
    }

    public static MonthDay at(int day) {
        return at(day, day);
    }

    public static MonthDay at(int from, int to) {
        MonthDay instance = new MonthDay();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private int from;

    public int getFrom() {
        return from;
    }

    private int to;

    public int getTo() {
        return to;
    }
}
