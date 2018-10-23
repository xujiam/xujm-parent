package top.xujm.modules.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.xujm.modules.cms.model.Cms;

import java.util.List;

public interface CmsEntiryRepository extends JpaRepository<Cms,Integer> {

    @Query(value = "select s from Cms s where s.categoryId = ?1 and s.title like %?2%")
    Page<Cms> findByCategoryIdAndTitle(int categoryId, String title, Pageable pageable);

    @Query(value = "select s from Cms s where s.categoryId = ?1 and s.title like %?2%")
    List<Cms> findByCategoryIdAndTitle(int categoryId, String title);

    Page<Cms> findAllByCategoryId(int categoryId, Pageable pageable);

    List<Cms> findAllByCategoryId(int categoryId);

}
