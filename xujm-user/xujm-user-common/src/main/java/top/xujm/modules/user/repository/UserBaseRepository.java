package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.xujm.modules.user.model.UserInfo;

/**
 * @author Xujm
 * 2018/10/23
 */
public interface UserBaseRepository extends JpaRepository<UserInfo,Integer> {

    @Query("select userId from UserInfo where account = :account")
    Integer selectUserIdByAccount(@Param("account") String account);

}
