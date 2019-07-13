package robot.model;

/**
 * 输入信息
 */
public class Perception {
    /**
     * 文本信息
     */
    private InputText inputText;

    /**
     * 图片信息
     */
    private InputImage inputImage;

    /**
     * 音频信息
     */
    private InputMedia inputMedia;

    /**
     * 客户端属性
     */
    private SelfInfo selfInfo;

    /**
     * 构造
     */
    public Perception() {
    }

    /**
     * 构造
     *
     * @param inputText 文本信息
     */
    public Perception(InputText inputText) {
        this.inputText = inputText;
    }

    /**
     * 构造
     *
     * @param inputImage 图片信息
     */
    public Perception(InputImage inputImage) {
        this.inputImage = inputImage;
    }

    /**
     * 构造
     *
     * @param inputMedia 音频信息
     */
    public Perception(InputMedia inputMedia) {
        this.inputMedia = inputMedia;
    }

    /**
     * 构造
     *
     * @param inputText 文本信息
     * @param selfInfo  客户端属性
     */
    public Perception(InputText inputText, SelfInfo selfInfo) {
        this.inputText = inputText;
        this.selfInfo = selfInfo;
    }

    /**
     * 构造
     *
     * @param inputImage 图片信息
     * @param selfInfo   客户端属性
     */
    public Perception(InputImage inputImage, SelfInfo selfInfo) {
        this.inputImage = inputImage;
        this.selfInfo = selfInfo;
    }

    /**
     * 构造
     *
     * @param inputMedia 音频信息
     * @param selfInfo   客户端属性
     */
    public Perception(InputMedia inputMedia, SelfInfo selfInfo) {
        this.inputMedia = inputMedia;
        this.selfInfo = selfInfo;
    }

    /**
     * 文本信息
     */
    public InputText getInputText() {
        return inputText;
    }

    /**
     * 文本信息
     */
    public void setInputText(InputText inputText) {
        this.inputText = inputText;
    }

    /**
     * 图片信息
     */
    public InputImage getInputImage() {
        return inputImage;
    }

    /**
     * 图片信息
     */
    public void setInputImage(InputImage inputImage) {
        this.inputImage = inputImage;
    }

    /**
     * 音频信息
     */
    public InputMedia getInputMedia() {
        return inputMedia;
    }

    /**
     * 音频信息
     */
    public void setInputMedia(InputMedia inputMedia) {
        this.inputMedia = inputMedia;
    }

    /**
     * 客户端属性
     */
    public SelfInfo getSelfInfo() {
        return selfInfo;
    }

    /**
     * 客户端属性
     */
    public void setSelfInfo(SelfInfo selfInfo) {
        this.selfInfo = selfInfo;
    }
}
