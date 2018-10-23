package top.xujm.modules.oss.processor.generator;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.xujm.modules.oss.common.OssRemindException;
import top.xujm.modules.oss.common.OssResultEnum;
import top.xujm.modules.oss.processor.OssConfig;
import top.xujm.modules.oss.processor.OssGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Xujm
 */
@Component
public class AliYunOssGenerator implements OssGenerator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 上传图片
     */
    @Override
    public void uploadFile(InputStream inputStream, String filePath, OssConfig ossConfig){
        upload(inputStream,filePath,ossConfig.getFileBucketName(),ossConfig);
    }

    /**
     * 上传视频
     */
    @Override
    public void uploadVideo(InputStream inputStream, String filePath, OssConfig ossConfig){
        upload(inputStream,filePath,ossConfig.getVideoBucketName(),ossConfig);
    }


    private void upload(InputStream inputStream, String filePath, String bucketName,OssConfig ossConfig){
        OSSClient ossClient = createOSSClient(ossConfig);
        try {
            ossClient.putObject(bucketName, filePath, inputStream);
        } catch (OSSException oe) {
            logger.error("AliYunOssGenerator:upload:{},{},{},{}"
                    ,oe.getHostId(),oe.getRequestId(),oe.getErrorCode(),oe.getMessage());
            throw new OssRemindException(OssResultEnum.UPLOAD_ERROR);
        } catch (Exception ce) {
            logger.error("AliYunOssGenerator:upload:{}",ce.getMessage());
            throw new OssRemindException(OssResultEnum.UPLOAD_ERROR);
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 创建储存实例
     */
    private OSSClient createOSSClient(OssConfig ossConfig) {
        String endpoint = "http://oss-cn-hongkong.aliyuncs.com";
        return new OSSClient(ossConfig.getEndpoint(),ossConfig.getAccessId(),ossConfig.getAccessSecret());
    }



}
