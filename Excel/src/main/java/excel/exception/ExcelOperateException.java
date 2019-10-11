package excel.exception;

import dispose.DateDispose;

import java.util.Date;

/**
 * 操作错误
 */
public class ExcelOperateException extends RuntimeException {
    /**
     * 操作错误
     */
    public ExcelOperateException(String s, Exception e) {
        super("[" + DateDispose.formatting_DateToString(new Date()) + "] -> " + s, e);
    }
}
