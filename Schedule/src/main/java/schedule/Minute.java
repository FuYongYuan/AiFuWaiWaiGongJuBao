package schedule;

public class Minute extends Schedule {

    private Minute() {
    }

    public static Minute at(int minute) {
        return at(minute, minute);
    }

    public static Minute at(int from, int to) {
        Minute range = new Minute();
        range.from = from;
        range.to = to;
        return range;
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
