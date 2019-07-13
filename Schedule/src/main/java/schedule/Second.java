package schedule;

public class Second extends Schedule {

    private Second() {
    }

    public static Second at(int second) {
        return at(second, second);
    }

    public static Second at(int from, int to) {
        Second instance = new Second();
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
