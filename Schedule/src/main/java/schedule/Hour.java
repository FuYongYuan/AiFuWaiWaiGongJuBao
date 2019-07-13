package schedule;

public class Hour extends Schedule {

    private Hour() {
    }

    public static Hour at(int hour) {
        return at(hour, hour);
    }

    public static Hour at(int from, int to) {
        Hour instance = new Hour();
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
