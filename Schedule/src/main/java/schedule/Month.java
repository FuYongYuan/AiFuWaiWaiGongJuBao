package schedule;

/**
 * 月份
 *
 * @author fyy
 */
public class Month extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private Month() {
    }

    /**
     * 设置*月执行
     *
     * @param month *月 0-11
     */
    public static Month at(int month) {
        return at(month, month);
    }

    /**
     * 设置*月执行
     *
     * @param from 开始*月 0-11
     * @param to   结束*月 0-11
     */
    public static Month at(int from, int to) {
        Month instance = new Month();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private int from;

    /**
     * 开始*月
     */
    public int getFrom() {
        return from;
    }

    private int to;

    /**
     * 结束*月
     */
    public int getTo() {
        return to;
    }
}
