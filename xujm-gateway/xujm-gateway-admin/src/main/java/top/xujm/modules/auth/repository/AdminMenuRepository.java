package top.xujm.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.xujm.modules.auth.model.AdminMenu;

import java.util.List;

/**
 * @author Xujm
 */
public interface AdminMenuRepository extends JpaRepository<AdminMenu,Integer> {

    @Query("select a from RoleMenu r left join AdminMenu a on r.menuId = a.menuId where r.roleId in (:list) and a.isShow = 1 ")
    List<AdminMenu> selectAdminMenuByRoleIdList(@Param("list") List<Integer> list);

    @Query("select new AdminMenu(a.menuId,r.roleId,a.menuUrl) from " +
            "AdminMenu a left join RoleMenu r on a.menuId = r.menuId")
    List<AdminMenu> selectAllMenuAndRoleList();

    /**
     * 获得用户所有目录列表
     */
    @Query("select a from RoleMenu r left join AdminMenu a on r.menuId = a.menuId where r.roleId in (:list) and a.menuType = 0 and a.isShow = 1 order by a.sort asc")
    List<AdminMenu> selectCatalogMenuByRoleIdList(@Param("list") List<Integer> list);

    @Query("select a from AdminMenu a where a.isShow = 1")
    List<AdminMenu> selectAllMenuList();

    @Query("select a from AdminMenu a where a.isShow = 1 order by a.sort asc")
    List<AdminMenu> findAllMenuList();

    @Query("select a from AdminMenu a where a.isShow = 1 and a.menuPid = 0 order by a.sort asc")
    List<AdminMenu> findAllMenuPid();

    @Query("select a from AdminMenu a where a.isShow = 1 and a.menuPid = :menuId")
    List<AdminMenu> findAllMenuByMenuPid(@Param("menuId") int menuId);

}
