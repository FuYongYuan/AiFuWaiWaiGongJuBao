package robot.model;

/**
 * 输出结果集
 *
 * @author fyy
 */
public class Results {
    /**
     * 输出类型 (文本(text);连接(url);音频(voice);视频(video);图片(image);图文(news))
     */
    private String resultType;

    /**
     * ‘组’编号:0为独立输出，大于0时可能包含同组相关内容 (如：音频与文本为一组时说明内容一致)
     */
    private int groupType;

    /**
     * 输出值
     */
    private Values values;

    /**
     * 输出类型 (文本(text);连接(url);音频(voice);视频(video);图片(image);图文(news))
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * 输出类型 (文本(text);连接(url);音频(voice);视频(video);图片(image);图文(news))
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * ‘组’编号:0为独立输出，大于0时可能包含同组相关内容 (如：音频与文本为一组时说明内容一致)
     */
    public int getGroupType() {
        return groupType;
    }

    /**
     * ‘组’编号:0为独立输出，大于0时可能包含同组相关内容 (如：音频与文本为一组时说明内容一致)
     */
    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    /**
     * 输出值
     */
    public Values getValues() {
        return values;
    }

    /**
     * 输出值
     */
    public void setValues(Values values) {
        this.values = values;
    }
}
