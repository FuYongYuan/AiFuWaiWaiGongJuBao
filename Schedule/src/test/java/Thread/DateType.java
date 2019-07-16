package Thread;

/**
 * 年月日时分秒格式枚举
 */
public enum DateType {
    Year("yyyy"),
    Month("MM"),
    Day("dd"),
    Hour("HH"),
    Minute("mm"),
    Second("ss"),
    MS("SSSS"),
    Year_Month_Day("yyyy-MM-dd"),
    Year_Month("yyyy-MM"),
    Month_Day("MM-dd"),
    Year_Month_Day_Hour_Minute_Second("yyyy-MM-dd HH:mm:ss"),
    Year_Month_Day_Hour_Minute_Second_MS("yyyy-MM-dd HH:mm:ss:SSSS"),
    Hour_Minute_Second("HH:mm:ss"),
    Month_Day_Hour_Minute_Second("MM-dd HH:mm:ss"),
    Month_Day_Hour_Minute("MM-dd HH:mm"),
    Day_Hour_Minute("dd HH:mm"),
    Month_Day_Hour("MM-dd HH"),
    YearMonthDayHourMinuteSecond("yyyyMMddHHmmss"),
    YearMonthDayHourMinuteSecondMS("yyyyMMddHHmmssSSSS"),
    YearMonthDay("yyyyMMdd"),
    HourMinuteSecond("HHmmss"),
    YYMonthDayHourMinute("yyMMddHHmm");


    private String value;

    DateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
