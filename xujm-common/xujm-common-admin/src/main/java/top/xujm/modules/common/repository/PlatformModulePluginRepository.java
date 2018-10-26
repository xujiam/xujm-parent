package top.xujm.modules.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.xujm.modules.common.model.PlatformModule;

import java.util.List;

/**
 * @author ZhengYP
 */
public interface PlatformModulePluginRepository extends JpaRepository<PlatformModule,Integer>{

    @Query(value= "select * from xujm_platform_module  where find_in_set(:pluginCode,plugin_code)",nativeQuery=true)
    List<PlatformModule> selectModulePluginByCode(@Param("pluginCode") String pluginCode);
}
