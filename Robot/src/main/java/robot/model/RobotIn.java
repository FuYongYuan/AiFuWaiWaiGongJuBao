package robot.model;

/**
 * 机器人入参
 */
public class RobotIn {
    /**
     * 输入类型:0-文本(默认)、1-图片、2-音频
     */
    private int reqType;

    /**
     * 输入信息
     */
    private Perception perception;

    /**
     * 用户参数
     */
    private UserInfo userInfo;

    /**
     * 构造
     */
    public RobotIn() {
    }

    /**
     * 构造
     *
     * @param reqType    输入类型:0-文本(默认)、1-图片、2-音频
     * @param perception 输入信息
     * @param userInfo   用户参数
     */
    public RobotIn(int reqType, Perception perception, UserInfo userInfo) {
        this.reqType = reqType;
        this.perception = perception;
        this.userInfo = userInfo;
    }

    /**
     * 输入类型:0-文本(默认)、1-图片、2-音频
     */
    public int getReqType() {
        return reqType;
    }

    /**
     * 输入类型:0-文本(默认)、1-图片、2-音频
     */
    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    /**
     * 输入信息
     */
    public Perception getPerception() {
        return perception;
    }

    /**
     * 输入信息
     */
    public void setPerception(Perception perception) {
        this.perception = perception;
    }

    /**
     * 用户参数
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 用户参数
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
