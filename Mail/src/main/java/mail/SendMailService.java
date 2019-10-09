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
import java.io.UnsupportedEncodingException;
import java.util.Properties;

class SendMailService {
    /**
     * 发信邮箱
     */
    private String from = "";
    /**
     * 收信邮箱
     */
    public String to = "";
    /**
     * 邮件主题
     */
    public String subject = "";
    /**
     * 账号
     */
    private String username = "";
    /**
     * 密码
     */
    private String password = "";
    /**
     * 邮件内容
     */
    public String text = "";
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
    public SendMailService(String stmp) {
        setSmtpHost(stmp);
    }

    /**
     * 创建传输Host
     */
    public void setSmtpHost(String smtpHostName) {
        if (props == null) {
            //创建Properties对象
            props = new Properties();
        }
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发信邮箱的smtp地址"smtp.sina.com"
        props.put("mail.smtp.host", smtpHostName);
        //验证
        props.setProperty("mail.smtp.auth", "true");
    }

    /**
     * 创建邮件
     *
     * @return 成功失败
     */
    public boolean createMimeMessage(String personal) {
        Authenticator auth = new AjavaAuthenticator(username, password); // 使用验证，创建一个Authenticator
        Session session = Session.getDefaultInstance(props, auth);// 根据Properties，Authenticator创建Session

        try {
            if (message == null) {
                message = new MimeMessage(session);// Message存储发送的电子邮件信息
            }
            InternetAddress setfrom = new InternetAddress(from, personal);
            message.setFrom(setfrom);    //设置发件人
            //message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));// 设置收信邮箱
            message.setSubject(subject);// 设置主题
            return true;
        } catch (MessagingException e) {
            throw new MailException("<<<<<=====-----发送邮件错误!-----=====>>>>>");
        } catch (UnsupportedEncodingException e) {
            throw new MailException("<<<<<=====-----发送邮件错误!-----=====>>>>>");
        }
    }

    /**
     * 发送邮件
     *
     * @return 成功失败
     */
    public boolean setOut() {
        try {
            message.setContent(mp);
            message.saveChanges();
            System.out.println("[INFO] 开始发送...");
            //发送
            Transport.send(message);
            System.out.println("[INFO] 发送完成!");
            return true;
        } catch (Exception e) {
            throw new MailException("<<<<<=====-----发送邮件错误!-----=====>>>>>");
        }
    }

    /**
     * 设置用户名密码
     *
     * @param name 用户名
     * @param pass 密码
     */
    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }

    /**
     * 设置标题
     *
     * @param mailSubject 标题
     * @return 成功失败
     */
    public boolean setSubject(String mailSubject) {
        try {
            if (TextDispose.isNotEmpty(mailSubject)) {
                subject = mailSubject;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 发送邮箱
     *
     * @param from 邮箱地址
     * @return 成功失败
     */
    public boolean setFrom(String from) {
        try {
            this.from = from;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 接收邮箱
     *
     * @param to 邮箱地址
     * @return 成功失败
     */
    public boolean setTo(String to) {
        if (TextDispose.isEmpty(to)) {
            return false;
        }
        try {
            this.to = to;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置发送内容
     *
     * @param text 发送内容
     * @return 成功失败
     */
    public boolean setText(String text) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Context-Type context=text/html;charset=utf-8>" + text, "text/html;charset=UTF-8");
            if (mp == null) {
                mp = new MimeMultipart();
            }
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 添加附件..
     *
     * @param filename 附件地址
     * @return 成功失败
     */
    public boolean addFileAffix(String filename) {
        System.out.println("增加附件..");
        if (mp == null) {
            mp = new MimeMultipart();
        }
        if (TextDispose.isEmpty(filename)) {
            return false;
        }
        String[] files = filename.split(";");
        System.out.println("你有 " + files.length + " 个附件!");
        try {
            for (String file : files) {
                BodyPart bp = new MimeBodyPart();
                FileDataSource fileds = new FileDataSource(file);
                bp.setDataHandler(new DataHandler(fileds));
                bp.setFileName(fileds.getName());
                mp.addBodyPart(bp);
            }
            return true;
        } catch (Exception e) {
            throw new MailException("增加附件: " + filename + "--faild!" + e);
        }
    }

    /**
     * 创建传入身份验证信息的 Authenticator类
     */
    class AjavaAuthenticator extends Authenticator {
        private String user;
        private String pwd;

        public AjavaAuthenticator(String user, String pwd) {
            this.user = user;
            this.pwd = pwd;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pwd);
        }
    }

}
