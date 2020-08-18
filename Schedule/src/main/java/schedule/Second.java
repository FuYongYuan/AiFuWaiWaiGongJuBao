package schedule;

/**
 * 秒
 *
 * @author fyy
 */
public class Second extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private Second() {
    }

    /**
     * 设置*秒执行
     *
     * @param second 秒 0-59
     */
    public static Second at(int second) {
        return at(second, second);
    }

    /**
     * 设置*秒执行
     *
     * @param from 开始*秒 0-59
     * @param to   结束*秒 0-59
     */
    public static Second at(int from, int to) {
        Second instance = new Second();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private int from;

    /**
     * 开始*秒
     */
    public int getFrom() {
        return from;
    }

    private int to;

    /**
     * 结束*秒
     */
    public int getTo() {
        return to;
    }
}
