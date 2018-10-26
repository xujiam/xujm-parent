package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.core.base.BaseConstants;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel("用户标签实体")
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_label", schema = BaseConstants.DATABASE_SCHEMA)
public class UserLabel {
    @ApiModelProperty("标签ID")
    private int labelId;
    @ApiModelProperty("标签名称")
    private String labelName;
    @ApiModelProperty("是否选中")
    private boolean isSelect;
    @JsonIgnore
    private byte isShow;
    @JsonIgnore
    private short sorts;

    @Id
    @Column(name = "label_id", nullable = false)
    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    @Basic
    @Column(name = "label_name", nullable = false, length = 10)
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Transient
    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
        UserLabel userLabel = (UserLabel) o;
        return labelId == userLabel.labelId &&
                isShow == userLabel.isShow &&
                sorts == userLabel.sorts &&
                Objects.equals(labelName, userLabel.labelName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(labelId, labelName, isShow, sorts);
    }
}
