package top.xujm.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel("用户举报实体")
@Entity
@Table(name = "weking_user_report", schema = "weking")
public class UserReport extends UserBaseInfo{
    @ApiParam(hidden = true)
    private int id;
    @ApiParam(hidden = true)
    private int objectId;
    @ApiModelProperty("举报对象(可为用户ACCOUNT,直播ID等)")
    @NotNull
    private String reportObject;
    @ApiModelProperty("举报对象模块(用户user,直播live)")
    @NotNull
    private String moduleName;
    @ApiModelProperty("举报信息")
    @NotNull
    private String reportMsg;
    @ApiParam(hidden = true)
    private Long addTime;

    public UserReport(){

    }

    public UserReport(int id,int userId, String account, String nickname, String avatar,int objectId
            ,String moduleName,String reportMsg,Long addTime){
        super(userId,account,nickname,avatar,(byte)0, (byte)0,null,(short)0);
        this.id = id;
        this.objectId = objectId;
        this.moduleName = moduleName;
        this.reportMsg = reportMsg;
        this.addTime = addTime;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "object_id", nullable = false)
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @Transient
    public String getReportObject() {
        return reportObject;
    }

    public void setReportObject(String reportObject) {
        this.reportObject = reportObject;
    }

    @Basic
    @Column(name = "module_name", nullable = false, length = 10)
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Basic
    @Column(name = "report_msg", nullable = false, length = 100)
    public String getReportMsg() {
        return reportMsg;
    }

    public void setReportMsg(String reportMsg) {
        this.reportMsg = reportMsg;
    }

    @Basic
    @Column(name = "add_time", nullable = false)
    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        UserReport that = (UserReport) o;
        return id == that.id &&
                objectId == that.objectId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(moduleName, that.moduleName) &&
                Objects.equals(reportMsg, that.reportMsg) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, objectId, moduleName, reportMsg, addTime);
    }
}
