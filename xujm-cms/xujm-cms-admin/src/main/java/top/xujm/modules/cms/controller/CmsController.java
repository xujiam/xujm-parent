package top.xujm.modules.cms.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.modules.cms.biz.CmsBiz;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsArticle;
import top.xujm.modules.cms.model.CmsCategory;
import top.xujm.modules.cms.service.CmsService;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.oss.model.OssLog;
import top.xujm.modules.oss.service.OssService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ZhengYP
 */
@RestController
@RequestMapping("/cms")
public class CmsController {

    @Autowired
    private CmsService cmsService;
    @Autowired
    private CmsBiz cmsBiz;

    @Autowired
    private OssService ossService;

    @GetMapping("/category/list")
    public ResultData<List<CmsCategory>> getCmsCategoryList(){
        return new ResultData<>(cmsBiz.getAllCategoryList());
    }

    @PostMapping("/category/del")
    public ResultMsg deleteCmsCategory(int categoryId){
        cmsBiz.delCmsCategory(categoryId);
        return new ResultMsg();
    }

    @PostMapping("/category/add")
    public ResultMsg deleteCmsCategory(CmsCategory cmsCategory){
        cmsBiz.addCmsCategory(cmsCategory);
        return new ResultMsg();
    }

    @GetMapping("/category/plist")
    public ResultData<List<CmsCategory>> getCategoryListByParentId(int parentId){
        return new ResultData<>(cmsBiz.getCategoryListByParentId(parentId));
    }
    /**
     * 获取股票热点
     */
    @GetMapping("/stockhotspot/list")
    public ResultAdminData<List<Cms>> stockHotSpotList(int page, int limit, int categoryId, String title, String addTime) {
        return cmsService.getCmsList(page, limit, categoryId, title, addTime);
    }

    /**
     * 获取系统说明
     */
    @GetMapping("/sysdesc/list")
    public ResultAdminData<List<Cms>> sysdescList(int page,int limit,int categoryId, String title, String addTime) {
        return cmsService.getCmsList(page, limit, categoryId, title, addTime);
    }

    /**
     * 获取文章详情
     */
    @GetMapping("getCmsArticleByCmsId")
    public ResultData<List<CmsArticle>> getCmsArticleByCmsId(int cmsId) {
        return cmsService.getCmsArticleByCmsId(cmsId);
    }

    /**
     * 预览文章(拼接链接)
     */
    @GetMapping("previewCmsArticle")
    public ResultMsg previewCmsArticle(int cmsId, String langCode) {
        return cmsService.previewCmsArticle(cmsId, langCode);
    }

    /**
     * 上传文章封面
     */
    /**
     * 预览文章(拼接链接)
     */
    @PostMapping("upload")
    public ResultMsg upload(MultipartFile file) {
        System.out.println(file);
        return null;
    }

    /**
     * 添加文章
     */
    @PostMapping("addCms")
    public ResultMsg addCms(String title, String description, String cover, String content, String template, Byte status, int categoryId) {
        cmsService.addCms(title, description, cover, content, template ,status, categoryId);
        return new ResultMsg();
    }

    /**
     * 获取语言配置
     */
    @GetMapping("getCmsLanguage")
    public JSONObject getCmsLanguage() {
        return cmsService.getCmsLanguage();
    }

    /**
     * 添加多篇文章
     */
    @PostMapping("addCmses")
    public ResultMsg addCmses(String post_datas, String cover, String template, Byte status, int categoryId, String title, String description) {
        cmsService.addCmses(post_datas, cover, template, status, categoryId, title, description );
        return new ResultMsg();
    }

    /**
     * 根据文章ID和语言获取文章详情
     */
    @GetMapping("getCmsArticleByCmsIdandLang")
    public ResultData<CmsArticle> getCmsArticleByCmsIdandLangCode(int cmsId, String langCode) {
        return cmsService.getCmsArticleByCmsIdandLang(cmsId, langCode);
    }

    /**
     * 修改多篇文章
     */
    @PostMapping("updateCmses")
    public ResultMsg updateCmses(String post_datas, String cover, String template, Byte status, int categoryId, String title, String description, int cmsId) {
        cmsService.updateCmses(post_datas, cover, template, status, categoryId, title, description, cmsId);
        return new ResultMsg();
    }

    /**
     * 根据ID获取文章信息
     */
    @GetMapping("getCmsByCmsId")
    public ResultData<Cms> getCmsByCmsId(int cmsId) {
        return cmsService.getCmsByCmsId(cmsId);
    }

    @RequestMapping(
            value = {"/upload/file"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    public JSONObject uploadFile(@RequestParam MultipartFile upload, @RequestParam("type_code") String typeCode) {
        String fileSuffix = StringUtils.substringAfter(upload.getOriginalFilename(), ".");
        InputStream inputStream = null;
        try {
            inputStream = upload.getInputStream();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        /*{"fileName":"veer-153463034.jpg","uploaded":1,"url":"\/userfiles\/files\/Public%20Folder\/veer-153463034.jpg"}*/
        OssLog o = this.ossService.uploadFile(inputStream, fileSuffix, typeCode);
        JSONObject j = new JSONObject();
        j.put("fileName",o.getFileId());
        j.put("uploaded",1);
        String configValue = ResourceConfig.getConfig("image_server");
        j.put("url",configValue+o.getFileUrl());
        return j;
    }
}
