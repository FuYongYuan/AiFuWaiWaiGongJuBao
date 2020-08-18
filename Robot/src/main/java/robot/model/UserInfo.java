package robot.model;

/**
 * 用户参数
 *
 * @author fyy
 */
public class UserInfo {
    /**
     * 机器人标识
     */
    private String apiKey;

    /**
     * 用户唯一标识
     */
    private String userId;

    /**
     * 群聊唯一标识
     */
    private String groupId;

    /**
     * 群内用户昵称
     */
    private String userIdName;

    /**
     * 构造
     */
    public UserInfo(String apiKey, String userId) {
        this.apiKey = apiKey;
        this.userId = userId;
    }

    /**
     * 构造
     */
    public UserInfo(String apiKey, String userId, String groupId, String userIdName) {
        this.apiKey = apiKey;
        this.userId = userId;
        this.groupId = groupId;
        this.userIdName = userIdName;
    }

    /**
     * 机器人标识
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * 机器人标识
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 用户唯一标识
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户唯一标识
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 群聊唯一标识
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 群聊唯一标识
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 群内用户昵称
     */
    public String getUserIdName() {
        return userIdName;
    }

    /**
     * 群内用户昵称
     */
    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }
}
