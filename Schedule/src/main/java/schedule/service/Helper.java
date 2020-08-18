package schedule.service;

import java.time.Duration;

/**
 * 获取毫秒
 *
 * @author fyy
 */
class Helper {

    public static int getMilliseconds(Duration d) {
        return (int) (d.getSeconds() * 1000 + d.getNano() / 1000000);
    }
}
