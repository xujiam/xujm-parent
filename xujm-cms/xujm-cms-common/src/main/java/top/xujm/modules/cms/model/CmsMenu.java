package top.xujm.modules.cms.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 * 2018/10/25
 */
@Entity
@Table(name = "xujm_cms_menu", schema = BaseConstants.DATABASE_SCHEMA)
public class CmsMenu {
    private int menuId;
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private int sort;
    private byte isShow;
    private long createTime;

    @Id
    @Column(name = "menu_id")
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "menu_name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "menu_url")
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Basic
    @Column(name = "menu_icon")
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    @Basic
    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "is_show")
    public byte getIsShow() {
        return isShow;
    }

    public void setIsShow(byte isShow) {
        this.isShow = isShow;
    }

    @Basic
    @Column(name = "create_time")
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmsMenu cmsMenu = (CmsMenu) o;
        return menuId == cmsMenu.menuId &&
                sort == cmsMenu.sort &&
                isShow == cmsMenu.isShow &&
                createTime == cmsMenu.createTime &&
                Objects.equals(menuName, cmsMenu.menuName) &&
                Objects.equals(menuUrl, cmsMenu.menuUrl) &&
                Objects.equals(menuIcon, cmsMenu.menuIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, menuName, menuUrl, menuIcon, sort, isShow, createTime);
    }
}
