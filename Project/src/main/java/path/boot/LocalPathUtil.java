package path.boot;

import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * 获取boot项目的绝对目录地址
 *
 * @author fyy
 */
public class LocalPathUtil {
    /**
     * 反斜杠
     */
    private static final String BACKSLASH = "\"";
    /**
     * 斜杠
     */
    private static final String SLASH = "/";

    /**
     * 项目Class目录
     */
    public static String getClassPath() {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        // 将/换成\
        path = path.replace('/', File.separatorChar);
        if (path.startsWith(BACKSLASH) || path.startsWith(String.valueOf(File.separatorChar))) {
            //去掉第一个\,如 \D:\JavaWeb...
            path = path.substring(1);
        }
        if (path.endsWith(SLASH) || path.endsWith(String.valueOf(File.separatorChar))) {
            //去掉最后一个斜杆
            path = path.substring(0, path.length() - 1);
        }
        path = path.replaceAll("%20", " ");
        return path;
    }

    /**
     * 项目调用文件目录
     */
    public static String getFilePath() {
        String classpath = "classpath:";
        ClassLoader cl = getDefaultClassLoader();
        URL url = cl != null ? cl.getResource(classpath) : ClassLoader.getSystemResource(classpath);
        String path = "";
        if (url != null) {
            path = new File(url.getPath()).getAbsolutePath();
        }
        // 将/换成\
        path = path.replace('/', File.separatorChar);
        //去掉file:
        path = path.replace("file:", "");
        //去掉classes\
        path = path.replace("classes\\", "");
        //去掉target\
        path = path.replace("target\\", "");
        if (path.startsWith(BACKSLASH) || path.startsWith(String.valueOf(File.separatorChar))) {
            //去掉第一个\,如 \D:\JavaWeb...
            path = path.substring(1);
        }
        if (path.endsWith(SLASH) || path.endsWith(String.valueOf(File.separatorChar))) {
            //去掉最后一个斜杆
            path = path.substring(0, path.length() - 1);
        }
        path = path.replaceAll("%20", " ");
        return path;
    }

    /**
     * 获取默认类位置
     */
    private static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ignored) {
        }
        if (cl == null) {
            cl = LocalPathUtil.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ignored) {
                }
            }
        }
        return cl;
    }
}
