package robot.model;

import java.util.List;

/**
 * 机器人出参
 */
public class RobotOut {
    /**
     * 请求意图
     */
    private Intent intent;
    /**
     * 输出结果集
     */
    private List<Results> results;

    /**
     * 请求意图
     */
    public Intent getIntent() {
        return intent;
    }

    /**
     * 请求意图
     */
    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    /**
     * 输出结果集
     */
    public List<Results> getResults() {
        return results;
    }

    /**
     * 输出结果集
     */
    public void setResults(List<Results> results) {
        this.results = results;
    }
}
