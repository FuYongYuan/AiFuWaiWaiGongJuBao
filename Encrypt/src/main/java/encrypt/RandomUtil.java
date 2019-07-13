package encrypt;

import dispose.DateDispose;
import enumerate.DateType;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 随机数
 */
public class RandomUtil {
    //------------------------------------------------------------------------------------------------------------------随机数字

    /**
     * 根据长度获取随机数
     */
    public static String randomNumberByLength(int length) {
        StringBuilder randomStr = new StringBuilder("");
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomStr.append(random.nextInt(10));
        }
        return randomStr.toString();
    }

    /**
     * 随机范围数
     */
    public static int randomNumberByWithin(int within) {
        return randomNumberByWithin(0, within);
    }

    /**
     * 随机范围数
     */
    public static int randomNumberByWithin(int startWithin, int endWithin) {
        Random random = new Random();
        return random.nextInt(endWithin - startWithin + 1) + startWithin;
    }
    //------------------------------------------------------------------------------------------------------------------随机字母

    /**
     * 随机字母
     */
    public static String randomAlphabet(int length) {
        char[] A_Z_a_z = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R'
                , 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
                , 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Random rd = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)//随即10个拿出来看看
        {
            result.append(A_Z_a_z[rd.nextInt(52)]);
        }
        return result.toString();
    }

    //------------------------------------------------------------------------------------------------------------------随机boolean

    /**
     * 随机boolean
     */
    public static boolean randomBoolean() {
        Random rd = new Random();
        return rd.nextBoolean();
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
                Integer.parseInt(DateDispose.formatting_Date(startDate, DateType.Year)),
                Integer.parseInt(DateDispose.formatting_Date(startDate, DateType.Month)) - 1,
                Integer.parseInt(DateDispose.formatting_Date(startDate, DateType.Day)));
        calendar.getTime().getTime();
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(DateDispose.formatting_Date(startDate, DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(DateDispose.formatting_Date(startDate, DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(DateDispose.formatting_Date(startDate, DateType.Second)));
        long min = calendar.getTime().getTime();
        calendar.set(
                Integer.parseInt(DateDispose.formatting_Date(endDate, DateType.Year)),
                Integer.parseInt(DateDispose.formatting_Date(endDate, DateType.Month)) - 1,
                Integer.parseInt(DateDispose.formatting_Date(endDate, DateType.Day)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(DateDispose.formatting_Date(endDate, DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(DateDispose.formatting_Date(endDate, DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(DateDispose.formatting_Date(endDate, DateType.Second)));
        calendar.getTime().getTime();
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
                Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(startDate), DateType.Year)),
                Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(startDate), DateType.Month)) - 1,
                Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(startDate), DateType.Day)));
        calendar.getTime().getTime();
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(startDate), DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(startDate), DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(startDate), DateType.Second)));
        long min = calendar.getTime().getTime();
        calendar.set(
                Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(endDate), DateType.Year)),
                Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(endDate), DateType.Month)) - 1,
                Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(endDate), DateType.Day)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(endDate), DateType.Hour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(endDate), DateType.Minute)));
        calendar.set(Calendar.SECOND, Integer.parseInt(DateDispose.formatting_Date(DateDispose.formatting_StringToDate(endDate), DateType.Second)));
        calendar.getTime().getTime();
        long max = calendar.getTime().getTime();
        //得到大于等于min小于max的double值
        double randomDate = Math.random() * (max - min) + min;
        //将double值舍入为整数，转化成long类型
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar.getTime();
    }
}
