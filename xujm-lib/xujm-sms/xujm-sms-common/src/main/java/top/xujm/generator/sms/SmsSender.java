package top.xujm.generator.sms;

/**
 * @author Xujm
 * 短信验证码发送器
 * 处理发送渠道接口
 */
public interface SmsSender {

    /**
     * 发送短信
     * @param mobile 手机号
     * @param content 内容
     * @param areaCode 区号
     * @param smsConfig 配置
     */
    void send(String mobile, String content, String areaCode, SmsConfig smsConfig);

}
