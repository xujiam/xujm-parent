package top.xujm.modules.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.auth.model.RoleMenu;

import java.util.List;

/**
 * @author Xujm
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu,Integer>{

    @Query("select menuId from RoleMenu where roleId = ?1")
    List<Integer> selectByRoleId(int roleId);


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("delete from RoleMenu where roleId = :roleId and menuId in (:menuIdList)")
    int delByRoleIdAndMenuIdList(@Param("roleId") int roleId, @Param("menuIdList") List<Integer> menuIdList);

}
