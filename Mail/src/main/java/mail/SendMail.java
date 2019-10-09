package mail;

import dispose.TextDispose;
import mail.exception.MailException;
import mail.set.Send;

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
            //发送邮件
            SendMailService sm = new SendMailService(send.getStmp());//服务地址
            sm.setNamePass(send.getUserName(), send.getUserPassword());//发送邮箱账号 //发送邮箱密码
            if (TextDispose.isNotEmpty(theme)) {
                sm.setSubject(theme);//标题
            } else {
                sm.setSubject("无主题");
            }
            sm.setFrom(send.getSendMail());//发送邮箱
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
            sm.createMimeMessage(send.getPersonal());
            sm.setOut();
            return true;
        } catch (Exception e) {
            throw new MailException("<<<<<=====-----发送邮件错误!-----=====>>>>>");
        }
    }
}
