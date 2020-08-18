package excel.exception;

import dispose.DateDispose;

import java.util.Date;

/**
 * 操作错误
 *
 * @author fyy
 */
public class ExcelOperateException extends RuntimeException {
    /**
     * 操作错误
     */
    public ExcelOperateException(String s, Exception e) {
        super("[" + DateDispose.formattingDateToString(new Date()) + "] -> " + s, e);
    }
}
