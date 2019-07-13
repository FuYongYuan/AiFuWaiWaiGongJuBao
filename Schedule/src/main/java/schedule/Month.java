package schedule;

public class Month extends Schedule {

    private Month() {
    }

    public static Month at(int month) {
        return at(month, month);
    }

    public static Month at(int from, int to) {
        Month instance = new Month();
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
