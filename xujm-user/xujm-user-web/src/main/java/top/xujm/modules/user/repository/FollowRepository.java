package top.xujm.modules.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.xujm.modules.user.model.UserFollow;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Xujm
 */
public interface FollowRepository extends JpaRepository<UserFollow,Integer> {

    /**
     * 查询两用户关注状态
     * @param userId 关注者
     * @param toId 被关注者
     * @return UserFollow
     */
    UserFollow findFirstByUserIdAndToId(int userId, int toId);

    @Transactional
    @Modifying
    @Query(value = "update UserFollow set followState = :state where userId = :userId and toId = :toId")
    int updateUserFollowState(@Param("userId") int userId, @Param("toId") int toId, @Param("state") Byte state);

    /**
     * 关注列表
     * @param userId 用户ID
     * @param pageable 分页
     * @return List<UserFollow>
     */
    @Query(value = "select new UserFollow(f.id,f.userId,f.toId,u.account,u.nickname,u.avatar,f.addTime,u.role,f.followState) from " +
            "UserFollow f left join UserInfo u on f.toId = u.userId where f.userId = :userId order by f.id desc")
    List<UserFollow> selectFollowList(@Param("userId") int userId, Pageable pageable);

    /**
     * 粉丝列表
     * @param userId 用户ID
     * @param pageable 分页
     * @return List<UserFollow>
     */
    @Query(value = "select new UserFollow(f.id,f.userId,f.toId,u.account,u.nickname,u.avatar,f.addTime,u.role,f.followState) from " +
            "UserFollow f left join UserInfo u on f.userId = u.userId where f.toId = :userId order by f.id desc")
    List<UserFollow> selectFansList(@Param("userId") int userId, Pageable pageable);

    /**
     * 查询关注用户ID列表
     * @param userId 用户ID
     * @return List<Integer>
     */
    @Query(value = "select u.toId from UserFollow u where u.userId = :userId")
    List<Integer> selectFollowUserIdList(@Param("userId") int userId);

    @Query("select count(f.userId) from UserFollow f where f.userId = ?1")
    int selectFollowNumByUserId(int userId);

    @Query("select count(f.userId) from UserFollow f where f.toId = ?1")
    int selectFansNumByUserId(int userId);

    @Query("select u.cid from UserFollow f left join UserInfo u on f.userId = u.userId where f.toId = :toId")
    List<String> selectFansCidList(@Param("toId") int toId);

}
