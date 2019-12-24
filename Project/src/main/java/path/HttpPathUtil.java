package path;

import javax.servlet.http.HttpServletRequest;

public class HttpPathUtil {
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
}
