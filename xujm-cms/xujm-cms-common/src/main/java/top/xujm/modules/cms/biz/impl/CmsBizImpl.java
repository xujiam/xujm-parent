package top.xujm.modules.cms.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.cms.biz.CmsBiz;
import top.xujm.modules.cms.model.Cms;
import top.xujm.modules.cms.model.CmsArticle;
import top.xujm.modules.cms.model.CmsCategory;
import top.xujm.modules.cms.model.CmsMenu;
import top.xujm.modules.cms.repository.CmsCategoryRepository;
import top.xujm.modules.cms.repository.CmsLabelRepository;
import top.xujm.modules.cms.repository.CmsMenuRepository;
import top.xujm.modules.cms.repository.CmsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 * 2018/10/24
 */
@Component
public class CmsBizImpl implements CmsBiz {

    @Autowired
    private CmsCategoryRepository cmsCategoryRepository;
    @Autowired
    private CmsMenuRepository cmsMenuRepository;
    @Autowired
    private CmsLabelRepository cmsLabelRepository;
    @Autowired
    private CmsRepository cmsRepository;

    @Override
    public void addCmsCategory(CmsCategory cmsCategory){
        cmsCategoryRepository.save(cmsCategory);
    }

    @Override
    public void delCmsCategory(int categoryId){
        cmsCategoryRepository.deleteById(categoryId);
    }

    public int updateCmsCategoryShow(int categoryId,Byte isShow){
        return cmsCategoryRepository.updateIsShowByCategoryId(categoryId,isShow);
    }

    @Override
    public List<CmsCategory> getAllCategoryList(){
        return cmsCategoryRepository.findAll();
    }

    @Override
    public List<CmsCategory> getCategoryListByParentId(int parentId){
        return cmsCategoryRepository.findAllByParentId(parentId);
    }

    @Override
    public Map<Integer,CmsCategory> getCmsCategoryMap(){
        Map<Integer,CmsCategory> cmsCategoryMap = new HashMap<>(0);
        List<CmsCategory> list = getAllCategoryList();
        for (CmsCategory info:list){
            cmsCategoryMap.put(info.getCategoryId(),info);
        }
        return cmsCategoryMap;
    }

    public List<CmsCategory> getCategoryTreeList(){
        return getCategoryTreeList(0, getAllCategoryList());
    }

    private List<CmsCategory> getCategoryTreeList(int parentId, List<CmsCategory> cmsCategoryList){
        List<CmsCategory> resultList = new ArrayList<>();
        for (CmsCategory info:cmsCategoryList){
            if(info.getParentId() == parentId){
                info.setList(getCategoryTreeList(info.getParentId(),cmsCategoryList));
                resultList.add(info);
            }
        }
        return resultList;
    }

    @Override
    public List<CmsMenu> getCmsMenuList(Byte isShow){
        List<CmsMenu> list;
        if(isShow == null){
            list = cmsMenuRepository.findAll();
        }else{
            list = cmsMenuRepository.findAllByIsShow(isShow);
        }
        return list;
    }

}
