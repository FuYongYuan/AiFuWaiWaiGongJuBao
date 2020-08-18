package robot.model;

/**
 * 地理位置信息
 *
 * @author fyy
 */
public class Location {
    /**
     * 所在城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 街道
     */
    private String street;

    /**
     * 构造
     *
     * @param city 城市
     */
    public Location(String city) {
        this.city = city;
    }

    /**
     * 构造
     *
     * @param city   城市
     * @param street 街道
     */
    public Location(String city, String street) {
        this.city = city;
        this.street = street;
    }

    /**
     * 构造
     *
     * @param city     城市
     * @param province 省份
     * @param street   街道
     */
    public Location(String city, String province, String street) {
        this.city = city;
        this.province = province;
        this.street = street;
    }

    /**
     * 所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 所在城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 街道
     */
    public String getStreet() {
        return street;
    }

    /**
     * 街道
     */
    public void setStreet(String street) {
        this.street = street;
    }
}
