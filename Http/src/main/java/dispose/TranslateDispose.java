package dispose;

import enumerate.CharsetFormat;
import enumerate.LanguageShortName;
import http.HttpRequestTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * 翻译工具
 */
public class TranslateDispose {
    /**
     * 翻译地址
     */
    private static final String url_template = "http://translate.google.cn/?langpair={0}&text={1}";

    /**
     * 结果集ID
     */
    private static final String result_box = "result_box";


    /**
     * 自动检测来源内容格式-翻译
     *
     * @param text            内容
     * @param target_language 目标语言
     */
    public static String translate(String text, LanguageShortName target_language) {
        return translate(text, LanguageShortName.Auto, target_language, CharsetFormat.UTF_8);
    }

    /**
     * 自动检测来源内容格式-翻译
     *
     * @param text            内容
     * @param target_language 目标语言
     * @param format          格式
     */
    public static String translate(String text, LanguageShortName target_language, CharsetFormat format) {
        return translate(text, LanguageShortName.Auto, target_language, format);
    }

    /**
     * 自动检测来源内容格式-翻译
     *
     * @param text            内容
     * @param source_lang     来源语言
     * @param target_language 目标语言
     */
    public static String translate(String text, LanguageShortName source_lang, LanguageShortName target_language) {
        return translate(text, source_lang, target_language, CharsetFormat.UTF_8);
    }

    /**
     * 翻译从某语言到某语言
     *
     * @param text            内容
     * @param source_lang     来源语言
     * @param target_language 目标语言
     * @param format          格式
     */
    public static String translate(String text, LanguageShortName source_lang, LanguageShortName target_language, CharsetFormat format) {
        try {
            String url = MessageFormat.format(url_template, URLEncoder.encode(source_lang.getValue() + "|" + target_language.getValue(), format.getValue()), URLEncoder.encode(text, format.getValue()));
            String html = HttpRequestTool.doGet(url);
            Document doc = Jsoup.parse(html);
            Element ele = doc.getElementById(result_box);
            return ele.text();
        } catch (Exception e) {
            return null;
        }
    }
}
