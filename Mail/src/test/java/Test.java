import mail.SendMail;
import mail.set.Send;

public class Test {
    public static void main(String[] args) {
        SendMail sendMail = new SendMail(
                Send.create()
                        .setPersonal("格式测试")
                        .setStmp("smtp.sina.cn")
                        .setUserName("fuyongyuan277@sina.cn")
                        .setUserPassword("d0afada3105a3a39")
                        .setSendMail("fuyongyuan277@sina.cn").build()
        );

        sendMail.sendMail("格式测试验证码", "您好：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的验证码是：999999！", "360687243@qq.com");
    }
}
