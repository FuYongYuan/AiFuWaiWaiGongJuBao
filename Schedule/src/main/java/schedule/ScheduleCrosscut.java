package schedule;

/**
 * 时间设置顺序
 *
 * @author fyy
 */
class ScheduleCrosscut {

    /**
     * 获取当前设置的顺序
     *
     * @param s 设置的单位
     * @return 顺序
     */
    public static int getOrder(AbstractSchedule s) {
        if (s instanceof Second || s instanceof Timer) {
            return 0;
        } else if (s instanceof Minute) {
            return 1;
        } else if (s instanceof Hour) {
            return 2;
        } else if (s instanceof WeekDay) {
            return 3;
        } else if (s instanceof MonthDay) {
            return 4;
        } else if (s instanceof Month) {
            return 5;
        }
        return 0;
    }
}
