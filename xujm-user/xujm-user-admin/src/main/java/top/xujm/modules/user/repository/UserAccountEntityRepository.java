package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.security.model.UserAccount;
import top.xujm.modules.user.model.UserAccountEntity;

import java.util.List;

public interface UserAccountEntityRepository extends JpaRepository<UserAccount,Integer>{


    UserAccount findFirstByUserId(int var1);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set insideRole=:insideRole where userId = :userId")
    int updateUserAccount(@Param("userId") int var1, @Param("insideRole") short var2);


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set isBlack=1 where userId = :userId")
    int blackUserAccount(@Param("userId") int var1);


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set isBlack=0 where userId = :userId")
    int removeblack(@Param("userId") int var1);


    List<UserAccount> findUserAccountByInsideRole(short insideRole);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set username=:username where userId = :userId")
    void updateUsername(@Param("userId") int var1, @Param("username") String username);


    @Query("select new UserAccountEntity (a.mobile,a.insideRole,a.isBlack,u.addTime,a.userId, u.account, u.nickname, u.avatar,u.sex,u.level, u.signature, u.role)" +
            " from UserAccount a ,UserInfo u where u.userId = a.userId and u.userId=:userId")
    UserAccountEntity selectUserById(@Param("userId") int objectId);

    /*@Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserAccount set is_black=:insideRole where userId = :userId")
    int blackUserAccount(@Param("userId") int var1);*/


    /*@Query(value = "select new UserAccountEntity (a.mobile,a.insideRole,a.isBlack,u.userId, u.account, u.nickname, u.avatar)" +
            " from UserAccount a left join UserInfo u on u.userId = a.userId ")
    Page<UserAccountEntity> selectUserAccountList(String param, Pageable pageable);*/

    /*@Query(value = "select count(*)" +
            " from xujm_user_account u left join xujm_user_info i on u.user_id = i.user_id",
            nativeQuery = true)
    Integer selectUserList();*/


}
