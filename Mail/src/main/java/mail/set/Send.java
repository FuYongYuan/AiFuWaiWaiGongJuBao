package mail.set;

import dispose.TextDispose;
import mail.exception.MailException;

public class Send {
    /**
     * 系统名称
     */
    private String personal;
    /**
     * 服务地址
     */
    private String stmp;
    /**
     * 发送邮箱用户名
     */
    private String userName;
    /**
     * 发送邮箱密码
     */
    private String userPassword;
    /**
     * 发送邮箱
     */
    private String sendMail;

    /**
     * 创建
     */
    public static Send create() {
        return new Send();
    }

    /**
     * 初始化
     */
    public Send build() {
        if (TextDispose.isEmpty(this.personal)) {
            throw new MailException("<<<<<=====-----系统名称为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.stmp)) {
            throw new MailException("<<<<<=====-----服务地址为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.userName)) {
            throw new MailException("<<<<<=====-----发送邮箱账号为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.userPassword)) {
            throw new MailException("<<<<<=====-----发送邮箱密码为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.sendMail)) {
            throw new MailException("<<<<<=====-----发送邮箱为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        return this;
    }

    /**
     * 系统名称
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * 系统名称
     */
    public Send setPersonal(String personal) {
        this.personal = personal;
        return this;
    }

    /**
     * 服务地址
     */
    public String getStmp() {
        return stmp;
    }

    /**
     * 服务地址
     */
    public Send setStmp(String stmp) {
        this.stmp = stmp;
        return this;
    }

    /**
     * 发送邮箱用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 发送邮箱用户名
     */
    public Send setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * 发送邮箱密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 发送邮箱密码
     */
    public Send setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    /**
     * 发送邮箱
     */
    public String getSendMail() {
        return sendMail;
    }

    /**
     * 发送邮箱
     */
    public Send setSendMail(String sendMail) {
        this.sendMail = sendMail;
        return this;
    }
}
