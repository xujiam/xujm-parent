package top.xujm.modules.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.cms.model.CmsCategory;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/24
 */
public interface CmsCategoryRepository extends JpaRepository<CmsCategory,Integer> {

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update CmsCategory set isShow = :isShow where categoryId = :categoryId")
    int updateIsShowByCategoryId(@Param("categoryId") int categoryId,@Param("isShow") byte isShow);

    List<CmsCategory> findAllByParentId(int parentId);

}
