package top.xujm.modules.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.config.model.PlatformConfig;

public interface PlatformConfigExtendRepository extends JpaRepository<PlatformConfig, Long> {

    PlatformConfig findFirstById(int var1);
}
