package dispose;

import java.math.BigDecimal;

/**
 * 数字转换为人民币的大写
 *
 * @author fyy
 */
public class MoneyToChinese {
    /**
     * 汉语中数字大写
     */
    private static final String[] CHINESE_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖"};

    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CHINESE_UPPER_MONEY_UNIT = {"厘", "分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟"};

    /**
     * 特殊字符：整
     */
    private static final String CHINESE_FULL = "整";

    /**
     * 特殊字符：负
     */
    private static final String CHINESE_NEGATIVE = "负";

    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 3;

    /**
     * 特殊字符：零元整
     */
    private static final String CHINESE_ZERO_FULL = "零元" + CHINESE_FULL;

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param money 输入的金额
     * @return 对应的汉语大写
     */
    public static String to(long money) {
        return to(new BigDecimal(money));
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param money 输入的金额
     * @return 对应的汉语大写
     */
    public static String to(int money) {
        return to(new BigDecimal(money));
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param money 输入的金额
     * @return 对应的汉语大写
     */
    public static String to(double money) {
        return to(new BigDecimal(money));
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param money 输入的金额
     * @return 对应的汉语大写
     */
    public static String to(String money) {
        return to(new BigDecimal(money));
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param money 输入的金额
     * @return 对应的汉语大写
     */
    public static String to(BigDecimal money) {
        StringBuilder sb = new StringBuilder();
        // -1、0、1，因为此BigDecimal的值是负数、零、正数
        int signum = money.signum();
        // 零元整的情况
        if (signum == 0) {
            return CHINESE_ZERO_FULL;
        }
        //这里会进行金额的四舍五入
        long number = money.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 1000;
        int numUnit;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        int afterSecond = 100;
        int afterFirst = 10;
        if (scale <= 0) {
            numIndex = 3;
            number = number / 1000;
            getZero = true;
        } else if (scale % afterSecond <= 0) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        } else if (scale % afterFirst <= 0) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (number > 0) {
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CHINESE_UPPER_MONEY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CHINESE_UPPER_MONEY_UNIT[10]);
                }
                sb.insert(0, CHINESE_UPPER_MONEY_UNIT[numIndex]);
                sb.insert(0, CHINESE_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CHINESE_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == MONEY_PRECISION) {
                    sb.insert(0, CHINESE_UPPER_MONEY_UNIT[numIndex]);
                } else if (((numIndex - MONEY_PRECISION) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CHINESE_UPPER_MONEY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果 = -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CHINESE_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (scale <= 0) {
            sb.append(CHINESE_FULL);
        }
        return sb.toString();
    }
}