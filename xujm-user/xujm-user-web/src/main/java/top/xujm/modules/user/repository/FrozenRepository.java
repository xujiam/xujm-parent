package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserFrozen;

/**
 * @author Xujm
 */
public interface FrozenRepository extends JpaRepository<UserFrozen,Integer> {

    UserFrozen findFirstById(Integer id);

    /**
     * 更新冻结状态
     * @param frozenId 冻结ID
     * @param frozenState 冻结状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserFrozen set frozenState = :frozenState where id = :id and frozenState = 1")
    int updateFrozenStateById(@Param("id") Integer frozenId, @Param("frozenState") byte frozenState);
}
