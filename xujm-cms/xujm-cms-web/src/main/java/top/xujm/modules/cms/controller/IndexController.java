package top.xujm.modules.cms.controller;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;
import top.xujm.core.base.WebBaseController;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.core.page.CmsPage;
import top.xujm.modules.cms.biz.CmsBiz;
import top.xujm.modules.cms.service.CmsService;

@Controller
public class IndexController extends WebBaseController {

    @Autowired
    private CmsBiz cmsBiz;
    @Autowired
    private CmsService cmsService;


    @GetMapping("/")
    public String index(@ApiIgnore ModelMap model,CmsPage cmsPage){
        addModel(model);
        model.addAttribute("articleList",cmsService.getNewCmsList(cmsPage));
        return getTemplate("index");
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
        model.addAttribute("sliderList",null);
        model.addAttribute("menuList",cmsBiz.getCmsMenuList(BooleanTypeEnum.TRUE.getType()));
    }

}
