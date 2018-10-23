package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoEntityRepository extends JpaRepository<UserInfo,Integer> {


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserInfo set nickname = :nickname,insideRole=:insideRole,sex=:sex,role=:role where userId = :userId")
    int updateUserInfo(@Param("userId") int var1, @Param("nickname") String var2, @Param("insideRole") short var3, @Param("sex") byte var4, @Param("role") short var5);


    @Query("select u from UserInfo u where u.userId in (:toids)")
    List<UserInfo> selectUserInfoInUserId(@Param("toids") List<Integer> toids);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserInfo set nickname = :nickname where account = :account")
    void updateUserNickname(@Param("account") String account, @Param("nickname") String nickname);




    @Query(value = "SELECT DATE_FORMAT(add_time,'%Y-%m-%d') as d ,:var1 ,COUNT(1) FROM weking_user_account GROUP BY d",
            nativeQuery = true)
    List<Map<String, Object>> selectRegisterDay(@Param("var1") String var1);

    @Query(value = "SELECT COUNT(*) FROM weking_user_info", nativeQuery = true)
    int selectUserNum();
}
