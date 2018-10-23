package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel("广告实体")
@Entity
@Table(name = "weking_user_adv", schema = "weking")
public class UserAdv {
    @ApiModelProperty("广告ID")
    private int advId;
    @ApiModelProperty("广告标题")
    private String title;
    @ApiModelProperty("广告封面")
    private String imgUrl;
    @ApiModelProperty("广告链接")
    private String linkUrl;
    @ApiModelProperty("广告封面宽度")
    private double width;
    @ApiModelProperty("广告封面高度")
    private double height;
    @ApiModelProperty("广告位置(login(登录)start(启动)index(首页))")
    private String position;
    @ApiModelProperty("广告类型代号(image(图片)video(视频))")
    private String advCode;
    @JsonIgnore
    private byte isShow;
    @JsonIgnore
    private short sorts;

    public UserAdv(){}

    public UserAdv(int advId, String title, String imgUrl, String linkUrl, double width, double height, String position, String advCode, byte isShow, short sorts) {
        this.advId = advId;
        this.title = title;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.width = width;
        this.height = height;
        this.position = position;
        this.advCode = advCode;
        this.isShow = isShow;
        this.sorts = sorts;
    }

    @Id
    @Column(name = "adv_id", nullable = false)
    public int getAdvId() {
        return advId;
    }

    public void setAdvId(int advId) {
        this.advId = advId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "img_url", nullable = false, length = 100)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "link_url", nullable = false, length = 100)
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Basic
    @Column(name = "width", nullable = false, precision = 2)
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height", nullable = false, precision = 2)
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Basic
    @Column(name = "position", nullable = false, length = 10)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "adv_code", nullable = false, length = 10)
    public String getAdvCode() {
        return advCode;
    }

    public void setAdvCode(String advCode) {
        this.advCode = advCode;
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
    @Column(name = "sorts", nullable = false)
    public short getSorts() {
        return sorts;
    }

    public void setSorts(short sorts) {
        this.sorts = sorts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        UserAdv that = (UserAdv) o;
        return advId == that.advId &&
                Double.compare(that.width, width) == 0 &&
                Double.compare(that.height, height) == 0 &&
                isShow == that.isShow &&
                sorts == that.sorts &&
                Objects.equals(title, that.title) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(linkUrl, that.linkUrl) &&
                Objects.equals(position, that.position) &&
                Objects.equals(advCode, that.advCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(advId, title, imgUrl, linkUrl, width, height, position, advCode, isShow, sorts);
    }
}
