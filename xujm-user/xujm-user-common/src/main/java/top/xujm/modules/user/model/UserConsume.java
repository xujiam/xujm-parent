package top.xujm.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@ApiModel("用户消费实体")
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_consume", schema = "weking")
public class UserConsume extends UserBaseInfo{
    private int id;
    private int toId;
    @ApiModelProperty("消费金额")
    private double costNum;
    private double incomeNum;
    private String consumeCode;
    private int moduleId;
    private int extendId;
    private long addTime;
    @ApiModelProperty("剩余金额")
    private double surplusDiamond;

    public UserConsume(){

    }

    public UserConsume(int id,int userId,String account, String nickname, String avatar,int toId
            ,double costNum,double incomeNum,String consumeCode,int moduleId,int extendId,long addTime){
        super(userId,account,nickname,avatar,(byte)0, (byte)0,null,(short)0);
        this.id = id;
        this.toId = toId;
        this.costNum = costNum;
        this.incomeNum = incomeNum;
        this.consumeCode = consumeCode;
        this.moduleId = moduleId;
        this.extendId = extendId;
        this.addTime = addTime;
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
    @Column(name = "income_num", nullable = false, precision = 0)
    public double getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(double incomeNum) {
        this.incomeNum = incomeNum;
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

    public void copy(UserConsume userConsume){
        super.copy(userConsume);
        this.toId = userConsume.getToId();
        this.costNum = userConsume.getCostNum();
        this.incomeNum = userConsume.getIncomeNum();
        this.consumeCode = userConsume.getConsumeCode();
        this.moduleId = userConsume.getModuleId();
        this.extendId = userConsume.getExtendId();
        this.addTime = userConsume.getAddTime();
    }
}
