package top.xujm.modules.cms.service;

import top.xujm.core.page.CmsPage;
import top.xujm.core.page.SlicePage;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsArticle;

/**
 * @author Xujm
 * 2018/10/25
 */
public interface CmsService {

    SlicePage<Cms> getNewCmsList(CmsPage cmsPage);

    CmsArticle getArticleDetail(int cmsId);

    SlicePage<Cms> getCategoryArticleList(int categoryId, CmsPage cmsPage);

    SlicePage<Cms> getSearchCmsList(String keyword,CmsPage cmsPage);
}
