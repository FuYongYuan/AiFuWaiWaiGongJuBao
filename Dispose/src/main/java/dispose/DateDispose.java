package dispose;

import enumerate.DateType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理类
 *
 * @author fyy
 */
public class DateDispose {

    /**
     * 当前时间-----------------------------------------------------------------------------------------------------------
     */
    public static String getYear() {
        return formattingDate(new Date(), DateType.Year);
    }

    public static String getMonth() {
        return formattingDate(new Date(), DateType.Month);
    }

    public static String getDay() {
        return formattingDate(new Date(), DateType.Day);
    }

    public static String getHour() {
        return formattingDate(new Date(), DateType.Hour);
    }

    public static String getMinute() {
        return formattingDate(new Date(), DateType.Minute);
    }

    public static String getSecond() {
        return formattingDate(new Date(), DateType.Second);
    }

    public static String getYearMonthDay() {
        return formattingDate(new Date(), DateType.Year_Month_Day);
    }

    public static String getYearMonth() {
        return formattingDate(new Date(), DateType.Year_Month);
    }

    public static String getMonthDay() {
        return formattingDate(new Date(), DateType.Month_Day);
    }

    public static String getYearMonthDayHourMinuteSecond() {
        return formattingDate(new Date(), DateType.Year_Month_Day_Hour_Minute_Second);
    }

    public static String getYearMonthDayHourMinuteSecondMillisecond() {
        return formattingDate(new Date(), DateType.Year_Month_Day_Hour_Minute_Second_MS);
    }

    public static String getHourMinuteSecond() {
        return formattingDate(new Date(), DateType.Hour_Minute_Second);
    }

    public static String getMonthDayHourMinuteSecond() {
        return formattingDate(new Date(), DateType.Month_Day_Hour_Minute_Second);
    }

    public static String getMonthDayHourMinute() {
        return formattingDate(new Date(), DateType.Month_Day_Hour_Minute);
    }

    public static String getDayHourMinute() {
        return formattingDate(new Date(), DateType.Day_Hour_Minute);
    }

    //------------------------------------------------------------------------------------------------------------------获取时间戳

    /**
     * 时间戳转时间(10位时间戳)
     */
    public static String timestamp10() {
        long timeStampSec = System.currentTimeMillis() / 1000;
        return String.format("%010d", timeStampSec);
    }

    /**
     * 时间戳转时间(13位时间戳)
     */
    public static String timestamp13() {
        return String.format("%010d", System.currentTimeMillis());
    }

    //------------------------------------------------------------------------------------------------------------------转换

    /**
     * date转String
     */
    public static String formattingDate(Date date, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * String转date
     */
    public static Date formattingDate(String date, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Date str = null;
        try {
            str = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String formattingDateToString(Date date) {
        return formattingDate(date, DateType.Year_Month_Day_Hour_Minute_Second);
    }

    public static Date formattingStringToDate(String date) {
        return formattingDate(date, DateType.Year_Month_Day_Hour_Minute_Second);
    }


    //------------------------------------------------------------------------------------------------------------------转换计算

    /**
     * 年计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String yearCalculateString(Date date, int i, DateType dateType) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.YEAR, i);
        date = rightNow.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * 年计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String yearCalculateString(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.YEAR, i);
        return sdf.format(rightNow.getTime());
    }

    /**
     * 年计算
     *
     * @param date 时间
     * @param i    需要加减 加用正数 减用负数
     */
    public static Date yearCalculateDate(Date date, int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.YEAR, i);
        return rightNow.getTime();
    }

    /**
     * 年计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static Date yearCalculateDate(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.YEAR, i);
        return rightNow.getTime();
    }

    /**
     * 月计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String monthCalculateString(Date date, int i, DateType dateType) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, i);
        date = rightNow.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * 月计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String monthCalculateString(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.MONTH, i);
        return sdf.format(rightNow.getTime());
    }

    /**
     * 月计算
     *
     * @param date 时间
     * @param i    需要加减 加用正数 减用负数
     */
    public static Date monthCalculateDate(Date date, int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, i);
        return rightNow.getTime();
    }

    /**
     * 月计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static Date monthCalculateDate(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.MONTH, i);
        return rightNow.getTime();
    }

    /**
     * 日计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String dayCalculateString(Date date, int i, DateType dateType) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR, i);
        date = rightNow.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * 日计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String dayCalculateString(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.DAY_OF_YEAR, i);
        return sdf.format(rightNow.getTime());
    }

    /**
     * 日计算
     *
     * @param date 时间
     * @param i    需要加减 加用正数 减用负数
     */
    public static Date dayCalculateDate(Date date, int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR, i);
        return rightNow.getTime();
    }

    /**
     * 日计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static Date dayCalculateDate(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.DAY_OF_YEAR, i);
        return rightNow.getTime();
    }

    /**
     * 小时计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String hoursCalculateString(Date date, int i, DateType dateType) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.HOUR_OF_DAY, i);
        date = rightNow.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * 小时计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String hoursCalculateString(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.HOUR_OF_DAY, i);
        return sdf.format(rightNow.getTime());
    }

    /**
     * 小时计算
     *
     * @param date 时间
     * @param i    需要加减 加用正数 减用负数
     */
    public static Date hoursCalculateDate(Date date, int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.HOUR_OF_DAY, i);
        return rightNow.getTime();
    }

    /**
     * 小时计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static Date hoursCalculateDate(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.HOUR_OF_DAY, i);
        return rightNow.getTime();
    }

    /**
     * 分钟计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String minutesCalculateString(Date date, int i, DateType dateType) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MINUTE, i);
        date = rightNow.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * 分钟计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String minutesCalculateString(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.MINUTE, i);
        return sdf.format(rightNow.getTime());
    }

    /**
     * 分钟计算
     *
     * @param date 时间
     * @param i    需要加减 加用正数 减用负数
     */
    public static Date minutesCalculateDate(Date date, int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MINUTE, i);
        return rightNow.getTime();
    }

    /**
     * 分钟计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static Date minutesCalculateDate(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.MINUTE, i);
        return rightNow.getTime();
    }

    /**
     * 秒计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String secondsCalculateString(Date date, int i, DateType dateType) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND, i);
        date = rightNow.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        return sdf.format(date);
    }

    /**
     * 秒计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static String secondsCalculateString(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.SECOND, i);
        return sdf.format(rightNow.getTime());
    }

    /**
     * 秒计算
     *
     * @param date 时间
     * @param i    需要加减 加用正数 减用负数
     */
    public static Date secondsCalculateDate(Date date, int i) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND, i);
        return rightNow.getTime();
    }

    /**
     * 秒计算
     *
     * @param date     时间
     * @param i        需要加减 加用正数 减用负数
     * @param dateType 返回的格式
     */
    public static Date secondsCalculateDate(String date, int i, DateType dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType.getValue());
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.SECOND, i);
        return rightNow.getTime();
    }

    //------------------------------------------------------------------------------------------------------------------返回

    /**
     * 返回文字表示:2013年01月01日
     */
    public static String dateFormatText(String date) {
        return date.replaceFirst("-", "年").replaceFirst("-", "月") + "日";
    }

    /**
     * 返回符号表示:2013-01-01
     */
    public static String dateFormatSymbol(String date) {
        return date.replaceFirst("年", "-").replaceFirst("月", "-").replaceFirst("日", "");
    }

    /**
     * 返回文字表示:2013年01月01日
     */
    public static String dateFormatText(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date).replaceFirst("-", "年").replaceFirst("-", "月") + "日";
    }

    /**
     * 返回符号表示:2013-01-01
     */
    public static String dateFormatSymbol(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date).replaceFirst("年", "-").replaceFirst("月", "-").replaceFirst("日", "");
    }

    //------------------------------------------------------------------------------------------------------------------比较

    /**
     * 比较时间是否相等
     *
     * @param d1 时间1
     * @param d2 时间2
     * @return true 时间1和时间2相等    false 时间1和时间2不相等
     */
    public static boolean compareDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        } else {
            //转换格式化
            SimpleDateFormat sdf = new SimpleDateFormat(DateType.Year_Month_Day_Hour_Minute_Second.getValue());
            //转成String类型进行比较
            String s1 = sdf.format(d1);
            String s2 = sdf.format(d2);

            return s1.equals(s2);
        }
    }

    /**
     * 比较时间大小
     *
     * @param d1 时间1
     * @param d2 时间2
     * @return true 时间1比时间2早-大    false 时间1比时间2晚-小
     */
    public static boolean compareDateSize(Date d1, Date d2) {
        return d1.before(d2);
    }

    /**
     * 比较时间是否在范围内
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param date      时间
     * @return true 是    false 否
     */
    public static boolean compareDateWithin(Date startDate, Date endDate, Date date) {
        return (compareDate(startDate, date) || compareDateSize(startDate, date)) && (compareDate(date, endDate) || compareDateSize(date, endDate));
    }

    /**
     * 计算两个时间差
     *
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param returnType 返回类型   hh小时  mm分钟  ss秒  day天
     */
    public static Long dateDiff(Date startTime, Date endTime, DateType returnType) {
        // 按照传入的格式生成一个simpledateformate对象
        // 一天的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        // 一小时的毫秒数
        long nh = 1000 * 60 * 60;
        // 一分钟的毫秒数
        long nm = 1000 * 60;
        // 一秒钟的毫秒数
        long ns = 1000;
        long diff;
        long day;
        long hour;
        long min;
        long sec;
        // 获得两个时间的毫秒时间差异
        diff = endTime.getTime() - startTime.getTime();
        // 计算差多少天
        day = diff / nd;
        // 计算差多少小时
        hour = diff % nd / nh + day * 24;
        // 计算差多少分钟
        min = diff % nd % nh / nm + (diff % nd / nh * 60) + day * 24 * 60;
        // 计算差多少秒
        sec = diff % nd % nh % nm / ns + (diff % nd / nh * 60 * 60) + (diff % nd % nh / nm * 60) + day * 24 * 60 * 60;
        // 输出结果
        if (DateType.Hour.equals(returnType)) {
            return hour;
        } else if (DateType.Minute.equals(returnType)) {
            return min;
        } else if (DateType.Second.equals(returnType)) {
            return sec;
        } else if (DateType.Day.equals(returnType)) {
            return day;
        } else {
            return 0L;
        }
    }

    /**
     * 计算两个时间差
     *
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param formatType 格式
     * @param returnType 返回类型   hh小时  mm分钟  ss秒  day天
     */
    public static Long dateDiff(String startTime, String endTime, DateType formatType, DateType returnType) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(formatType.getValue());
        // 一天的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        // 一小时的毫秒数
        long nh = 1000 * 60 * 60;
        // 一分钟的毫秒数
        long nm = 1000 * 60;
        // 一秒钟的毫秒数
        long ns = 1000;
        long diff;
        long day;
        long hour;
        long min;
        long sec;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            // 计算差多少天
            day = diff / nd;
            // 计算差多少小时
            hour = diff % nd / nh + day * 24;
            // 计算差多少分钟
            min = diff % nd % nh / nm + (diff % nd / nh * 60) + day * 24 * 60;
            // 计算差多少秒
            sec = diff % nd % nh % nm / ns + (diff % nd / nh * 60 * 60) + (diff % nd % nh / nm * 60) + day * 24 * 60 * 60;
            // 输出结果
            if (DateType.Hour.equals(returnType)) {
                return hour;
            } else if (DateType.Minute.equals(returnType)) {
                return min;
            } else if (DateType.Second.equals(returnType)) {
                return sec;
            } else if (DateType.Day.equals(returnType)) {
                return day;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    //------------------------------------------------------------------------------------------------------------------判断

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     */
    public static int dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat(DateType.Year_Month_Day.getValue());
        Calendar c = Calendar.getInstance();
        int dayForWeek = 0;
        try {
            c.setTime(format.parse(pTime));
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayForWeek;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     */
    public static int dayForWeek(Date pTime) {
        Calendar c = Calendar.getInstance();
        int dayForWeek = 0;
        try {
            c.setTime(pTime);
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayForWeek;
    }

    /**
     * 判断当月多少天
     *
     * @param month 月份
     * @param year  年
     * @return 多少天
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

    //------------------------------------------------------------------------------------------------------------------随机时间

    /**
     * 生成随机日期
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 随机日期
     */
    public static Date randomDate(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        //注意月份要减去1
        calendar.set(
                Integer.parseInt(formattingDate(startDate, DateType.Year)),
                Integer.parseInt(formattingDate(startDate, DateType.Month)) - 1,
                Integer.parseInt(formattingDate(startDate, DateType.Day)));
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(formattingDate(startDate, DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(formattingDate(startDate, DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(formattingDate(startDate, DateType.Second)));
        long min = calendar.getTime().getTime();
        calendar.set(
                Integer.parseInt(formattingDate(endDate, DateType.Year)),
                Integer.parseInt(formattingDate(endDate, DateType.Month)) - 1,
                Integer.parseInt(formattingDate(endDate, DateType.Day)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(formattingDate(endDate, DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(formattingDate(endDate, DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(formattingDate(endDate, DateType.Second)));
        long max = calendar.getTime().getTime();
        //得到大于等于min小于max的double值
        double randomDate = Math.random() * (max - min) + min;
        //将double值舍入为整数，转化成long类型
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar.getTime();
    }

    /**
     * 生成随机日期
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 随机日期
     */
    public static Date randomDate(String startDate, String endDate) {
        Calendar calendar = Calendar.getInstance();
        //注意月份要减去1
        calendar.set(
                Integer.parseInt(formattingDate(formattingStringToDate(startDate), DateType.Year)),
                Integer.parseInt(formattingDate(formattingStringToDate(startDate), DateType.Month)) - 1,
                Integer.parseInt(formattingDate(formattingStringToDate(startDate), DateType.Day)));
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(formattingDate(formattingStringToDate(startDate), DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(formattingDate(formattingStringToDate(startDate), DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(formattingDate(formattingStringToDate(startDate), DateType.Second)));
        long min = calendar.getTime().getTime();
        calendar.set(
                Integer.parseInt(formattingDate(formattingStringToDate(endDate), DateType.Year)),
                Integer.parseInt(formattingDate(formattingStringToDate(endDate), DateType.Month)) - 1,
                Integer.parseInt(formattingDate(formattingStringToDate(endDate), DateType.Day)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(formattingDate(formattingStringToDate(endDate), DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(formattingDate(formattingStringToDate(endDate), DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(formattingDate(formattingStringToDate(endDate), DateType.Second)));
        long max = calendar.getTime().getTime();
        //得到大于等于min小于max的double值
        double randomDate = Math.random() * (max - min) + min;
        //将double值舍入为整数，转化成long类型
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar.getTime();
    }
}

