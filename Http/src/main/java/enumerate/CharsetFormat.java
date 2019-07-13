package enumerate;

/**
 * 编码格式
 */
public enum CharsetFormat {
    UTF_8("UTF-8"),
    GBK("GBK"),
    GB2312("GB2312"),
    ISO_8859_1("ISO-8859-1");


    private String value;

    CharsetFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
