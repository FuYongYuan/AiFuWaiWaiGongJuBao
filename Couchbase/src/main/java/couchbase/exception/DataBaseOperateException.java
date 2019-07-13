package couchbase.exception;

/**
 * 操作错误
 */
public class DataBaseOperateException extends RuntimeException {
    /**
     * 数据库操作错误
     */
    public DataBaseOperateException(String s) {
        super(s);
    }
}
