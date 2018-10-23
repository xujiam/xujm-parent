package top.xujm.modules.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.cms.model.CmsArticle;

import java.util.List;

public interface CmsArticleEntityRepository  extends JpaRepository<CmsArticle,Integer> {

    List<CmsArticle> findAllByCmsId(int cmsId);

    CmsArticle findByCmsIdAndLangCode(int cmsId, String langCode);


}
