package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.MsgSwitch;

import java.util.List;

/**
 * @author Xujm
 */
public interface MsgSwitchRepository extends JpaRepository<MsgSwitch,Integer> {

    @Query("select m.msgCode from MsgSwitch m where m.userId = ?1 and m.isClose = 1")
    List<Integer> selectMsgCodeByUserId(int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update MsgSwitch set isClose = :isClose where userId = :userId and msgCode = :msgCode")
    int updateIsCloseByUserIdAndMsgCode(@Param("userId") int userId, @Param("msgCode") int msgCode, @Param("isClose") byte isClose);

}
