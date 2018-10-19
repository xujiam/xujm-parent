package top.xujm.modules.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.config.model.PlatformConfig;

/**
 * @author Xujm
 */
public interface PlatformConfigRepository extends JpaRepository<PlatformConfig, Long> {

    /**
     * 通过键值查询数据
     * @param configKey key
     * @return PlatformConfig
     */
    PlatformConfig findByConfigKey(String configKey);
}
