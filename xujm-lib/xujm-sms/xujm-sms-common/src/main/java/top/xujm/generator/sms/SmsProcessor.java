package top.xujm.generator.sms;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;

import java.util.Map;

/**
 * @author Xujm
 * 短信处理器
 * 根据配置选取发送器
 */
@Component
public class SmsProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private Map<String,SmsSender> smsSenderMap;


    public void send(String mobile, String content, String areaCode){
        SmsConfig smsConfig = getSmsConfig();
        if(!smsConfig.isEnable()){
            throw new RemindException(BaseResultEnum.FUNCTION_CLOSE);
        }
        SmsSender smsSender = smsSenderMap.get(getSendBean(smsConfig));
        if(smsSender == null){
            logger.error("SmsProcessor:send:{}",smsConfig.getSendBean());
            throw new RemindException(BaseResultEnum.ERROR.getCode(),"SmsSender error");
        }
        smsSender.send(mobile,content,areaCode,smsConfig);
    }


    public SmsConfig getSmsConfig(){
        String smsConfig = ResourceConfig.getConfig("sms.account");
        return JSON.parseObject(smsConfig,SmsConfig.class);
    }


    private String getSendBean(SmsConfig smsConfig){
        return String.format("%s%s",smsConfig.getSendBean(),"SmsSender");
    }

}
