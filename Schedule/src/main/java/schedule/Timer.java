package schedule;

import java.time.Duration;

public class Timer extends Schedule {

    private Timer() {
    }

    public static Timer duration(Duration interval) {
        Timer instance = new Timer();
        instance.interval = interval;
        return instance;
    }

    private Duration interval;

    public Duration getInterval() {
        return interval;
    }
}
