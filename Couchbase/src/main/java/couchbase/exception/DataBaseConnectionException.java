package couchbase.exception;

/**
 * 数据连接池错误
 */
public class DataBaseConnectionException extends RuntimeException {
    /**
     * 数据库操作错误
     */
    public DataBaseConnectionException(String s) {
        super(s);
    }
}
