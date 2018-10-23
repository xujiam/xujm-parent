package top.xujm.modules.auth.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_admin_menu", schema = "weking")
public class AdminMenu {
    private int menuId;
    private int menuPid;
    private String menuName;
    private String menuUrl;
    private String menuPerms;
    private byte menuType;
    private String menuIcon;
    private int sort;
    private byte isShow;
    private long createTime;
    private Integer roleId;
    private boolean isHold;
    private List<String> roleCodeList;
    private List<AdminMenu> list;

    public AdminMenu(){

    }

    public AdminMenu(int menuId,Integer roleId,String menuUrl){
        this.menuId = menuId;
        this.roleId = roleId;
        this.menuUrl = menuUrl;
    }

    @Id
    @Column(name = "menu_id", nullable = false)
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "menu_pid", nullable = false)
    public int getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(int menuPid) {
        this.menuPid = menuPid;
    }

    @Basic
    @Column(name = "menu_name", nullable = false, length = 50)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "menu_url", nullable = false, length = 200)
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Basic
    @Column(name = "menu_perms", nullable = false, length = 500)
    public String getMenuPerms() {
        return menuPerms;
    }

    public void setMenuPerms(String menuPerms) {
        this.menuPerms = menuPerms;
    }

    @Basic
    @Column(name = "menu_type", nullable = false)
    public byte getMenuType() {
        return menuType;
    }

    public void setMenuType(byte menuType) {
        this.menuType = menuType;
    }

    @Basic
    @Column(name = "menu_icon", nullable = false, length = 50)
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "is_show", nullable = false)
    public byte getIsShow() {
        return isShow;
    }

    public void setIsShow(byte isShow) {
        this.isShow = isShow;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Transient
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Transient
    public boolean getIsHold() {
        return isHold;
    }

    public void setIsHold(boolean hold) {
        isHold = hold;
    }

    @Transient
    public List<String> getRoleCodeList() {
        return roleCodeList;
    }

    public void setRoleCodeList(List<String> roleCodeList) {
        this.roleCodeList = roleCodeList;
    }

    @Transient
    public List<AdminMenu> getList() {
        return list;
    }

    public void setList(List<AdminMenu> list) {
        this.list = list;
    }
}
