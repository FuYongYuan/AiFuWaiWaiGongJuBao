package robot.model;

/**
 * 文本信息
 *
 * @author fyy
 */
public class InputText {
    /**
     * 直接输入文本
     */
    private String text;

    /**
     * 构造
     *
     * @param text 内容
     */
    public InputText(String text) {
        this.text = text;
    }

    /**
     * 直接输入文本
     */
    public String getText() {
        return text;
    }

    /**
     * 直接输入文本
     */
    public void setText(String text) {
        this.text = text;
    }
}
