package top.xujm.modules.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.cms.model.CmsArticle;

/**
 * @author Xujm
 * 2018/10/25
 */
public interface CmsArticleRepository extends JpaRepository<CmsArticle,Integer> {
}
