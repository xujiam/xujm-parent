package top.xujm.modules.cms.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 * 2018/10/25
 */
@Entity
@Table(name = "xujm_cms_label", schema = BaseConstants.DATABASE_SCHEMA)
public class CmsLabel {
    private int labelId;
    private String labelName;
    private byte isShow;
    private short sorts;

    @Id
    @Column(name = "label_id")
    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    @Basic
    @Column(name = "label_name")
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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
        CmsLabel cmsLabel = (CmsLabel) o;
        return labelId == cmsLabel.labelId &&
                isShow == cmsLabel.isShow &&
                sorts == cmsLabel.sorts &&
                Objects.equals(labelName, cmsLabel.labelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labelId, labelName, isShow, sorts);
    }
}
