package top.xujm.modules.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.config.model.PlatformScheduled;

/**
 * @author Xujm
 */
public interface PlatformScheduledRepository extends JpaRepository<PlatformScheduled,Integer> {

}
