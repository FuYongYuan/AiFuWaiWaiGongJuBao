package schedule.service;

/**
 * 公历
 *
 * @author fyy
 */
class GregorianCalendar {
    /**
     * 获取月份多少天
     *
     * @param month 月份
     * @param year  年
     */
    public static int getDaysOfMonth(int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return (year % 400) == 0 || (year % 100) != 0 && (year % 4) == 0 ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 0;
        }
    }
}
