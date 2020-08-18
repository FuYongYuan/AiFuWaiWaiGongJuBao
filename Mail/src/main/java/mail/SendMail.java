package mail;

import dispose.TextDispose;
import mail.exception.MailException;
import mail.set.Send;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 发送邮件逻辑类
 *
 * @author fyy
 */
public class SendMail {

    /**
     * 发送信息
     */
    private final Send send;

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
     * @param to      收信邮箱
     * @return String
     */
    public boolean sendMail(String theme, String content, String... to) {
        return this.sendMail(theme, content, Arrays.asList(to), null, null, null);
    }

    /**
     * 发送邮件
     *
     * @param theme   邮件主题
     * @param content 邮件内容
     * @param to      收信邮箱
     * @param cc      抄送邮箱
     * @return String
     */
    public boolean sendMail(String theme, String content, List<String> to, List<String> cc) {
        return this.sendMail(theme, content, to, cc, null, null);
    }

    /**
     * 发送邮件
     *
     * @param theme   邮件主题
     * @param content 邮件内容
     * @param to      收信邮箱
     * @param cc      抄送邮箱
     * @param bcc     密送邮箱
     * @return String
     */
    public boolean sendMail(String theme, String content, List<String> to, List<String> cc, List<String> bcc) {
        return this.sendMail(theme, content, to, cc, bcc, null);
    }

    /**
     * 发送邮件
     *
     * @param theme     邮件主题
     * @param content   邮件内容
     * @param to        收信邮箱
     * @param cc        抄送邮箱
     * @param bcc       密送邮箱
     * @param fileAffix 附件地址
     * @return String家庭经济管理系统
     */
    public boolean sendMail(String theme, String content, List<String> to, List<String> cc, List<String> bcc, String fileAffix) {
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
            //收信邮箱
            if (to != null && to.size() > 0) {
                sm.setTo(to);
            } else {
                throw new MailException("诊断：没有发送目标邮箱发送停止！", new NullPointerException());
            }
            //抄送邮箱
            if (cc != null && cc.size() > 0) {
                sm.setCc(cc);
            }
            //密送邮箱
            if (bcc != null && bcc.size() > 0) {
                sm.setBcc(bcc);
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
