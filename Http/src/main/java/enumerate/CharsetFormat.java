package enumerate;

/**
 * 编码格式
 *
 * @author fyy
 */
public enum CharsetFormat {

    /**
     * UTF-8
     */
    UTF_8("UTF-8"),

    /**
     * GBK
     */
    GBK("GBK"),

    /**
     * GB2312
     */
    GB2312("GB2312"),

    /**
     * ISO-8859-1
     */
    ISO_8859_1("ISO-8859-1");

    /**
     * 实际字符串
     */
    private final String value;

    CharsetFormat(String value) {
        this.value = value;
    }

    /**
     * 获取实际字符串
     */
    public String getValue() {
        return value;
    }

}
