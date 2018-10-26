package top.xujm.modules.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.config.model.PlatformAdv;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/26
 */
public interface PlatformAdvRepository extends JpaRepository<PlatformAdv,Integer> {

    List<PlatformAdv> findByPositionAndIsShowOrderBySortsDesc(String position, byte isShow);

}
