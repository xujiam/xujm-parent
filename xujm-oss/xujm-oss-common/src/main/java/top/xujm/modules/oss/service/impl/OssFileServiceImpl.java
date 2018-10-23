package top.xujm.modules.oss.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.facade.file.FileService;
import top.xujm.modules.oss.service.OssService;

/**
 * @author Xujm
 * 2018/8/22
 */
@Component
public class OssFileServiceImpl implements FileService {

    @Autowired
    private OssService ossService;

    @Override
    public JSONObject saveFile(String fileUrl) {
        return LibBeanUtil.entityToJsonObject(ossService.saveFile(fileUrl));
    }

}
