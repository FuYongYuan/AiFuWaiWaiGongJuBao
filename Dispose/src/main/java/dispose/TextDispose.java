package dispose;

import enumerate.Format;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本内容处理类
 */
public class TextDispose {
    //------------------------------------------------------------------------------------------------------------------格式化

    /**
     * 返回格式化JSON字符串。
     *
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public static String formatJson(String json) {
        int indent = 0;
        //存放格式化的json字符串
        StringBuilder jsonFormat = new StringBuilder();
        for (int i = 0; i < json.length(); i++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = json.charAt(i);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (indent > 0 && '\n' == jsonFormat.charAt(jsonFormat.length() - 1)) {
                jsonFormat.append(indent(indent));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonFormat.append(c).append("\n");
                    indent++;
                    break;
                case ',':
                    jsonFormat.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    jsonFormat.append("\n");
                    indent--;
                    jsonFormat.append(indent(indent));
                    jsonFormat.append(c);
                    break;
                default:
                    jsonFormat.append(c);
                    break;
            }
        }
        return jsonFormat.toString();
    }

    //------------------------------------------------------------------------------------------------------------------去除

    /**
     * 去除html标签
     */
    public static String deleteHtml(String value) {
        return value.replaceAll("</?[^>]+>", "");
    }

    /**
     * 去除各种换行空格符和html标签
     */
    public static String deleteHtmlSymbols(String value) {
        return deleteHtml(deleteSymbols(value));
    }

    /**
     * 去除各种换行空格符号
     */
    public static String deleteSymbols(String value) {
        return value.replaceAll("\\s*|\t|\r|\n", "");
    }

    /**
     * 删除所有html标签和最后的回车换行
     */
    public static String deleteHtmlLastEnter(String value) {
        return deleteLastEnter(deleteHtml(value));
    }

    /**
     * 去除最后的回车
     */
    public static String deleteLastEnter(String value) {
        if (!value.contains("\n")) {
            return value;
        } else if (value.lastIndexOf("\n") == (value.length() - 1)) {
            return value.substring(0, value.lastIndexOf("\n"));
        } else {
            return value;
        }
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     */
    public static String deleteEmoji(String source) {
        if (isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------分割截取

    /**
     * 截取数字
     */
    public static String splitNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 截取非数字
     */
    public static String splitNotNumber(String content) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 分割字符串
     *
     * @param s 字符串
     * @param c 分割值
     * @return 分割后的数组
     */
    public static String[] split(String s, char c) {
        ArrayList sections = new ArrayList();
        int pos = 0;

        while (true) {
            int pos1 = s.indexOf(c, pos);
            if (pos1 == -1) {
                sections.add(s.substring(pos));
                return (String[]) toArray(sections, String.class);
            }

            sections.add(s.substring(pos, pos1));
            pos = pos1 + 1;
        }
    }

    /**
     * 分割字符串
     *
     * @param s 字符串
     * @param p 分割值
     * @return 分割后的数组
     */
    public static String[] split(String s, String p) {
        ArrayList sections = new ArrayList();
        int pos = 0;

        while (true) {
            int pos1 = s.indexOf(p, pos);
            if (pos1 == -1) {
                sections.add(s.substring(pos));
                return (String[]) toArray(sections, String.class);
            }

            sections.add(s.substring(pos, pos1));
            pos = pos1 + 1;
        }
    }

    private static Object[] toArray(List<?> list, Class<?> elementType) {
        Object[] array = (Object[]) Array.newInstance(elementType, list.size());
        list.toArray(array);
        return array;
    }

    //------------------------------------------------------------------------------------------------------------------转换

    //提供对字符串的全角->半角，半角->全角转换
    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    private static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    private static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    private static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    private static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    private static final char SBC_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    private static final char DBC_SPACE = ' '; // 半角空格

    /**
     * 半角字符->全角字符转换
     * 只处理空格，!到˜之间的字符，忽略其他
     */
    public static String Half_TO_All(String src) {
        if (isEmpty(src)) {
            return null;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] cas = src.toCharArray();
        for (char ca : cas) {
            if (ca == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((ca >= DBC_CHAR_START) && (ca <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (ca + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca);
            }
        }
        return buf.toString();
    }

    /**
     * 全角字符->半角字符转换
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     */
    public static String All_TO_Half(String src) {
        if (isEmpty(src)) {
            return null;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }


    /**
     * 定义固定拆分标识_
     */
    private static final char SEPARATOR = '_';

    /**
     * 转换 没有_
     *
     * @param s       内容
     * @param head    头的大小写设置
     * @param content 内容的大小写设置
     * @return 没有_的内容
     */
    public static String toRemoverCamelCase(String s, Format head, Format content) {
        if (isEmpty(s)) {
            return null;
        }
        if (Format.Lower.equals(content)) {
            //全部转换为小写
            s = s.toLowerCase();
        } else if (Format.Upper.equals(content)) {
            //全部转换为小写
            s = s.toUpperCase();
        }
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        if (Format.Lower.equals(head)) {
            return sb.substring(0, 1).toLowerCase() + sb.substring(1);
        } else if (Format.Upper.equals(head)) {
            return sb.substring(0, 1).toUpperCase() + sb.substring(1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 转换 带有_
     *
     * @param s      内容
     * @param format 大小写设置
     * @return 转换后的内容
     */
    public static String toCamelCase(String s, Format format) {
        if (isEmpty(s)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;
            boolean upperCase = Character.isUpperCase(c);
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
            if ((i >= 0) && upperCase) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                } else if (upperCase || nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
            }
            sb.append(Character.toLowerCase(c));
        }
        if (Format.Lower.equals(format)) {
            return sb.toString().toLowerCase();
        } else if (Format.Upper.equals(format)) {
            return sb.toString().toUpperCase();
        } else {
            return sb.toString();
        }
    }

    /**
     * 首字母大写其他不变
     *
     * @param s 如: auditDate
     * @return 首字母大写其他不变
     */
    public static String toUpperCaseFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 首字母小写其他不变
     *
     * @param s 如: auditDate
     * @return 首字母小写其他不变
     */
    public static String toLowerCaseFirst(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * 在驼峰内容的驼峰处加入空格
     */
    public static String toCamelCaseJoinBlank(String str) {
        if (isEmpty(str)) {
            return "";
        }
        String head = str.substring(0, 1);
        String value = str.substring(1, str.length());
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            String s = value.substring(i, i + 1);
            if (isAllUpper(s)) {
                result.append(" ");
                result.append(s);
            } else if (isAllLower(s)) {
                result.append(s);
            }
        }
        return head + result.toString();
    }

    /**
     * String[] 转 String
     */
    public static String arrayToString(String[] strArr) {
        if (strArr != null && strArr.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (String s : strArr) {
                sb.append(s).append(",");
            }
            return new String(sb.delete(sb.length() - 1, sb.length()));
        } else {
            return null;
        }
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     *
     * @param sourceDate   为正数。
     * @param formatLength 字符总长度为 formatLength
     * @return 重组后的数据
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {
        return String.format("%0" + formatLength + "d", sourceDate);
    }

    //------------------------------------------------------------------------------------------------------------------钱额度中文数字转换

    private static final char[] ChineseNum = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    private static final char[] ChineseUnit = {'里', '分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟'};

    /**
     * 返回关于钱的中文式大写数字,支仅持到亿
     */
    public static String arabNumberToChineseRMB(int moneyNum) {
        String res = "";
        int i = 3;
        if (moneyNum == 0) {
            return "零元";
        }
        while (moneyNum > 0) {
            res = ChineseUnit[i++] + res;
            res = ChineseNum[moneyNum % 10] + res;
            moneyNum /= 10;
        }
        return res
                .replaceAll("零[拾佰仟]", "零")
                .replaceAll("零+亿", "亿")
                .replaceAll("零+万", "万")
                .replaceAll("零+元", "元")
                .replaceAll("零+", "零");

    }

    /**
     * 整数位支持12位,到仟亿
     * 支持到小数点后3位,如果大于3位,那么会四舍五入到3位
     */
    public static String arabNumberToChineseRMB(double moneyNum) {
        String res = "";
        String money = String.format("%.3f", moneyNum);
        int i = 0;
        if (moneyNum == 0.0) {
            return "零元";
        }
        String inte = money.split("\\.")[0];
        int deci = Integer.parseInt(money.split("\\.")[1].substring(0, 3));
        while (deci > 0) {
            res = ChineseUnit[i++] + res;
            res = ChineseNum[deci % 10] + res;
            deci /= 10;
        }
        res = res.replaceAll("零[里分角]", "零");
        if (i < 3) {
            res = "零" + res;
        }
        res = res.replaceAll("零+", "零");
        if (res.endsWith("零"))
            res = res.substring(0, res.length() - 1);
        try {
            return arabNumberToChineseRMB(inte) + res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回关于钱的中文式大写数字,支仅持到亿
     */
    private static String arabNumberToChineseRMB(String moneyNum) throws Exception {
        String res = "";
        int i = 3;
        int len = moneyNum.length();
        if (len > 12) {
            throw new Exception("超过最大位数!");
        }
        if ("0".equals(moneyNum)) {
            return "零元";
        }
        for (len--; len >= 0; len--) {
            res = ChineseUnit[i++] + res;
            int num = Integer.parseInt(moneyNum.charAt(len) + "");
            res = ChineseNum[num] + res;
        }
        return res
                .replaceAll("零[拾佰仟]", "零")
                .replaceAll("零+亿", "亿")
                .replaceAll("零+万", "万")
                .replaceAll("零+元", "元")
                .replaceAll("零+", "零");

    }

    //------------------------------------------------------------------------------------------------------------------判断

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断一个字符串是否含有数字
     */
    public static boolean isHasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断一个字符是否是中文
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    /**
     * 判断一个字符串是否含有中文
     */
    public static boolean isChinese(String str) {
        if (isEmpty(str)) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }

    /**
     * 判断是否都为大写
     */
    public static boolean isAllUpper(String str) {
        return str.matches("[A-Z]*");
    }

    /**
     * 判断是否都为小写
     */
    public static boolean isAllLower(String str) {
        return str.matches("[a-z]*");
    }

    /**
     * 判断一个字符串是否都为数字
     */
    public static boolean isDigit(String str) {
        return str.matches("[0-9]*");
    }

    /**
     * 判断去除第一位后续是否是驼峰命名规则
     */
    public static boolean isCamelCase(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String head = str.substring(0, 1);
        str = str.substring(1, str.length());
        int upper = 0;
        int lower = 0;
        int other = 0;
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (isAllUpper(s)) {
                upper = upper + 1;
            } else if (isAllLower(s)) {
                lower = lower + 1;
            } else {
                other = other + 1;
            }
        }
        if (other > 0) {
            return false;
        } else if ((upper + lower) == str.length()) {
            if (upper != 0 && lower != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是否包含表情
     */
    public static boolean isContainsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    /**
     * String 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * String 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 测试型校验
     */
    public static boolean isEmpty(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String) obj).isEmpty();
            } else if (obj instanceof Integer) {
                return false;
            } else if (obj instanceof Double) {
                return false;
            } else if (obj instanceof Boolean) {
                return false;
            } else if (obj instanceof BigDecimal) {
                return false;
            } else if (obj instanceof Date) {
                return false;
            } else if (obj instanceof Map) {
                return ((Map) obj).isEmpty();
            } else if (obj instanceof Collection) {
                return ((Collection) obj).isEmpty();
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 测试型校验
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    //------------------------------------------------------------------------------------------------------------------内部方法

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
     *
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private static String indent(int number) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < number; i++) {
            indent.append("   ");
        }
        return indent.toString();
    }

    /**
     * 是否空的
     */
    private static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 是否有Emoji表情
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
}
