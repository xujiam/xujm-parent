package top.xujm.modules.config.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 * 2018/10/26
 */
@Entity
@Table(name = "xujm_platform_link", schema = BaseConstants.DATABASE_SCHEMA)
public class PlatformLink {
    private int linkId;
    private String linkName;
    private String linkUrl;
    private String linkIcon;
    private int sorts;
    private byte isShow;
    private long createTime;

    @Id
    @Column(name = "link_id")
    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    @Basic
    @Column(name = "link_name")
    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @Basic
    @Column(name = "link_url")
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Basic
    @Column(name = "link_icon")
    public String getLinkIcon() {
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }

    @Basic
    @Column(name = "sorts")
    public int getSorts() {
        return sorts;
    }

    public void setSorts(int sorts) {
        this.sorts = sorts;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlatformLink that = (PlatformLink) o;
        return linkId == that.linkId &&
                sorts == that.sorts &&
                isShow == that.isShow &&
                createTime == that.createTime &&
                Objects.equals(linkName, that.linkName) &&
                Objects.equals(linkUrl, that.linkUrl) &&
                Objects.equals(linkIcon, that.linkIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkId, linkName, linkUrl, linkIcon, sorts, isShow, createTime);
    }
}
