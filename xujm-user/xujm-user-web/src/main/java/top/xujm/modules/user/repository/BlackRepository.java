package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserBlack;

import java.util.List;

/**
 * @author Xujm
 */
public interface BlackRepository extends JpaRepository<UserBlack,Integer> {

    /**
     * 根据用户ID查询拉黑信息
     * @param userId 用户ID
     * @param toId 对方ID
     * @return UserBlack
     */
    UserBlack findByUserIdAndToId(int userId, int toId);

    /**
     * 删除拉黑信息
     * @param userId 用户ID
     * @param toId 对方ID
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    int deleteByUserIdAndToId(int userId, int toId);


    @Query("select new UserBlack(b.id,u.account,u.nickname,u.avatar,u.addTime) from UserBlack b " +
            "left join UserInfo u on b.userId = u.userId where b.userId=?1")
    List<UserBlack> selectBlackListByUserId(int userId);

}
