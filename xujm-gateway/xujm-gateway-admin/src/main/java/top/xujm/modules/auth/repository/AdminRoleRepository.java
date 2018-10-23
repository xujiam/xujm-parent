package top.xujm.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.auth.model.AdminRole;

import java.util.List;

/**
 * @author Xujm
 */
public interface AdminRoleRepository extends JpaRepository<AdminRole,Integer> {


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("delete from AdminRole where roleId in (:roleIdList)")
    int delRoleByList(@Param("roleIdList") List<Integer> roleIdList);

    @Query("select u.roleName from AdminRole u where u.roleId = ?1")
    List<String> selectRoleNameByroleId(Integer ru);

}
