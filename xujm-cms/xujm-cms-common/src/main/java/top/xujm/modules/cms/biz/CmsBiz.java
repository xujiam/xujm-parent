package top.xujm.modules.cms.biz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsCategory;
import top.xujm.modules.cms.model.CmsMenu;

import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 * 2018/10/24
 */
public interface CmsBiz {

    List<CmsCategory> getAllCategoryList();

    void delCmsCategory(int categoryId);

    void addCmsCategory(CmsCategory cmsCategory);

    List<CmsCategory> getCategoryListByParentId(int parentId);

    List<CmsMenu> getCmsMenuList(Byte isShow);

    Map<Integer,CmsCategory> getCmsCategoryMap();


}
