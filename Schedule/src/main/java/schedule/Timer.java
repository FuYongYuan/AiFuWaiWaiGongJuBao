package schedule;

import java.time.Duration;

/**
 * 每个*执行
 *
 * @author fyy
 */
public class Timer extends AbstractSchedule {

    /**
     * 无需构造封闭 new 通过 at 创建
     */
    private Timer() {
    }

    /**
     * 设置间隔周期
     * @param interval 间隔时间
     */
    public static Timer duration(Duration interval) {
        Timer instance = new Timer();
        instance.interval = interval;
        return instance;
    }

    private Duration interval;

    /**
     * 间隔时间
     */
    public Duration getInterval() {
        return interval;
    }
}
