package top.xujm.modules.oss.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import top.xujm.common.core.model.ResultData;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.oss.model.OssLog;
import top.xujm.modules.oss.service.OssService;

import java.io.*;

/**
 * @author Xujm
 */
@Api(value="/oss", tags="储存模块")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type_code",value = "文件类型")
    })
    @RequestMapping(value = "/upload/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<OssLog> uploadFile(@RequestParam MultipartFile file, @RequestParam("type_code")String typeCode){
        String fileSuffix = StringUtils.substringAfter(file.getOriginalFilename(),".");
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultData<>(ossService.uploadFile(inputStream,fileSuffix,typeCode));
    }

    @ApiOperation("保存线上文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileUrl",value = "文件地址")
    })
    @PostMapping("/upload/saveOnLineFile")
    public JSONObject saveOnLineFile(String fileUrl){
        OssLog ossLog = ossService.saveFile(fileUrl);
        if(ossLog == null){
            return null;
        }
        return LibBeanUtil.entityToJsonObject(ossLog);
    }


    @ApiIgnore
    @RequestMapping("/upload/refresh")
    public JSONObject refresh(){
        ossService.refresh();
        return LibSysUtil.getResultJSON();
    }

}
