package top.xujm.modules.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.config.model.PlatformLink;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/26
 */
public interface PlatformLinkRepository extends JpaRepository<PlatformLink,Integer> {

    List<PlatformLink> findByIsShowOrderBySortsDesc(byte isShow);

}
