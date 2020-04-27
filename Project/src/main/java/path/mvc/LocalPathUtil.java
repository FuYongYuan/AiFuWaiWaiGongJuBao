package path.mvc;

import java.io.File;
import java.util.Objects;

public class LocalPathUtil {
    /**
     * 项目Class目录
     */
    public static String getClassPath() {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        path = path.replace('/', File.separatorChar); // 将/换成\
        path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
        path = path.substring(0, path.length() - 1);//去掉最后一个斜杆
        path = path.replaceAll("%20", " ");
        return path;
    }

    /**
     * 项目调用文件目录
     */
    public static String getFilePath() {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).toString();
        path = path.replace('/', File.separatorChar); // 将/换成\
        path = path.replace("file:", ""); //去掉file:
        path = path.replace("classes\\", ""); //去掉classes\
        path = path.replace("target\\", ""); //去掉target\
        path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
        path = path.substring(0, path.length() - 1);//去掉最后一个斜杆
        path = path.replaceAll("%20", " ");
        return path;
    }
}
