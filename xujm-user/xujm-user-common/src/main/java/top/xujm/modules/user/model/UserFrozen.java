package top.xujm.modules.user.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_user_frozen", schema = "weking")
public class UserFrozen extends UserBaseInfo{
    private int id;
    private int toId;
    private double costNum;
    private String consumeCode;
    private int moduleId;
    private int extendId;
    private byte frozenState;
    private long addTime;
    private double surplusDiamond;

    public UserFrozen(){

    }

    public UserFrozen(int id,int userId,String account, String nickname, String avatar,int toId
            ,double costNum,String consumeCode,int moduleId,int extendId,byte frozenState,long addTime){
        super(userId,account,nickname,avatar,(byte)0, (byte)0,null,(short)0);
        this.id = id;
        this.toId = toId;
        this.costNum = costNum;
        this.consumeCode = consumeCode;
        this.moduleId = moduleId;
        this.extendId = extendId;
        this.addTime = addTime;
        this.frozenState = frozenState;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Column(name = "to_id", nullable = false)
    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    @Basic
    @Column(name = "cost_num", nullable = false, precision = 2)
    public double getCostNum() {
        return costNum;
    }

    public void setCostNum(double costNum) {
        this.costNum = costNum;
    }

    @Basic
    @Column(name = "consume_code", nullable = false)
    public String getConsumeCode() {
        return consumeCode;
    }

    public void setConsumeCode(String consumeCode) {
        this.consumeCode = consumeCode;
    }

    @Basic
    @Column(name = "module_id", nullable = false)
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    @Basic
    @Column(name = "extend_id", nullable = false)
    public int getExtendId() {
        return extendId;
    }

    public void setExtendId(int extendId) {
        this.extendId = extendId;
    }

    @Basic
    @Column(name = "frozen_state", nullable = false)
    public byte getFrozenState() {
        return frozenState;
    }

    public void setFrozenState(byte frozenState) {
        this.frozenState = frozenState;
    }

    @Basic
    @Column(name = "add_time", nullable = false)
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Transient
    public double getSurplusDiamond() {
        return surplusDiamond;
    }

    public void setSurplusDiamond(double surplusDiamond) {
        this.surplusDiamond = surplusDiamond;
    }

    public void copy(UserFrozen userFrozen){
        super.copy(userFrozen);
        this.toId = userFrozen.getToId();
        this.costNum = userFrozen.getCostNum();
        this.consumeCode = userFrozen.getConsumeCode();
        this.moduleId = userFrozen.getModuleId();
        this.extendId = userFrozen.getExtendId();
        this.addTime = userFrozen.getAddTime();
        this.frozenState = userFrozen.getFrozenState();
    }
}
