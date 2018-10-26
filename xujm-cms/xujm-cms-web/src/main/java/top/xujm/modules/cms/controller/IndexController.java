package top.xujm.modules.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.annotations.ApiIgnore;
import top.xujm.core.base.WebBaseController;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.core.page.CmsPage;
import top.xujm.modules.cms.biz.CmsBiz;
import top.xujm.modules.cms.service.CmsService;
import top.xujm.modules.config.biz.PlatformBiz;

@Controller
public class IndexController extends WebBaseController {

    @Autowired
    private CmsBiz cmsBiz;
    @Autowired
    private CmsService cmsService;
    @Autowired
    private PlatformBiz platformBiz;


    @GetMapping("/")
    public String index(@ApiIgnore ModelMap model,CmsPage cmsPage){
        addModel(model);
        model.addAttribute("articleList",cmsService.getNewCmsList(cmsPage));
        return getTemplate("index");
    }

    @PostMapping("/cms/search")
    public String search(@ApiIgnore ModelMap model,String keyword,CmsPage cmsPage){
        addModel(model);
        model.addAttribute("articleList",cmsService.getSearchCmsList(keyword,cmsPage));
        return getTemplate("index");
    }

    @GetMapping("/ie")
    public String ie(){
        return getTemplate("ie");
    }

    @GetMapping("/cms/detail/{cmsId}.html")
    public String detail(@ApiIgnore ModelMap model, @PathVariable("cmsId")int cmsId){
        addModel(model);
        model.addAttribute("articleInfo",cmsService.getArticleDetail(cmsId));
        return getTemplate("detail");
    }

    @GetMapping("/cms/category/article/{categoryId}.html")
    public String categoryArticle(@ApiIgnore ModelMap model, @PathVariable("categoryId")int categoryId, CmsPage cmsPage){
        addModel(model);
        model.addAttribute("articleList",cmsService.getCategoryArticleList(categoryId,cmsPage));
        return getTemplate("index");
    }

    private void addModel(ModelMap model){
        model.addAttribute("siteName","随风逸");
        model.addAttribute("template","default");
        model.addAttribute("sliderList",platformBiz.getAdvList("home",BooleanTypeEnum.TRUE));
        model.addAttribute("menuList",cmsBiz.getCmsMenuList(BooleanTypeEnum.TRUE.getType()));
        model.addAttribute("linkList",platformBiz.getPlatformLinkList(BooleanTypeEnum.TRUE));
    }

}
