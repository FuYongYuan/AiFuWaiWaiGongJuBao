package schedule.service;

/**
 * 默认时间类
 *
 * @author fyy
 */
class DateNumberDef {

    public FiniteNumberSet month;

    public FiniteNumberSet week;

    public FiniteNumberSet day;

    public FiniteNumberSet hour;

    public FiniteNumberSet minute;

    public FiniteNumberSet second;

    public FiniteNumberSet nano;

    public DateNumberDef() {
        month = new FiniteNumberSet(1, 12);
        week = new FiniteNumberSet(1, 7);
        day = new FiniteNumberSet(1, 31);
        hour = new FiniteNumberSet(0, 23);
        minute = new FiniteNumberSet(0, 59);
        second = new FiniteNumberSet(0, 59);
        nano = new FiniteNumberSet(0, 999999999);
    }
}
