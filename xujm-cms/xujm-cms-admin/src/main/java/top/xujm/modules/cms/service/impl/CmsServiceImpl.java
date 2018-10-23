package top.xujm.modules.cms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsArticle;
import top.xujm.modules.cms.repository.CmsArticleEntityRepository;
import top.xujm.modules.cms.repository.CmsEntiryRepository;
import top.xujm.modules.cms.service.CmsService;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.user.service.impl.UserBaseService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

@Component
public class CmsServiceImpl extends UserBaseService implements CmsService {


    @PersistenceContext
    private EntityManager em;


    @Autowired
    private CmsEntiryRepository cmsEntiryRepository;
    @Autowired
    private CmsArticleEntityRepository cmsArticleEntityRepository;

    /**
     *  获取文章内容
     */
    @Override
    public ResultAdminData<List<Cms>> getCmsList(int page, int limit, int categoryId, String title, String addTime) {
        // 得到图片服务器的路径
        String configValue = ResourceConfig.getConfig("image_server");
        String sql = "select c from Cms c where 1=1 ";
        if (categoryId != 0) {
            sql += " and c.categoryId = " + categoryId;
        }
        if (StringUtils.isNotEmpty(title)) {
            sql += " and c.title like '%" +title + "%' ";
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and c.addTime >= " + times[0];
            sql += " and c.addTime <= " + times[1];
        }
        //创建原生SQL查询QUERY实例
        Query query =  em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;           //如果查询结果少于开始记录，则返回第一页显示
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        //执行查询，返回的是查询属性值数组的集合
        List<Cms> objecArraytList = query.getResultList();
        em.close();
        String cover = null;
        for(Cms info : objecArraytList) {
            cover = configValue + info.getCover();
            info.setCover(cover);
        }
        return new ResultAdminData<>(total,objecArraytList);
    }

    /**
     * 获取文章详情
     */
    @Override
    public ResultData<List<CmsArticle>> getCmsArticleByCmsId(int cmsId) {
        return new ResultData(cmsArticleEntityRepository.findAllByCmsId(cmsId));
    }

    /**
     * 预览文章(拼接链接)
     */
    @Override
    public ResultMsg previewCmsArticle(int cmsId, String langCode) {
        // 得到图片服务器的路径
        String configValue = ResourceConfig.getConfig("server_ip");
        String result = configValue + "cms/detail/" + cmsId + ".html?lang_code=" + langCode;
        //http://192.168.50.29/cms/detail/1.html?lang_code=en_US
        return new ResultMsg(0,result);
    }

    /**
     * 添加文章
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCms(String title, String description, String cover, String content, String template,Byte status,int categoryId) {
        Cms cms = new Cms();
        cms.setUserId(getLoginUserId());
        cms.setTitle(title);
        cms.setDescription(description);
        cms.setCover(cover);
        cms.setCategoryId(categoryId);
        cms.setStatus(status);
        cms.setTemplate(template);
        cms.setAddTime(LibDateUtil.getNowTime());
        Cms c= cmsEntiryRepository.save(cms);
        CmsArticle article = new CmsArticle();
        article.setCmsId(c.getCmsId());
        article.setLangCode("zh_CN");
        article.setTitle(title);
        article.setKeywords(title);
        article.setDescription(description);
        article.setContent(content);
        article.setExtend("");
        article.setIsEnable(status);
        article.setAddTime(LibDateUtil.getNowTime());
        cmsArticleEntityRepository.save(article);
    }

    /**
     * 获取语言配置
     */
    @Override
    public JSONObject getCmsLanguage() {
        String configValue = ResourceConfig.getConfig("cms.language");
        JSONObject result = LibSysUtil.getResultJSON(configValue);
        return result;
    }

    /**
     * 添加多篇文章
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCmses(String post_datas, String cover, String template, Byte status, int categoryId, String title, String description) {
        JSONArray result = JSONArray.parseArray(post_datas);
        // 添加文章主表
        Cms cms = new Cms();
        cms.setUserId(getLoginUserId());
        cms.setTitle(title);
        cms.setDescription(description);
        cms.setCover(cover);
        cms.setCategoryId(categoryId);
        cms.setStatus(status);
        cms.setTemplate(template);
        cms.setAddTime(LibDateUtil.getNowTime());
        Cms c= cmsEntiryRepository.save(cms);
        Iterator<Object> it = result.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            CmsArticle article = new CmsArticle();
            article.setCmsId(c.getCmsId());
            article.setKeywords(title);
            article.setDescription(description);
            article.setExtend("");
            article.setIsEnable(status);
            article.setAddTime(LibDateUtil.getNowTime());
            if(ob.getString("title")!=null){
                article.setTitle(ob.getString("title"));
            }
            if(ob.getString("description")!=null){
                article.setDescription(ob.getString("description"));
            }
            if(ob.getString("langCode")!=null){
                article.setLangCode(ob.getString("langCode"));
            }
            if(ob.getString("content")!=null){
                article.setContent(ob.getString("content"));
            }
            cmsArticleEntityRepository.save(article);
        }
    }

    /**
     * 根据文章ID和语言获取文章详情
     */
    @Override
    public ResultData<CmsArticle> getCmsArticleByCmsIdandLang(int cmsId, String langCode) {
        CmsArticle result = cmsArticleEntityRepository.findByCmsIdAndLangCode(cmsId, langCode);
        return new ResultData(result);
    }

    /**
     * 修改多篇文章
     */
    @Override
    public void updateCmses(String post_datas, String cover, String template, Byte status, int categoryId, String title, String description, int cmsId) {
        JSONArray result = JSONArray.parseArray(post_datas);
        System.out.println(result);
        // 修改文章主表
        Cms cms = cmsEntiryRepository.findById(cmsId).get();
        cms.setTitle(title);
        cms.setDescription(description);
        cms.setCover(cover);
        cms.setCategoryId(categoryId);
        cms.setAddTime(LibDateUtil.getNowTime());
        Cms c= cmsEntiryRepository.save(cms);
        CmsArticle article;
        Iterator<Object> it = result.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            if(ob.getString("langCode")!=null){
                article = cmsArticleEntityRepository.findByCmsIdAndLangCode(cmsId,ob.getString("langCode"));
                if (article != null) {
                    if(ob.getString("title")!=null){
                        article.setTitle(ob.getString("title"));
                    }
                    if(ob.getString("description")!=null){
                        article.setDescription(ob.getString("description"));
                    }
                    if(ob.getString("content")!=null){
                        article.setContent(ob.getString("content"));
                    }
                }else {
                    article = new CmsArticle();
                    article.setCmsId(c.getCmsId());
                    article.setKeywords(title);
                    article.setDescription(description);
                    article.setExtend("");
                    article.setIsEnable(status);
                    article.setAddTime(LibDateUtil.getNowTime());
                    if(ob.getString("title")!=null){
                        article.setTitle(ob.getString("title"));
                    }
                    if(ob.getString("description")!=null){
                        article.setDescription(ob.getString("description"));
                    }
                    if(ob.getString("langCode")!=null){
                        article.setLangCode(ob.getString("langCode"));
                    }
                    if(ob.getString("content")!=null){
                        article.setContent(ob.getString("content"));
                    }
                }
                cmsArticleEntityRepository.save(article);
            }
        }
    }

    /**
     * 根据ID获取文章信息
     */
    @Override
    public ResultData<Cms> getCmsByCmsId(int cmsId) {
        return new ResultData(cmsEntiryRepository.findById(cmsId).get());
    }

}
