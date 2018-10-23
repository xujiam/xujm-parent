package top.xujm.modules.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.auth.common.AuthResultEnum;
import top.xujm.modules.auth.model.AdminMenu;
import top.xujm.modules.auth.model.AdminRole;
import top.xujm.modules.auth.model.RoleMenu;
import top.xujm.modules.auth.repository.AdminMenuRepository;
import top.xujm.modules.auth.repository.AdminRoleRepository;
import top.xujm.modules.auth.repository.AdminUserRoleRepository;
import top.xujm.modules.auth.repository.RoleMenuRepository;
import top.xujm.modules.auth.service.AuthService;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.security.common.SecurityRemindException;
import top.xujm.modules.security.common.SecurityResultEnum;
import top.xujm.modules.security.service.impl.SecurityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
@Component
public class AuthServiceImpl extends SecurityService implements AuthService {

    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;
    @Autowired
    private AdminMenuRepository adminMenuRepository;
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Override
    public Map<Integer, AdminMenu> getAllAdminMenuMap(){
        Map<Integer,AdminMenu> adminMenuMap = new HashMap<>();
        Map<Integer,String> adminRoleMap = getAdminRoleMap();
        List<AdminMenu> menuList = adminMenuRepository.selectAllMenuAndRoleList();
        List<String> roleCodeList;
        for (AdminMenu adminMenu:menuList){
            if(!StringUtils.isEmpty(adminMenu.getMenuUrl())){
                AdminMenu menu = adminMenuMap.get(adminMenu.getMenuId());
                if(menu != null){
                    roleCodeList = menu.getRoleCodeList();
                }else{
                    menu = adminMenu;
                    roleCodeList = new ArrayList<>();
                }
                roleCodeList.add(adminRoleMap.get(adminMenu.getRoleId()));
                menu.setRoleCodeList(roleCodeList);
                adminMenuMap.put(adminMenu.getMenuId(),menu);
            }
        }
        return adminMenuMap;
    }

    @Override
    public List<AdminMenu> getUserAdminMenuList() {
        int userId = getUserId();
        if(userId == 0){
            return null;
        }
        List<Integer> roleIdList = getRoleIdList(userId);
        return adminMenuRepository.selectAdminMenuByRoleIdList(roleIdList);
    }

    @Override
    public List<AdminRole> getRoleList(){
        return adminRoleRepository.findAll();
    }

    @Override
    public void addRole(AdminRole adminRole){
        if(!StringUtils.startsWith(adminRole.getRoleCode(),"ROLE_")){
            throw new SecurityRemindException(AuthResultEnum.ROLE_CODE_ERROR.getMessage());
        }
        adminRole.setCreateTime(LibDateUtil.getNowTime());
        adminRoleRepository.save(adminRole);
    }

    @Override
    public void delRole(List<Integer> roleIdList){
        adminRoleRepository.delRoleByList(roleIdList);
    }

    private List<Integer> getRoleIdList(int userId){
        List<Integer> roleIdList = adminUserRoleRepository.selectRoleIdByUserId(userId);
        if(roleIdList.size() == 0){
            //没有分配到角色直接登录失效
            throw new SecurityRemindException(SecurityResultEnum.AUTH_ERROR);
        }
        return roleIdList;
    }

    @Override
    public List<AdminMenu> getAdminMenuTreeList(){
        List<AdminMenu> list = adminMenuRepository.selectCatalogMenuByRoleIdList(getRoleIdList(getLoginUserId()));
        return getAdminMenu(0,list,null);
    }

    @Override
    public List<AdminMenu> getAllMenuTreeList(int roleId){
        List<AdminMenu> list = adminMenuRepository.selectAllMenuList();
        return getAdminMenu(0,list,getRoleMenuList(roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleMenu(int roleId,List<Integer> menuIdList){
        List<Integer> newRoleMenuList = new ArrayList<>(menuIdList);
        List<Integer> curRoleMenuList = getRoleMenuList(roleId);
        //移除当前拥有权限菜单，剩余则是新增权限菜单
        newRoleMenuList.removeAll(curRoleMenuList);
        //移除当前提交权限菜单，剩余则是删除权限菜单
        curRoleMenuList.removeAll(menuIdList);
        if(curRoleMenuList.size() > 0){
            roleMenuRepository.delByRoleIdAndMenuIdList(roleId,curRoleMenuList);
        }
        List<RoleMenu> saveList = new ArrayList<>();
        RoleMenu roleMenu;
        for (Integer menuId:newRoleMenuList){
            roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            saveList.add(roleMenu);
        }
        roleMenuRepository.saveAll(saveList);
    }

    /**
     * 获取菜单列表
     */
    @Override
    public ResultAdminData<List<AdminMenu>> getAdminMenu() {
        List<AdminMenu> adminMenu = adminMenuRepository.findAllMenuList();
        return new ResultAdminData<>(adminMenu.size(), adminMenu);
    }

    /**
     * 获取所有的父级菜单
     */
    @Override
    public ResultAdminData<List<AdminMenu>> getAdminMenuPid() {
        List<AdminMenu> adminMenu = adminMenuRepository.findAllMenuPid();
        return new ResultAdminData<>(adminMenu.size(), adminMenu);
    }

    /**
     * 添加菜单
     */
    @Override
    public void addMenu(AdminMenu adminMenu) {
        byte isShow = 1;
        adminMenu.setMenuPerms("");
        adminMenu.setIsShow(isShow);
        adminMenu.setCreateTime(LibDateUtil.getNowTime());
        adminMenuRepository.save(adminMenu);
    }

    /**
     * 删除菜单
     */
    @Override
    public ResultMsg delMenu(int menuId, int menuPid) {
        AdminMenu m = adminMenuRepository.findById(menuId).get();
        if (menuPid == 0){
            List<AdminMenu> list = adminMenuRepository.findAllMenuByMenuPid(menuId);
            if (list.size() != 0) {
                return new ResultMsg(-1,"删除失败，有关联数据不能删除!");
            } else {
                adminMenuRepository.delete(m);
                return new ResultMsg();
            }
        }
        adminMenuRepository.delete(m);
        return new ResultMsg();
    }

    private List<AdminMenu> getAdminMenu(int pid,List<AdminMenu> adminMenuList,List<Integer> roleMenuIdList){
        List<AdminMenu> resultList = new ArrayList<>();
        for (AdminMenu adminMenu : adminMenuList) {
            if(roleMenuIdList != null){
                if(roleMenuIdList.contains(adminMenu.getMenuId())){
                    adminMenu.setIsHold(true);
                }
            }
            if(adminMenu.getMenuPid() == pid){
                adminMenu.setList(getAdminMenu(adminMenu.getMenuId(),adminMenuList,roleMenuIdList));
                resultList.add(adminMenu);
            }
        }
        return resultList;
    }

    /**
     * 获得角色菜单ID列表
     */
    private List<Integer> getRoleMenuList(int roleId){
        return roleMenuRepository.selectByRoleId(roleId);
    }

    private Map<Integer,String> getAdminRoleMap(){
        Map<Integer,String> adminRoleMap = new HashMap<>();
        List<AdminRole> list = adminRoleRepository.findAll();
        for (AdminRole info:list){
            adminRoleMap.put(info.getRoleId(),info.getRoleCode());
        }
        return adminRoleMap;
    }

}
