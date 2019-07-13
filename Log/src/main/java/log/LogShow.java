package log;

import dispose.DateDispose;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 日志中输出详细错误
 */
public class LogShow {
    /**
     * 输出详细信息
     *
     * @param e 错误
     * @return 详细信息
     */
    public static String output(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return "[" + DateDispose.formatting_DateToString(new Date()) + "] -> " + sw.toString();
    }

    /**
     * 输出详细信息
     *
     * @param promptContent 提示内容
     * @param e             错误
     * @return 详细信息
     */
    public static String output(String promptContent, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return "[" + DateDispose.formatting_DateToString(new Date()) + "] -> " + promptContent + "\n" + sw.toString();
    }
}
