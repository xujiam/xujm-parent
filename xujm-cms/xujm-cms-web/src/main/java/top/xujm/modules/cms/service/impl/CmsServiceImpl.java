package top.xujm.modules.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import top.xujm.core.page.CmsPage;
import top.xujm.core.page.SlicePage;
import top.xujm.modules.cms.common.CmsStatusEnum;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsArticle;
import top.xujm.modules.cms.repository.CmsArticleWebRepository;
import top.xujm.modules.cms.repository.CmsRepository;
import top.xujm.modules.cms.repository.CmsWebRepository;
import top.xujm.modules.cms.service.CmsService;

/**
 * @author Xujm
 * 2018/10/25
 */
@Component
public class CmsServiceImpl implements CmsService {

    @Autowired
    private CmsWebRepository cmsWebRepository;
    @Autowired
    private CmsArticleWebRepository cmsArticleWebRepository;

    @Override
    public SlicePage<Cms> getNewCmsList(CmsPage cmsPage){
        Pageable pageable = new PageRequest(cmsPage.getPage(),cmsPage.getSize(), Sort.Direction.DESC,"cmsId");
        return new SlicePage<>(cmsWebRepository.findAllByStatus(CmsStatusEnum.NORMAL.getStatus(),pageable));
    }

    @Override
    public SlicePage<Cms> getCategoryArticleList(int categoryId,CmsPage cmsPage){
        Pageable pageable = new PageRequest(cmsPage.getPage(),cmsPage.getSize(), Sort.Direction.DESC,"cmsId");
        return new SlicePage<>(cmsWebRepository.findAllByCategoryIdAndStatus(categoryId,CmsStatusEnum.NORMAL.getStatus(),pageable));
    }

    @Override
    public CmsArticle getArticleDetail(int cmsId){
        return cmsArticleWebRepository.selectCmsArticleInfo(cmsId,"zh_CN");
    }

}
