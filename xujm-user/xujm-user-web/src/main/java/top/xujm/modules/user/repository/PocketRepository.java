package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserPocket;


/**
 * @author Xujm
 */
public interface PocketRepository extends JpaRepository<UserPocket, Integer> {

    UserPocket findFirstByUserId(Integer userId);

    @Query("select p.surplusDiamond from UserPocket p where p.userId = :userId")
    double findSurplusDiamondById(@Param("userId") Integer userId);

    /**
     * 扣减虚拟币
     * @param userId 用户ID
     * @param surplusDiamond 扣减数量
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserPocket set surplusDiamond = surplusDiamond - :diamond," +
            "totalConsume = totalConsume + :diamond where userId = :userId and surplusDiamond >= :diamond")
    int deductDiamondByUserId(@Param("userId") Integer userId, @Param("diamond") double surplusDiamond);

    /**
     * 增加虚拟币
     * @param userId 用户ID
     * @param surplusDiamond 扣减数量
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserPocket set surplusDiamond = surplusDiamond + :diamond," +
            "totalDiamond = totalDiamond + :diamond where userId = :userId")
    int increaseDiamondByUserId(@Param("userId") Integer userId, @Param("diamond") double surplusDiamond);

    /**
     * 扣减冻结虚拟币
     * @param userId 用户ID
     * @param frozenDiamond 扣减冻结数量
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserPocket set frozenDiamond = frozenDiamond - :diamond where userId = :userId")
    int deductFrozenDiamondByUserId(@Param("userId") Integer userId, @Param("diamond") double frozenDiamond);

    /**
     * 冻结虚拟币
     * @param userId 用户ID
     * @param surplusDiamond 冻结数量
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserPocket set surplusDiamond = surplusDiamond - :diamond " +
            ",frozenDiamond = frozenDiamond + :diamond where userId = :userId and surplusDiamond >= :diamond")
    int frozenDiamondByUserId(@Param("userId") Integer userId, @Param("diamond") double surplusDiamond);

    /**
     * 返还冻结虚拟币
     * @param userId 用户ID
     * @param diamond 返还冻结数量
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserPocket set surplusDiamond = surplusDiamond + :diamond " +
            ",frozenDiamond = frozenDiamond - :diamond where userId = :userId")
    int backFrozenDiamondByUserId(@Param("userId") Integer userId, @Param("diamond") double diamond);
    /**
     * 增加收益
     * @param userId 用户ID
     * @param ticket 收益数量
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserPocket set surplusTicket = surplusTicket + :ticket,totalTicket = totalTicket + :ticket where userId = :userId")
    int increaseTicketByUserId(@Param("userId") Integer userId, @Param("ticket") double ticket);

}
