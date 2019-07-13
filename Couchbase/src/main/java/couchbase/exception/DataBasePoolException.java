package couchbase.exception;

/**
 * 数据连接池错误
 */
public class DataBasePoolException extends RuntimeException {
    /**
     * 数据库操作错误
     */
    public DataBasePoolException(String s) {
        super(s);
    }
}
