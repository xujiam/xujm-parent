package top.xujm.modules.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.modules.auth.model.AdminMenu;
import top.xujm.modules.auth.model.AdminRole;
import top.xujm.modules.auth.service.AuthService;
import top.xujm.modules.common.model.ResultAdminData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xujm
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/getMenuList")
    public ResultData<List<AdminMenu>> getMenuList() {
        return new ResultData<>(authService.getAdminMenuTreeList());
    }

    @GetMapping("/getAllMenuList")
    public ResultData<List<AdminMenu>> getAllMenuList(int roleId) {
        return new ResultData<>(authService.getAllMenuTreeList(roleId));
    }

    @GetMapping("/menu/list")
    public ResultData<List<AdminMenu>> menuList(int roleId) {
        return new ResultData<>(authService.getAllMenuTreeList(roleId));
    }

    @PostMapping("/addRoleMenu")
    public ResultMsg addRoleMenu(int roleId, String roleMenuIds) {
        List<String> menuIdsList = Arrays.asList(StringUtils.split(roleMenuIds,","));
        List<Integer> menuIdList = new ArrayList<>();
        for (String menuId:menuIdsList){
            menuIdList.add(LibConverterUtil.toInt(menuId));
        }
        authService.addRoleMenu(roleId,menuIdList);
        return new ResultMsg();
    }



    @GetMapping("/getRoleList")
    public ResultData<List<AdminRole>> getRoleList(){
        return new ResultData<>(authService.getRoleList());
    }

    @GetMapping("/role/list")
    public ResultData<List<AdminRole>> roleList(){
        return new ResultData<>(authService.getRoleList());
    }

    @PostMapping("/addRole")
    public ResultMsg addRole(AdminRole adminRole){
        authService.addRole(adminRole);
        return new ResultMsg();
    }

    @PostMapping("/delRole")
    public ResultMsg delRole(String roleIds){
        List<String> roleIdsList = Arrays.asList(StringUtils.split(roleIds,","));
        List<Integer> roleIdList = new ArrayList<>();
        for (String roleId:roleIdsList){
            roleIdList.add(LibConverterUtil.toInt(roleId));
        }
        authService.delRole(roleIdList);
        return new ResultMsg();
    }

    /**
     * 获取菜单列表
     */
    @GetMapping("/getAdminMenu")
    public ResultAdminData<List<AdminMenu>> getAdminMenu() {
        return authService.getAdminMenu();
    }

    /**
     * 获取所有的父级菜单
     */
    @GetMapping("getAdminMenuPid")
    public ResultAdminData<List<AdminMenu>> getAdminMenuPid() {
        return authService.getAdminMenuPid();
    }

    /**
     * 添加菜单
     */
    @PostMapping("/addMenu")
    public ResultMsg addMenu(AdminMenu adminMenu) {
        System.out.println(adminMenu);
        authService.addMenu(adminMenu);
        return new ResultMsg();
    }

    /**
     * 删除菜单
     */
    @PostMapping("delMenu")
    public ResultMsg delMenu(int menuId,int menuPid) {
        return authService.delMenu(menuId, menuPid);
    }

}
