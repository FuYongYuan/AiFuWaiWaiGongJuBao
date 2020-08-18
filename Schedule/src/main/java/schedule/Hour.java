package schedule;

/**
 * 小时
 *
 * @author fyy
 */
public class Hour extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private Hour() {
    }

    /**
     * 设置*小时执行
     *
     * @param hour *小时 0-23
     */
    public static Hour at(int hour) {
        return at(hour, hour);
    }

    /**
     * 设置*小时执行
     *
     * @param from 开始*小时 0-23
     * @param to   结束*小时 0-23
     */
    public static Hour at(int from, int to) {
        Hour instance = new Hour();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private int from;

    /**
     * 开始*小时
     */
    public int getFrom() {
        return from;
    }

    private int to;

    /**
     * 结束*小时
     */
    public int getTo() {
        return to;
    }
}
