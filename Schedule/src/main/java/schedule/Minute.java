package schedule;

/**
 * 分钟
 *
 * @author fyy
 */
public class Minute extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private Minute() {
    }

    /**
     * 设置*分钟执行
     *
     * @param minute *分钟 0-59
     */
    public static Minute at(int minute) {
        return at(minute, minute);
    }

    /**
     * 设置*分钟执行
     *
     * @param from 开始*分钟 0-59
     * @param to   结束*分钟 0-59
     */
    public static Minute at(int from, int to) {
        Minute range = new Minute();
        range.from = from;
        range.to = to;
        return range;
    }


    private int from;

    /**
     * 开始*分钟
     */
    public int getFrom() {
        return from;
    }

    private int to;

    /**
     * 结束*分钟
     */
    public int getTo() {
        return to;
    }
}
