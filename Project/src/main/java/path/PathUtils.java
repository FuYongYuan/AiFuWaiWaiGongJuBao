package path;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;

public class PathUtils {
    /**
     * resources目录
     */
    public static String getResourcesPath(HttpServletRequest request) {

        String path = request.getContextPath();
        String homePath = request.getScheme() + "://" + request.getServerName();
        homePath = request.getServerPort() == 80 ? homePath : homePath + ":"
                + request.getServerPort();
        String basePath = homePath + path;
        return basePath + "/resources";
    }

    /**
     * 项目根路径
     */
    public static String getPath(HttpServletRequest request) {

        String path = request.getContextPath();
        String homePath = request.getScheme() + "://" + request.getServerName();
        homePath = request.getServerPort() == 80 ? homePath : homePath + ":"
                + request.getServerPort();
        return homePath + path;
    }

    /**
     * 项目名称
     */
    public static String getProjectName(HttpServletRequest request) {
        return request.getContextPath();
    }

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

    /**
     * 获取访问者ip地址
     */
    public static String getVisitorIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本机IP
     */
    public static String getHostIP() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && !ip.getHostAddress().contains(":")) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}