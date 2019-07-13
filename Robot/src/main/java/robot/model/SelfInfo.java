package robot.model;

/**
 * 客户端属性
 */
public class SelfInfo {
    /**
     * 地理位置信息
     */
    private Location location;

    /**
     * 构造
     */
    public SelfInfo(Location location) {
        this.location = location;
    }

    /**
     * 地理位置信息
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 地理位置信息
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
