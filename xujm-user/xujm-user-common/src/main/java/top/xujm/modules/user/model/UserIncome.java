package top.xujm.modules.user.model;

import top.xujm.core.base.BaseConstants;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_income", schema = BaseConstants.DATABASE_SCHEMA)
public class UserIncome extends UserBaseInfo{
    private int id;
    private double incomeNum;
    private String incomeCode;
    private int moduleId;
    private int extendId;
    private long addTime;

    public UserIncome(){

    }

    public UserIncome(int id,int userId,String account, String nickname, String avatar
            ,double incomeNum,String incomeCode,int moduleId,int extendId,long addTime){
        super(userId,account,nickname,avatar,(byte)0, (byte)0,null,(short)0);
        this.id = id;
        this.incomeNum = incomeNum;
        this.incomeCode = incomeCode;
        this.moduleId = moduleId;
        this.extendId = extendId;
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
    @Column(name = "income_num", nullable = false, precision = 0)
    public double getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(double incomeNum) {
        this.incomeNum = incomeNum;
    }

    @Basic
    @Column(name = "income_code", nullable = false, length = 20)
    public String getIncomeCode() {
        return incomeCode;
    }

    public void setIncomeCode(String incomeCode) {
        this.incomeCode = incomeCode;
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

    public void copy(UserIncome userIncome){
        super.copy(userIncome);
        this.incomeCode = userIncome.getIncomeCode();
        this.incomeNum = userIncome.getIncomeNum();
        this.moduleId = userIncome.getModuleId();
        this.extendId = userIncome.getExtendId();
        this.addTime = userIncome.getAddTime();
    }
}
