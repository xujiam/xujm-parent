package top.xujm.modules.cms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.xujm.modules.cms.model.CmsArticle;

/**
 * @author Xujm
 * 2018/10/25
 */
public interface CmsArticleWebRepository extends CmsArticleRepository {

    @Query("select new CmsArticle(c.cmsId,c.userId,a.title,a.description,c.cover,c.categoryId,c.readNum" +
            "            ,c.commentNum,c.likeNum,c.addTime,a.content,a.extend) from CmsArticle a left join Cms c" +
            " on c.cmsId = a.cmsId where c.cmsId = :cmsId and a.langCode = :langCode")
    CmsArticle selectCmsArticleInfo(@Param("cmsId") int cmsId, @Param("langCode")String langCode);

}
