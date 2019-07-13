package log.model;

/**
 * 日志总数统计VM
 */
public class LogSumModel {
    /**
     * 访问IP
     */
    private String requestIP;
    /**
     * 访问接口
     */
    private String interfaceURI;
    /**
     * 次数
     */
    private int sum;

    public String getRequestIP() {
        return requestIP;
    }

    public void setRequestIP(String requestIP) {
        this.requestIP = requestIP;
    }

    public String getInterfaceURI() {
        return interfaceURI;
    }

    public void setInterfaceURI(String interfaceURI) {
        this.interfaceURI = interfaceURI;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
