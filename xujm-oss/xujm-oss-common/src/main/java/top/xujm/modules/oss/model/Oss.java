package top.xujm.modules.oss.model;

import top.xujm.modules.oss.common.OssConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = OssConstants.MODULE_PREFIX, schema = "weking")
public class Oss {
    private int id;
    private String typeCode;
    private String datePath;
    private Byte fileType;
    private byte isEnable;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_code", nullable = false, length = 20)
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "file_type", nullable = false, length = 2)
    public Byte getFileType() {
        return fileType;
    }

    public void setFileType(Byte fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "date_path", nullable = false, length = 10)
    public String getDatePath() {
        return datePath;
    }

    public void setDatePath(String datePath) {
        this.datePath = datePath;
    }

    @Basic
    @Column(name = "is_enable", nullable = false)
    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Oss oss = (Oss) o;
        return id == oss.id &&
                isEnable == oss.isEnable &&
                Objects.equals(typeCode, oss.typeCode) &&
                Objects.equals(datePath, oss.datePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, typeCode, datePath, isEnable);
    }
}
