package top.xujm.modules.oss.processor.generator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.xujm.modules.oss.processor.OssConfig;
import top.xujm.modules.oss.processor.OssGenerator;

import java.io.*;

/**
 * @author Xujm
 * 2018/10/25
 */
@Component
public class LocalOssGenerator implements OssGenerator {

    private Logger logger = LoggerFactory.getLogger(LocalOssGenerator.class);

    @Override
    public void uploadFile(InputStream inputStream, String filePath, OssConfig ossConfig) {
        try {
            boolean flag = true;
            File file = new File(getSavePath(filePath));
            if (!file.exists()) {
                 flag = file.mkdirs();
            }
            if(flag){
                // 指定要写入的图片
                FileOutputStream out = new FileOutputStream(filePath);
                // 每次读取的字节长度
                int n;
                // 存储每次读取的内容
                byte[] bb = new byte[1024];
                while ((n = inputStream.read(bb)) != -1) {
                    // 将读取的内容，写入到输出流当中
                    out.write(bb, 0, n);
                }
                out.close();// 关闭输入输出流
                inputStream.close();
            }else {
                logger.error("创建文件目录失败");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void uploadVideo(InputStream inputStream, String filePath, OssConfig ossConfig) {

    }

    private String getSavePath(String filePath){
        String[] pathArr = StringUtils.split(filePath,"/");
        return StringUtils.replace(filePath,pathArr[pathArr.length-1],"");
    }
}
