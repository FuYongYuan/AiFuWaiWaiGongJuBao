package enumerate;

/**
 * 语言简写名称
 */
public enum LanguageShortName {
    Auto("auto"),
    English("en"),
    Chinese("zh-CN"),
    Japanese("ja"),
    Chinese_Taiwan("zh-TW"),
    Dutch("nl"),
    French("fr"),
    German("de"),
    Swiss("de-CH"),
    Finnish("fi"),
    Iceland("is"),;


    private String value;

    LanguageShortName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
