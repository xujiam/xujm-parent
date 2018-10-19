package top.xujm.modules.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.config.model.AppVersion;

/**
 * @author Xujm
 */
public interface AppVersionRepository extends JpaRepository<AppVersion,Integer> {

    AppVersion findFirstByAppCodeOrderByIdDesc(String appCode);

}
