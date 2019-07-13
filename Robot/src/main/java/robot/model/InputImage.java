package robot.model;

/**
 * 图片信息
 */
public class InputImage {
    /**
     * 图片地址
     */
    private String url;

    /**
     * 构造
     *
     * @param url 地址
     */
    public InputImage(String url) {
        this.url = url;
    }

    /**
     * 图片地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 图片地址
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
