package schedule;

import java.time.DayOfWeek;

/**
 * 周*
 *
 * @author fyy
 */
public class WeekDay extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private WeekDay() {
    }

    /**
     * 设置周*执行
     *
     * @param dayOfWeek 周*
     */
    public static WeekDay at(DayOfWeek dayOfWeek) {
        return at(dayOfWeek, dayOfWeek);
    }

    /**
     * 设置从周*开始到周* 只能正向即 周一到周三 不能周三到周一
     *
     * @param from 开始周*
     * @param to   结束周*
     */
    public static WeekDay at(DayOfWeek from, DayOfWeek to) {
        WeekDay instance = new WeekDay();
        instance.from = from;
        instance.to = to;
        return instance;
    }


    private DayOfWeek from;

    /**
     * 开始周*
     */
    public DayOfWeek getFrom() {
        return from;
    }

    private DayOfWeek to;

    /**
     * 结束周*
     */
    public DayOfWeek getTo() {
        return to;
    }
}
