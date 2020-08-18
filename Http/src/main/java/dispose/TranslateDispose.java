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
 *
 * @author fyy
 */
public class TranslateDispose {
    /**
     * 翻译地址
     */
    private static final String URL_TEMPLATE = "http://translate.google.cn/?langpair={0}&text={1}";

    /**
     * 结果集ID
     */
    private static final String RESULT_BOX = "result_box";


    /**
     * 自动检测来源内容格式-翻译
     *
     * @param text           内容
     * @param targetLanguage 目标语言
     */
    public static String translate(String text, LanguageShortName targetLanguage) {
        return translate(text, LanguageShortName.Auto, targetLanguage, CharsetFormat.UTF_8);
    }

    /**
     * 自动检测来源内容格式-翻译
     *
     * @param text           内容
     * @param targetLanguage 目标语言
     * @param format         格式
     */
    public static String translate(String text, LanguageShortName targetLanguage, CharsetFormat format) {
        return translate(text, LanguageShortName.Auto, targetLanguage, format);
    }

    /**
     * 自动检测来源内容格式-翻译
     *
     * @param text           内容
     * @param sourceLang     来源语言
     * @param targetLanguage 目标语言
     */
    public static String translate(String text, LanguageShortName sourceLang, LanguageShortName targetLanguage) {
        return translate(text, sourceLang, targetLanguage, CharsetFormat.UTF_8);
    }

    /**
     * 翻译从某语言到某语言
     *
     * @param text           内容
     * @param sourceLang     来源语言
     * @param targetLanguage 目标语言
     * @param format         格式
     */
    public static String translate(String text, LanguageShortName sourceLang, LanguageShortName targetLanguage, CharsetFormat format) {
        try {
            String url = MessageFormat.format(URL_TEMPLATE, URLEncoder.encode(sourceLang.getValue() + "|" + targetLanguage.getValue(), format.getValue()), URLEncoder.encode(text, format.getValue()));
            String html = HttpRequestTool.doGet(url);
            Document doc = Jsoup.parse(html);
            Element ele = doc.getElementById(RESULT_BOX);
            return ele.text();
        } catch (Exception e) {
            return null;
        }
    }
}
