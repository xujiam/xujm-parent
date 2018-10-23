package top.xujm.modules.oss.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.modules.oss.common.OssConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@ApiModel(value = "储存对象")
@Entity
@Table(name = OssConstants.MODULE_PREFIX+"_log", schema = "weking")
public class OssLog {
    @ApiModelProperty(value = "文件ID",name = "fileId")
    private int fileId;
    @ApiModelProperty(value = "文件类型",name = "typeCode")
    private String typeCode;
    @ApiModelProperty(value = "文件相对地址",name = "fileUrl")
    private String fileUrl;
    @ApiModelProperty(value = "添加时间",name = "addTime")
    private long addTime;

    @Id
    @Column(name = "file_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
    @Column(name = "file_url", nullable = false, length = 20)
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Basic
    @Column(name = "add_time", nullable = false)
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

}
