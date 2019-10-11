package mail;

import dispose.TextDispose;
import mail.exception.MailException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

class SendMailService {
    /**
     * 发信邮箱
     */
    private String from = "";
    /**
     * 收信邮箱
     */
    private String to = "";
    /**
     * 邮件主题
     */
    private String subject = "";
    /**
     * 账号
     */
    private String username = "";
    /**
     * 密码
     */
    private String password = "";
    /**
     * 存放邮件的title 内容和附件
     */
    private Multipart mp;
    /**
     * Properties对象
     */
    private Properties props;
    /**
     * Message存储发送的电子邮件信息
     */
    private Message message;

    /**
     * 构造发送邮件实体
     *
     * @param stmp 服务地址
     */
    SendMailService(String stmp) {
        setSmtpHost(stmp);
    }

    /**
     * 创建传输Host
     */
    private void setSmtpHost(String smtpHostName) {
        if (this.props == null) {
            //创建Properties对象
            this.props = new Properties();
        }
        //设置传输协议
        this.props.setProperty("mail.transport.protocol", "smtp");
        //设置发信邮箱的smtp地址"smtp.sina.com"
        this.props.put("mail.smtp.host", smtpHostName);
        //验证
        this.props.setProperty("mail.smtp.auth", "true");
    }

    /**
     * 创建邮件
     *
     * @return 成功失败
     */
    boolean createMimeMessage(String personal) {
        Authenticator auth = new AjavaAuthenticator(this.username, this.password); // 使用验证，创建一个Authenticator
        Session session = Session.getDefaultInstance(this.props, auth);// 根据Properties，Authenticator创建Session

        try {
            if (this.message == null) {
                this.message = new MimeMessage(session);// Message存储发送的电子邮件信息
            }
            InternetAddress setfrom = new InternetAddress(this.from, personal);
            this.message.setFrom(setfrom);    //设置发件人
            //message.setFrom(new InternetAddress(from));
            this.message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.to));// 设置收信邮箱
            this.message.setSubject(this.subject);// 设置主题
            return true;
        } catch (Exception e) {
            throw new MailException("诊断：发送邮件错误！", e);
        }
    }

    /**
     * 发送邮件
     *
     * @return 成功失败
     */
    boolean setOut() {
        try {
            this.message.setContent(this.mp);
            this.message.saveChanges();
            System.out.println("[Mail-INFO] 开始发送邮件 -> " + this.from + " 至 " + this.to);
            //发送
            Transport.send(this.message);
            System.out.println("[Mail-INFO] 发送邮件完成！");
            return true;
        } catch (Exception e) {
            throw new MailException("诊断：发送邮件错误！", e);
        }
    }

    /**
     * 设置用户名密码
     *
     * @param name 用户名
     * @param pass 密码
     */
    void setNamePass(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    /**
     * 设置标题
     *
     * @param mailSubject 标题
     */
    void setSubject(String mailSubject) {
        this.subject = mailSubject;
    }

    /**
     * 发送邮箱
     *
     * @param from 邮箱地址
     */
    void setFrom(String from) {
        this.from = from;
    }

    /**
     * 接收邮箱
     *
     * @param to 邮箱地址
     */
    void setTo(String to) {
        this.to = to;
    }

    /**
     * 设置发送内容
     *
     * @param text 发送内容
     */
    void setText(String text) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Context-Type context=text/html;charset=utf-8>" + text, "text/html;charset=UTF-8");
            if (this.mp == null) {
                this.mp = new MimeMultipart();
            }
            this.mp.addBodyPart(bp);
        } catch (Exception e) {
            throw new MailException("诊断：发送内容： " + text + " 错误！", e);
        }
    }

    /**
     * 添加附件..
     *
     * @param filename 附件地址
     * @return 成功失败
     */
    boolean addFileAffix(String filename) {
        System.out.println("开始增加附件");
        if (this.mp == null) {
            this.mp = new MimeMultipart();
        }
        if (TextDispose.isEmpty(filename)) {
            return false;
        }
        String[] files = filename.split(";");
        System.out.println("你有 " + files.length + " 个附件！");
        try {
            for (String file : files) {
                BodyPart bp = new MimeBodyPart();
                FileDataSource fileds = new FileDataSource(file);
                bp.setDataHandler(new DataHandler(fileds));
                bp.setFileName(fileds.getName());
                this.mp.addBodyPart(bp);
            }
            return true;
        } catch (Exception e) {
            throw new MailException("诊断：增加附件： " + filename + " 失败！", e);
        }
    }

    /**
     * 创建传入身份验证信息的 Authenticator类
     */
    static class AjavaAuthenticator extends Authenticator {
        private String user;
        private String pwd;

        AjavaAuthenticator(String user, String pwd) {
            this.user = user;
            this.pwd = pwd;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pwd);
        }
    }

}
