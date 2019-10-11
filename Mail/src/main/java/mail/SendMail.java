package mail;

import dispose.TextDispose;
import mail.exception.MailException;
import mail.set.Send;

import java.io.IOException;

/**
 * 发送邮件逻辑类
 */
public class SendMail {

    /**
     * 发送信息
     */
    private Send send;

    /**
     * 创建发送邮件服务类
     *
     * @param send 发送信息
     */
    public SendMail(Send send) {
        this.send = send;
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
     * @return String家庭经济管理系统
     */
    public boolean sendMail(String theme, String content, String eMail, String fileAffix) {
        try {
            //发送邮件对象并初始化服务地址
            SendMailService sm = new SendMailService(send.getStmp());
            //发送邮箱
            sm.setFrom(send.getSendMail());
            //发送邮箱账号 //发送邮箱密码
            sm.setNamePass(send.getUserName(), send.getUserPassword());
            //标题
            if (TextDispose.isNotEmpty(theme)) {
                sm.setSubject(theme);
            } else {
                sm.setSubject("无主题");
            }
            //接收邮箱
            if (TextDispose.isNotEmpty(eMail)) {
                sm.setTo(eMail);
            } else {
                throw new MailException("诊断：没有发送目标邮箱发送停止！", new NullPointerException());
            }
            //内容
            if (TextDispose.isNotEmpty(content)) {
                sm.setText(content);
            } else {
                sm.setText("");
            }
            //附件
            if (TextDispose.isNotEmpty(fileAffix)) {
                if (!sm.addFileAffix(fileAffix)) {
                    throw new MailException("诊断：附件上传失败请检查附件地址！", new IOException());
                }
            }
            //发送
            if (sm.createMimeMessage(send.getPersonal())) {
                return sm.setOut();
            }
            return false;
        } catch (Exception e) {
            throw new MailException("诊断：发送邮件错误！", e);
        }
    }
}
