package mail;

/**
 * 操作错误
 */
class MailException extends RuntimeException {
    /**
     * 操作错误
     */
    public MailException(String s) {
        super(s);
    }
}
