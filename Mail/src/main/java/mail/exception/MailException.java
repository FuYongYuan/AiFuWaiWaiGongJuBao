package mail.exception;

import dispose.DateDispose;

import java.util.Date;

/**
 * 操作错误
 *
 * @author fyy
 */
public class MailException extends RuntimeException {
    /**
     * 操作错误
     */
    public MailException(String s, Exception e) {
        super("[" + DateDispose.formattingDateToString(new Date()) + "] -> " + s, e);
    }
}
