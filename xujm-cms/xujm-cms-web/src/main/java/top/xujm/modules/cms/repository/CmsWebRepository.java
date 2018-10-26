package top.xujm.modules.cms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import top.xujm.modules.cms.model.Cms;

/**
 * @author Xujm
 * 2018/10/25
 */
public interface CmsWebRepository extends CmsRepository {

    Slice<Cms> findAllByCategoryIdAndStatus(int categoryId, byte status, Pageable pageable);

    Slice<Cms> findAllByStatus(byte status,Pageable pageable);

    Slice<Cms> findAllByTitleLike(String keyword,Pageable pageable);

}
