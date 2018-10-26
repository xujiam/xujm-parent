package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.xujm.modules.user.model.UserExtend;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Xujm
 */
public interface UserExtendRepository extends JpaRepository<UserExtend,Integer> {

    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query("update UserExtend set extendValue = :extendValue where userId = :userId and extendKey = :extendKey")
    int updateUserExtendInfo(@Param("userId") int userId, @Param("extendKey") String key, @Param("extendValue") String val);

    List<UserExtend> findByUserId(int userId);

}
