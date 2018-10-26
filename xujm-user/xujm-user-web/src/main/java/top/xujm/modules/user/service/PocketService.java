package top.xujm.modules.user.service;

import top.xujm.modules.user.model.UserConsume;
import top.xujm.modules.user.model.UserFrozen;
import top.xujm.modules.user.model.UserPocket;

/**
 * @author Xujm
 */
public interface PocketService {

    UserPocket getUserPocket();

    double getTotalTicket(int userId);

    double getTotalConsume(int userId);

    /**
     * 用户消费(用户及时扣钱情景)
     * @param userId 消费方
     * @param toId 对方ID
     * @param consumeCode 消费类型(送礼物等)
     * @param moduleId 直播ID或动态ID
     * @param extendId 物品ID或礼物ID
     * @param costNum 花费数
     * @return UserConsume
     */
    UserConsume consume(int userId, int toId, String consumeCode, int moduleId, int extendId, double costNum);


    /**
     * 冻结虚拟币
     * @param userId 消费方
     * @param toId 对方ID
     * @param consumeCode 消费类型(送礼物等)
     * @param moduleId 直播ID或动态ID
     * @param extendId 物品ID或礼物ID
     * @param costNum 花费数
     * @return UserFrozen
     */
    UserFrozen frozen(int userId, int toId, String consumeCode, int moduleId, int extendId, double costNum);

    /**
     * 冻结消费
     * @param frozenId 冻结ID
     */
    void frozenConsume(int frozenId);
    /**
     * 冻结消费
     * @param frozenId 冻结ID
     * @param moduleId moduleId
     */
    void frozenConsume(int frozenId, int moduleId);

    void backFrozen(int frozenId);

    void income(int userId, String incomeCode, int moduleId, int extendId, double incomeNum);

}
