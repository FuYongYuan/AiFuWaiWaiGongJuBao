package robot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import http.HttpRequestTool;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import robot.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 机器人服务
 */
public class RobotService {

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 创建服务 需要初始化用户信息
     */
    public RobotService(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    //------------------------------------------------------------------------------------------------------------------请求方法

    /**
     * 简易传入文本传出文本
     *
     * @param inContent 内容
     * @return 返回机器人出参
     */
    public String robot(String inContent) throws IOException {
        Perception perception = new Perception(new InputText(inContent));
        RobotIn in = new RobotIn(0, perception, this.userInfo);
        RobotOut robotOut = this.robot(in);
        if (judgeCode(robotOut.getIntent().getCode())) {
            if (robotOut.getResults() != null && robotOut.getResults().size() > 0) {
                if (robotOut.getResults().get(0).getValues() != null) {
                    return robotOut.getResults().get(0).getValues().getText();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 根据类型信息入参
     *
     * @param inContent 内容
     * @return 返回机器人出参
     */
    public RobotOut robot(int reqType, String inContent) throws IOException {
        Perception perception = new Perception();
        switch (reqType) {
            case 0:
                perception.setInputText(new InputText(inContent));
            case 1:
                perception.setInputImage(new InputImage(inContent));
            case 2:
                perception.setInputMedia(new InputMedia(inContent));
        }
        RobotIn in = new RobotIn(0, perception, this.userInfo);
        return this.robot(in);
    }

    /**
     * 文本信息
     *
     * @param inputText 文本内容
     * @return 返回机器人出参
     */
    public RobotOut robot(InputText inputText) throws IOException {
        Perception perception = new Perception(inputText);
        RobotIn in = new RobotIn(0, perception, this.userInfo);
        return this.robot(in);
    }

    /**
     * 图片信息
     *
     * @param inputImage 图片内容
     * @return 返回机器人出参
     */
    public RobotOut robot(InputImage inputImage) throws IOException {
        Perception perception = new Perception(inputImage);
        RobotIn in = new RobotIn(1, perception, this.userInfo);
        return this.robot(in);
    }

    /**
     * 音频信息
     *
     * @param inputMedia 音频内容
     * @return 返回机器人出参
     */
    public RobotOut robot(InputMedia inputMedia) throws IOException {
        Perception perception = new Perception(inputMedia);
        RobotIn in = new RobotIn(2, perception, this.userInfo);
        return this.robot(in);
    }

    /**
     * 机器人
     *
     * @param reqType    内容类型
     * @param perception 内容
     * @return 返回机器人出参
     */
    public RobotOut robot(int reqType, Perception perception) throws IOException {
        RobotIn in = new RobotIn(reqType, perception, this.userInfo);
        return this.robot(in);
    }

    /**
     * 机器人
     *
     * @param in 入参内容
     * @return 返回机器人出参
     */
    public RobotOut robot(RobotIn in) throws IOException {
        String inJsonString = JSON.toJSONString(in);
        HttpPost httpPost = new HttpPost(RobotStaticParameter.RobotUrl);
        StringEntity se = new StringEntity(inJsonString, StandardCharsets.UTF_8);
        httpPost.setEntity(se);
        httpPost.setHeader(HTTP.CONTENT_TYPE, RobotStaticParameter.CONTENT_TYPE);
        String content = HttpRequestTool.doPost(httpPost);
        if (content != null) {
            RobotOut robotOut = JSONObject.parseObject(content, RobotOut.class);
            if (judgeCode(robotOut.getIntent().getCode())) {
                return robotOut;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------内部方法

    /**
     * 判断code是否有问题
     *
     * @param code 状态码
     */
    private static boolean judgeCode(int code) {
        switch (code) {
            case 5000:
                System.out.println("无解析结果");
                return false;
            case 6000:
                System.out.println("暂不支持该功能");
                return false;
            case 4000:
                System.out.println("请求参数格式错误");
                return false;
            case 4001:
                System.out.println("加密方式错误");
                return false;
            case 4002:
                System.out.println("无功能权限");
                return false;
            case 4003:
                System.out.println("该apikey没有可用请求次数");
                return false;
            case 4005:
                System.out.println("无功能权限");
                return false;
            case 4007:
                System.out.println("apikey不合法");
                return false;
            case 4100:
                System.out.println("userid获取失败");
                return false;
            case 4200:
                System.out.println("上传格式错误");
                return false;
            case 4300:
                System.out.println("批量操作超过限制");
                return false;
            case 4400:
                System.out.println("没有上传合法userid");
                return false;
            case 4500:
                System.out.println("userid申请个数超过限制");
                return false;
            case 4600:
                System.out.println("输入内容为空");
                return false;
            case 4602:
                System.out.println("输入文本内容超长(上限150)");
                return false;
            case 7002:
                System.out.println("上传信息失败");
                return false;
            case 8008:
                System.out.println("服务器错误");
                return false;
            case 0:
                System.out.println("上传成功");
                return true;
            default:
                return true;
        }
    }
}
