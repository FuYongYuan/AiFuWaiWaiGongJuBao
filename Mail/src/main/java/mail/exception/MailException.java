package mail.exception;

import dispose.DateDispose;

import java.util.Date;

/**
 * 操作错误
 */
public class MailException extends RuntimeException {
    /**
     * 操作错误
     */
    public MailException(String s, Exception e) {
        super("[" + DateDispose.formatting_DateToString(new Date()) + "] -> " + s, e);
    }
}
