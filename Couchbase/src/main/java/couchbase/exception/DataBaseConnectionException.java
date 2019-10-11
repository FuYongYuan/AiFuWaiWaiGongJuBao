package couchbase.exception;

import dispose.DateDispose;

import java.util.Date;

/**
 * 数据连接池错误
 */
public class DataBaseConnectionException extends RuntimeException {
    /**
     * 数据库操作错误
     */
    public DataBaseConnectionException(String s, Exception e) {
        super("[" + DateDispose.formatting_DateToString(new Date()) + "] -> " + s, e);
    }
}
