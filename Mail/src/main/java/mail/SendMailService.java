package mail;

import dispose.TextDispose;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 发送邮件逻辑类
 */
public class SendMailService {
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
    private String send;

    private SendMailService() {

    }

    /**
     * 校验参数是否都初始化成功
     */
    private boolean checkParameter() {
        if (TextDispose.isEmpty(this.stmp)) {
            throw new MailException("<<<<<=====-----服务地址为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.userName)) {
            throw new MailException("<<<<<=====-----发送邮箱账号为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.userPassword)) {
            throw new MailException("<<<<<=====-----发送邮箱密码为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        if (TextDispose.isEmpty(this.send)) {
            throw new MailException("<<<<<=====-----发送邮箱为空创建发送邮件服务类失败!-----=====>>>>>");
        }
        return true;
    }

    /**
     * 创建发送邮件服务类
     *
     * @param fileName 配置文件地址
     * @return 发送邮件服务类
     */
    public static SendMailService create(String fileName) {
        SendMailService service = new SendMailService();
        try {
            InputStream in = SendMailService.class.getResourceAsStream(fileName);
            Properties p = new Properties();
            p.load(in);
            in.close();
            service.stmp = p.getProperty("STMP");
            service.userName = p.getProperty("SEND_USER_NAME");
            service.userPassword = p.getProperty("SEND_USER_PWD");
            service.send = p.getProperty("SEND");
            if (service.checkParameter()) {
                return service;
            }
        } catch (IOException e) {
            throw new MailException("<<<<<=====-----配置文件未读取到创建发送邮件服务类失败!-----=====>>>>>");
        }
        return null;
    }

    /**
     * 创建发送邮件服务类
     *
     * @param fileName     配置文件地址
     * @param stmp         服务地址名称
     * @param sendUserName 发送邮箱账号名称
     * @param sendUserPwd  发送邮箱密码名称
     * @param send         发送邮箱名称
     * @return 发送邮件服务类
     */
    public static SendMailService create(String fileName, String stmp, String sendUserName, String sendUserPwd, String send) {
        SendMailService service = new SendMailService();
        try {
            InputStream in = SendMailService.class.getResourceAsStream(fileName);
            Properties p = new Properties();
            p.load(in);
            in.close();
            service.stmp = p.getProperty(stmp);
            service.userName = p.getProperty(sendUserName);
            service.userPassword = p.getProperty(sendUserPwd);
            service.send = p.getProperty(send);
            if (service.checkParameter()) {
                return service;
            }
        } catch (IOException e) {
            throw new MailException("<<<<<=====-----配置文件未读取到创建发送邮件服务类失败!-----=====>>>>>");
        }
        return null;
    }

    /**
     * 创建发送邮件服务类
     *
     * @param stmp     服务地址
     * @param userName 发送邮箱账号
     * @param password 发送邮箱密码
     * @param send     发送邮箱
     * @return 发送邮件服务类
     */
    public static SendMailService create(String stmp, String userName, String password, String send) {
        SendMailService service = new SendMailService();
        service.stmp = stmp;
        service.userName = userName;
        service.userPassword = password;
        service.send = send;
        if (service.checkParameter()) {
            return service;
        } else {
            return null;
        }
    }

    /**
     * 发送邮件
     *
     * @param theme   邮件主题
     * @param content 邮件内容
     * @param eMail   收件人--收信邮箱
     * @return String
     */
    public boolean sendMail(String theme, String content, String eMail) {
        return this.sendMail(theme, content, eMail, null);
    }

    /**
     * 发送邮件
     *
     * @param theme     邮件主题
     * @param content   邮件内容
     * @param eMail     收件人--收信邮箱
     * @param fileAffix 附件地址
     * @return String
     */
    public boolean sendMail(String theme, String content, String eMail, String fileAffix) {
        try {
            //发送邮件
            SendMailEntity sm = new SendMailEntity(stmp);//服务地址
            sm.setNamePass(userName, userPassword);//发送邮箱账号 //发送邮箱密码
            if (TextDispose.isNotEmpty(theme)) {
                sm.setSubject(theme);//标题
            } else {
                sm.setSubject("无主题");
            }
            sm.setFrom(send);//发送邮箱
            if (TextDispose.isNotEmpty(eMail)) {
                sm.setTo(eMail);//接收邮箱
            } else {
                throw new MailException("<<<<<=====-----没有发送目标邮箱发送停止!-----=====>>>>>");
            }
            if (TextDispose.isNotEmpty(content)) {
                sm.setText(content);//内容
            } else {
                sm.setText("");
            }
            if (TextDispose.isNotEmpty(fileAffix)) {
                sm.addFileAffix(fileAffix);//附件
            }
            sm.createMimeMessage();
            sm.setOut();
            return true;
        } catch (Exception e) {
            throw new MailException("<<<<<=====-----发送邮件错误!-----=====>>>>>");
        }
    }
}
