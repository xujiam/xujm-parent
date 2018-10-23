package top.xujm.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.auth.model.AdminUserRole;

import java.util.List;

/**
 * @author Xujm
 */
public interface AdminUserRoleRepository extends JpaRepository<AdminUserRole,Integer> {

    @Query("select new AdminUserRole(u.roleId,a.roleCode) from AdminUserRole u left join AdminRole a on u.roleId = a.roleId where " +
            "u.userId = ?1")
    List<AdminUserRole> selectUserRoleByUserId(int userId);

    @Query("select u.roleId from AdminUserRole u where u.userId = ?1")
    List<Integer> selectRoleIdByUserId(int userId);


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("delete from AdminUserRole where userId = :userId")
    int deleteUserRole(@Param("userId") int var1);
}
