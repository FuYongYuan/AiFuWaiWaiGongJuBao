package schedule;

/**
 * 月份天-几号
 *
 * @author fyy
 */
public class MonthDay extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private MonthDay() {
    }

    /**
     * 设置月份里面的*日执行
     *
     * @param day *日 1-31
     */
    public static MonthDay at(int day) {
        return at(day, day);
    }

    /**
     * 设置月份里面的*日执行
     *
     * @param from 开始*日 1-31
     * @param to   结束*日 1-31
     */
    public static MonthDay at(int from, int to) {
        MonthDay instance = new MonthDay();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private int from;

    /**
     * 开始*日
     */
    public int getFrom() {
        return from;
    }

    private int to;

    /**
     * 结束*日
     */
    public int getTo() {
        return to;
    }
}
