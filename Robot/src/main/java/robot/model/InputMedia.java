package robot.model;

/**
 * 音频信息
 */
public class InputMedia {
    /**
     * 音频地址
     */
    private String url;

    /**
     * 构造
     *
     * @param url 地址
     */
    public InputMedia(String url) {
        this.url = url;
    }

    /**
     * 音频地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 音频地址
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
