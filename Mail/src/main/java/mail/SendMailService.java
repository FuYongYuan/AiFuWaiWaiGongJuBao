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
import java.util.List;
import java.util.Properties;

class SendMailService {
    /**
     * 发信邮箱
     */
    private String from = "";
    /**
     * 收信邮箱
     */
    private List<String> to;
    /**
     * 抄送邮箱
     */
    private List<String> cc;
    /**
     * 密送邮箱
     */
    private List<String> bcc;
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
        // 使用验证，创建一个Authenticator
        Authenticator auth = new AjavaAuthenticator(this.username, this.password);
        // 根据Properties，Authenticator创建Session
        Session session = Session.getDefaultInstance(this.props, auth);

        try {
            if (this.message == null) {
                // Message存储发送的电子邮件信息
                this.message = new MimeMessage(session);
            }
            //设置发件人
            InternetAddress setfrom = new InternetAddress(this.from, personal);
            this.message.setFrom(setfrom);
            //设置收信邮箱
            if (this.to != null && this.to.size() > 0) {
                InternetAddress[] toInternetAddress = new InternetAddress[this.to.size()];
                for (int i = 0; i < this.to.size(); i++) {
                    toInternetAddress[i] = new InternetAddress(this.to.get(i));
                }
                this.message.setRecipients(Message.RecipientType.TO, toInternetAddress);
            }
            if (this.cc != null && this.cc.size() > 0) {
                InternetAddress[] ccInternetAddress = new InternetAddress[this.cc.size()];
                for (int i = 0; i < this.cc.size(); i++) {
                    ccInternetAddress[i] = new InternetAddress(this.cc.get(i));
                }
                this.message.setRecipients(Message.RecipientType.CC, ccInternetAddress);
            }
            if (this.bcc != null && this.bcc.size() > 0) {
                InternetAddress[] bccInternetAddress = new InternetAddress[this.bcc.size()];
                for (int i = 0; i < this.bcc.size(); i++) {
                    bccInternetAddress[i] = new InternetAddress(this.bcc.get(i));
                }
                this.message.setRecipients(Message.RecipientType.BCC, bccInternetAddress);
            }
            //设置主题
            this.message.setSubject(this.subject);
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
            System.out.println("[Mail-INFO] 开始发送邮件 -> " + this.from
                    + " 至 收件人：" + (this.to == null ? "无" : this.to)
                    + " 与 抄送人：" + (this.cc == null ? "无" : this.cc)
                    + " 与 密送人：" + (this.bcc == null ? "无" : this.bcc)
            );
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
     * 收信邮箱
     *
     * @param to 邮箱地址
     */
    void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * 抄送邮箱
     *
     * @param cc 邮箱地址
     */
    void setCc(List<String> cc) {
        this.cc = cc;
    }

    /**
     * 密送邮箱
     *
     * @param bcc 邮箱地址
     */
    void setBcc(List<String> bcc) {
        this.bcc = bcc;
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
        private final String user;
        private final String pwd;

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
