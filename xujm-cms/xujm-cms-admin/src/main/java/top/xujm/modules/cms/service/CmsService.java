package top.xujm.modules.cms.service;

import com.alibaba.fastjson.JSONObject;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsArticle;
import top.xujm.modules.common.model.ResultAdminData;

import java.util.List;
import java.util.Map;

public interface CmsService {

    ResultAdminData<List<Cms>> getCmsList(int page, int limit, int categoryId, String title, String addTime);

    ResultData<List<CmsArticle>> getCmsArticleByCmsId(int cmsId);

    ResultMsg previewCmsArticle(int cmsId, String langCode);

    void addCms(String title, String description, String cover, String content, String template, Byte status, int categoryId);

    JSONObject getCmsLanguage();

    void addCmses(String post_datas, String cover, String template, Byte status, int categoryId, String title, String description);

    ResultData<CmsArticle> getCmsArticleByCmsIdandLang(int cmsId, String langCode);

    void updateCmses(String post_datas, String cover, String template, Byte status, int categoryId, String title, String description, int cmsId);

    ResultData<Cms> getCmsByCmsId(int cmsId);
}
