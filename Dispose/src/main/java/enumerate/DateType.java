package enumerate;

/**
 * 年月日时分秒格式枚举
 *
 * @author fyy
 */
public enum DateType {

    /**
     * yyyy
     */
    Year("yyyy"),

    /**
     * MM
     */
    Month("MM"),

    /**
     * dd
     */
    Day("dd"),

    /**
     * HH
     */
    Hour("HH"),

    /**
     * mm
     */
    Minute("mm"),

    /**
     * ss
     */
    Second("ss"),

    /**
     * SSSS
     */
    MS("SSSS"),

    /**
     * yyyy-MM-dd
     */
    Year_Month_Day("yyyy-MM-dd"),

    /**
     * yyyy-MM
     */
    Year_Month("yyyy-MM"),

    /**
     * MM-dd
     */
    Month_Day("MM-dd"),

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    Year_Month_Day_Hour_Minute_Second("yyyy-MM-dd HH:mm:ss"),

    /**
     * yyyy-MM-dd HH:mm:ss:SSSS
     */
    Year_Month_Day_Hour_Minute_Second_MS("yyyy-MM-dd HH:mm:ss:SSSS"),

    /**
     * HH:mm:ss
     */
    Hour_Minute_Second("HH:mm:ss"),

    /**
     * MM-dd HH:mm:ss
     */
    Month_Day_Hour_Minute_Second("MM-dd HH:mm:ss"),

    /**
     * MM-dd HH:mm
     */
    Month_Day_Hour_Minute("MM-dd HH:mm"),

    /**
     * dd HH:mm
     */
    Day_Hour_Minute("dd HH:mm"),

    /**
     * MM-dd HH
     */
    Month_Day_Hour("MM-dd HH"),

    /**
     * yyyyMMddHHmmss
     */
    YearMonthDayHourMinuteSecond("yyyyMMddHHmmss"),

    /**
     * yyyyMMddHHmmssSSSS
     */
    YearMonthDayHourMinuteSecondMS("yyyyMMddHHmmssSSSS"),

    /**
     * yyyyMMdd
     */
    YearMonthDay("yyyyMMdd"),

    /**
     * HHmmss
     */
    HourMinuteSecond("HHmmss"),

    /**
     * yyMMddHHmm
     */
    YYMonthDayHourMinute("yyMMddHHmm");

    /**
     * 实际字符串
     */
    private final String value;

    DateType(String value) {
        this.value = value;
    }

    /**
     * 获取实际字符串
     */
    public String getValue() {
        return value;
    }

}
