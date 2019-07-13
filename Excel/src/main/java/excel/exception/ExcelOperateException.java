package excel.exception;

/**
 * 操作错误
 */
public class ExcelOperateException extends RuntimeException {
    /**
     * 数据库操作错误
     */
    public ExcelOperateException(String s) {
        super(s);
    }
}
