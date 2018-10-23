package top.xujm.modules.oss.service;

import top.xujm.modules.oss.model.OssLog;

import java.io.InputStream;

/**
 * @author Xujm
 */
public interface OssService {

    /**
     * 上传文件
     * @param fileStream 文件流
     * @param fileSuffix 文件后缀
     * @param fileTypeCode 文件类型
     * @return OssLog
     */
    OssLog uploadFile(InputStream fileStream, String fileSuffix, String fileTypeCode);

    OssLog saveFile(String fileUrl);
    /**
     * 刷新
     */
    void refresh();

}
