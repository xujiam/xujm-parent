package top.xujm.modules.oss.processor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.modules.oss.common.OssConstants;
import top.xujm.modules.oss.common.OssRemindException;
import top.xujm.modules.oss.common.OssResultEnum;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Xujm
 */
@Component
public class OssProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private Map<String,OssGenerator> ossGeneratorMap;

    public void uploadFile(InputStream fileStream,String filePath){
        OssConfig ossConfig = getOssConfig();
        if(!ossConfig.isEnable()){
            throw new RemindException(BaseResultEnum.FUNCTION_CLOSE);
        }
        OssGenerator ossGenerator = getOssGenerator(ossConfig);
        if(ossGenerator == null){
            logger.error("OssProcessor:uploadFile:{}",ossConfig.getDealBean());
            throw new OssRemindException(OssResultEnum.GENERATOR_ERROR);
        }
        ossGenerator.uploadFile(fileStream,filePath,ossConfig);
    }

    private OssGenerator getOssGenerator(OssConfig ossConfig){
        if(ossGeneratorMap == null){
            throw new OssRemindException(OssResultEnum.GENERATOR_ERROR);
        }
        return ossGeneratorMap.get(getOssBean(ossConfig.getDealBean()));
    }


    private OssConfig getOssConfig(){
        String ossConfig = ResourceConfig.getConfig(OssConstants.OSS_CONFIG);
        return JSON.parseObject(ossConfig,OssConfig.class);
    }


    private String getOssBean(String dealBean){
        return String.format("%s%s",dealBean,"OssGenerator");
    }

}
