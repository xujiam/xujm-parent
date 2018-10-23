package top.xujm.modules.oss.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.core.utils.LibRandomUtil;
import top.xujm.modules.oss.common.FileTypeEnum;
import top.xujm.modules.oss.common.OssRemindException;
import top.xujm.modules.oss.common.OssResultEnum;
import top.xujm.modules.oss.model.Oss;
import top.xujm.modules.oss.model.OssLog;
import top.xujm.modules.oss.processor.OssProcessor;
import top.xujm.modules.oss.repository.OssLogRepository;
import top.xujm.modules.oss.repository.OssRepository;
import top.xujm.modules.oss.service.OssService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
@Component
public class OssServiceImpl implements OssService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, Oss> ossMap = new HashMap<>();

    @Autowired
    private OssRepository ossRepository;
    @Autowired
    private OssLogRepository ossLogRepository;
    @Autowired
    private OssProcessor ossProcessor;

    /**
     * 上传文件
     */
    @Override
    public OssLog uploadFile(InputStream fileStream, String fileSuffix, String fileTypeCode){
        Oss oss = getOssInfo(fileTypeCode);
        verifyFileType(oss.getFileType(),fileSuffix);
        String filePath = getFilePath(fileTypeCode,fileSuffix,oss.getDatePath());
        ossProcessor.uploadFile(fileStream,filePath);
        OssLog ossLog = new OssLog();
        ossLog.setFileUrl(filePath);
        ossLog.setTypeCode(fileTypeCode);
        ossLog.setAddTime(LibDateUtil.getNowTime());
        ossLogRepository.save(ossLog);
        return ossLog;
    }

    @Override
    public OssLog saveFile(String fileUrl) {
        InputStream inputStream = null;
        try {
            inputStream = new URL(fileUrl).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null){
            return null;
        }
        return uploadFile(inputStream,"jpg","avatar");
    }

    /**
     * 校验文件类型
     */
    private void verifyFileType(Byte fileType,String fileSuffix){
        FileTypeEnum fileTypeEnum = FileTypeEnum.getEnum(fileType);
        if(fileTypeEnum == null){
            throw new OssRemindException(OssResultEnum.FILE_TYPE_ERROR);
        }
        String[] format = StringUtils.split(fileTypeEnum.getFormat(),",");
        if(!Arrays.asList(format).contains(fileSuffix)){
            throw new OssRemindException(OssResultEnum.FILE_TYPE_ERROR);
        }
    }

    private String getFilePath(String fileTypeCode,String fileSuffix,String dateFormat){
        String datePath = LibDateUtil.getPatternFormat(dateFormat);
        return String.format("%s/%s/%s",fileTypeCode,datePath,getFileName(fileSuffix));
    }


    private String getFileName(String fileSuffix){
        return String.format("%d%s.%s",LibDateUtil.getNowMillis(), LibRandomUtil.randomStr(4),fileSuffix);
    }


    /**
     * 上传文件配置列表
     * */
    private Map<String, Oss> getOssList(){
        if(ossMap == null || ossMap.size() == 0){
            List<Oss> list = ossRepository.findAll();
            for (Oss info:list){
                ossMap.put(info.getTypeCode(),info);
            }
        }
        return ossMap;
    }

    /**
     * 获得oss信息
     */
    private Oss getOssInfo(String typeCode){
        Oss oss = getOssList().get(typeCode);
        if(oss == null){
            logger.error("getOssInfo:{}",typeCode);
            throw new OssRemindException(OssResultEnum.OSS_ERROR);
        }
        if(oss.getIsEnable() != 1){
            throw new RemindException(BaseResultEnum.FUNCTION_CLOSE);
        }
        return oss;
    }

    @Override
    public void refresh(){
        ossMap = new HashMap<>(0);
    }

}
