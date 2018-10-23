package top.xujm.modules.auth.service;

import top.xujm.common.core.model.ResultMsg;
import top.xujm.modules.auth.model.AdminMenu;
import top.xujm.modules.auth.model.AdminRole;
import top.xujm.modules.common.model.ResultAdminData;

import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public interface AuthService {

    Map<Integer, AdminMenu> getAllAdminMenuMap();

    List<AdminRole> getRoleList();

    void addRole(AdminRole adminRole);

    void delRole(List<Integer> roleIdList);

    List<AdminMenu> getUserAdminMenuList();

    List<AdminMenu> getAdminMenuTreeList();

    List<AdminMenu> getAllMenuTreeList(int roleId);

    void addRoleMenu(int roleId, List<Integer> menuIdList);

    ResultAdminData<List<AdminMenu>> getAdminMenu();

    ResultAdminData<List<AdminMenu>> getAdminMenuPid();

    void addMenu(AdminMenu adminMenu);

    ResultMsg delMenu(int menuId, int menuPid);
}
