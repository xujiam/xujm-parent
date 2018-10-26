package top.xujm.modules.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserBaseInfo;
import top.xujm.modules.user.model.UserInfo;

import java.util.List;

/**
 * @author Xujm
 */
public interface UserRepository extends UserBaseRepository {

    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return UserInfo
     */
    UserInfo findFirstByUserId(Integer userId);

    @Query("select new UserInfo(u.account,u.nickname,u.avatar,u.sex,u.level,u.signature,u.role) from UserInfo u where u.account like %:query% or u.nickname like %:query%")
    List<UserInfo> selectSearchUserList(@Param("query") String query, Pageable pageable);

    @Query("select new top.xujm.modules.user.model.UserBaseInfo(u.userId,u.account,u.nickname,u.avatar,u.sex,u.level,u.signature,u.role) from UserInfo u where u.insideRole = 1")
    List<UserBaseInfo> selectAllRobotList();

    @Query("select new top.xujm.modules.user.model.UserBaseInfo(u.userId,u.account,u.nickname,u.avatar,u.sex,u.level,u.signature,u.role) from UserInfo u where u.recommend = 1")
    List<UserBaseInfo> selectRecommendUserList();

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set nickname = :nickname where userId = :id")
    int updateUserNickname(@Param("nickname") String nickname, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set account = :account where userId = :id")
    int updateUserAccount(@Param("account") String account, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set signature = :signature where userId = :id")
    int updateUserSignature(@Param("signature") String signature, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set city = :city where userId = :id")
    int updateUserCity(@Param("city") String city, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set lng = :lng,lat = :lat where userId = :id")
    int updateUserLngAndLat(@Param("lng") Double lng, @Param("lat") Double lat, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set birthday = :birthday where userId = :id")
    int updateUserBirthday(@Param("birthday") String birthday, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set cid = :cid where userId = :id")
    int updateUserCid(@Param("cid") String cid, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set langCode = :langCode where userId = :id")
    int updateUserLangCode(@Param("langCode") String langCode, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set avatar = :avatar where userId = :id")
    int updateUserAvatar(@Param("avatar") String avatar, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set sex = :sex where userId = :id")
    int updateUserSex(@Param("sex") Byte sex, @Param("id") int userId);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set fansNum = fansNum + :fansNum where userId = :id")
    int updateUserFansNum(@Param("id") int userId, @Param("fansNum") int fansNum);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserInfo set followNum = followNum + :followNum where userId = :id")
    int updateUserFollowNum(@Param("id") int userId, @Param("followNum") int followNum);
}
