package top.xujm.modules.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.xujm.modules.record.common.RecordConstants;
import top.xujm.modules.record.model.RecordLogin;

/**
 * @author ZhengYP
 */
public interface RecordLoginEntityRepository extends JpaRepository<RecordLogin, Integer>{


    @Query(value = "SELECT COUNT(DISTINCT(user_id)) as 'num' FROM "+ RecordConstants.MODULE_PREFIX+"_login WHERE login_time >= ?1",
    nativeQuery = true)
    int selectLoginNum(String var1);
}
