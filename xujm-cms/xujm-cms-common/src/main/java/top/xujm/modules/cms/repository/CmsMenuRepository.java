package top.xujm.modules.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.cms.model.CmsMenu;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/25
 */
public interface CmsMenuRepository extends JpaRepository<CmsMenu,Integer> {

    List<CmsMenu> findAllByIsShow(Byte isShow);

}
