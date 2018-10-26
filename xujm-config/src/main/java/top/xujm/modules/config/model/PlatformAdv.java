package top.xujm.modules.config.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 * 2018/10/26
 */
@Entity
@Table(name = "xujm_platform_adv", schema = BaseConstants.DATABASE_SCHEMA)
public class PlatformAdv {
    private int advId;
    private String title;
    private String showUrl;
    private String linkUrl;
    private double width;
    private double height;
    private String position;
    private String advCode;
    private byte isShow;
    private short sorts;

    @Id
    @Column(name = "adv_id")
    public int getAdvId() {
        return advId;
    }

    public void setAdvId(int advId) {
        this.advId = advId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "show_url")
    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
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
    @Column(name = "width")
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height")
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "adv_code")
    public String getAdvCode() {
        return advCode;
    }

    public void setAdvCode(String advCode) {
        this.advCode = advCode;
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
    @Column(name = "sorts")
    public short getSorts() {
        return sorts;
    }

    public void setSorts(short sorts) {
        this.sorts = sorts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformAdv that = (PlatformAdv) o;
        return advId == that.advId &&
                Double.compare(that.width, width) == 0 &&
                Double.compare(that.height, height) == 0 &&
                isShow == that.isShow &&
                sorts == that.sorts &&
                Objects.equals(title, that.title) &&
                Objects.equals(showUrl, that.showUrl) &&
                Objects.equals(linkUrl, that.linkUrl) &&
                Objects.equals(position, that.position) &&
                Objects.equals(advCode, that.advCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advId, title, showUrl, linkUrl, width, height, position, advCode, isShow, sorts);
    }
}
