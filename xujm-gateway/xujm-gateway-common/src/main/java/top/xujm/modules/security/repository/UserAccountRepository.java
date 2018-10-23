package top.xujm.modules.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.security.model.UserAccount;

/**
 * @author Xujm
 */
public interface UserAccountRepository extends JpaRepository<UserAccount,Long>{

    /**
     * 通过登录账号查询用户信息
     * @param mobile 手机号
     * @return User
     */
    UserAccount findFirstByMobile(String mobile);

    UserAccount findFirstByUsername(String username);

    UserAccount findByMobileAndAreaCode(String mobile, String areaCode);

    UserAccount findFirstByUserId(int userId);
    /**
     * 通过手机号查询用户账号
     * @param mobile 手机号
     * @param areaCode 区号
     * @return UserAccount
     */
    UserAccount findUserAccountByMobileAndAreaCode(String mobile, String areaCode);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set password = :password where userId = :userId")
    int updateUserPassword(@Param("userId") int userId, @Param("password") String password);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set mobile = :mobile,areaCode=:areaCode where userId = :userId")
    int updateUserMobile(@Param("userId") int userId, @Param("mobile") String mobile, @Param("areaCode") String areaCode);
}
