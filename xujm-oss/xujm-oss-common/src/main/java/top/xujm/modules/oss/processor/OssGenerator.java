package top.xujm.modules.oss.processor;

import java.io.InputStream;

/**
 * @author Xujm
 */
public interface OssGenerator {

    /**
     * 上传文件
     * @param inputStream 文件流
     * @param filePath 文件名
     * @param ossConfig 上传配置
     */
    void uploadFile(InputStream inputStream, String filePath, OssConfig ossConfig);

    /**
     * 上传视频
     * @param inputStream 文件流
     * @param filePath 文件名
     * @param ossConfig 上传配置
     */
    void uploadVideo(InputStream inputStream, String filePath, OssConfig ossConfig);



}
