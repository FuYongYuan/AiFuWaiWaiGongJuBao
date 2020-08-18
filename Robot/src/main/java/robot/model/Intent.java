package robot.model;

import java.util.Map;

/**
 * 请求意图
 *
 * @author fyy
 */
public class Intent {
    /**
     * 输出功能code
     */
    private int code;

    /**
     * 意图名称
     */
    private String intentName;

    /**
     * 意图动作名称
     */
    private String actionName;

    /**
     * 功能相关参数
     */
    private Map<String, String> parameters;

    /**
     * 输出功能code
     */
    public int getCode() {
        return code;
    }

    /**
     * 输出功能code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 意图名称
     */
    public String getIntentName() {
        return intentName;
    }

    /**
     * 意图名称
     */
    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    /**
     * 意图动作名称
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 意图动作名称
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * 功能相关参数
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * 功能相关参数
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
