package couchbase.exception;

import dispose.DateDispose;

import java.util.Date;

/**
 * 操作错误
 */
public class DataBaseOperateException extends RuntimeException {
    /**
     * 数据库操作错误
     */
    public DataBaseOperateException(String s, Exception e) {
        super("[" + DateDispose.formatting_DateToString(new Date()) + "] -> " + s, e);
    }
}
