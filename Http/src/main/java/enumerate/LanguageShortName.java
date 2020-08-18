package enumerate;

/**
 * 语言简写名称
 *
 * @author fyy
 */
public enum LanguageShortName {

    /**
     * 自动
     */
    Auto("auto"),

    /**
     * 英语
     */
    English("en"),

    /**
     * 简体中文
     */
    Chinese("zh-CN"),

    /**
     * 日语
     */
    Japanese("ja"),

    /**
     * 繁体中文
     */
    Chinese_Taiwan("zh-TW"),

    /**
     * 荷兰语
     */
    Dutch("nl"),

    /**
     * 法语
     */
    French("fr"),

    /**
     * 德语
     */
    German("de"),

    /**
     * 瑞士语
     */
    Swiss("de-CH"),

    /**
     * 芬兰语
     */
    Finnish("fi"),

    /**
     * 冰岛
     */
    Iceland("is"),
    ;

    /**
     * 实际字符串
     */
    private final String value;

    LanguageShortName(String value) {
        this.value = value;
    }

    /**
     * 获取实际字符串
     */
    public String getValue() {
        return value;
    }
}
