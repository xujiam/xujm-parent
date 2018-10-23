package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel("用户钱包")
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_pocket", schema = "weking")
public class UserPocket extends UserBaseInfo{
    @ApiModelProperty("剩余收入")
    private double surplusTicket;
    @ApiModelProperty("剩余虚拟币")
    private double surplusDiamond;
    @ApiModelProperty("总收入")
    private double totalTicket;
    @ApiModelProperty("总虚拟币")
    private double totalDiamond;
    @ApiModelProperty("总消费")
    private double totalConsume;
    @ApiModelProperty("冻结虚拟币")
    private double frozenDiamond;

    public UserPocket(){

    }

    public UserPocket(int userId,String account, String nickname, String avatar ,double surplusTicket
            ,double surplusDiamond,double totalTicket,double totalDiamond,double totalConsume,double frozenDiamond){
        super(userId,account,nickname,avatar,(byte)0, (byte)0,null,(short)0);
        this.surplusTicket = surplusTicket;
        this.surplusDiamond = surplusDiamond;
        this.totalTicket = totalTicket;
        this.totalDiamond = totalDiamond;
        this.totalConsume = totalConsume;
        this.frozenDiamond = frozenDiamond;
    }

    @Override
    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "surplus_ticket", nullable = false, precision = 2)
    public double getSurplusTicket() {
        return surplusTicket;
    }

    public void setSurplusTicket(double surplusTicket) {
        this.surplusTicket = surplusTicket;
    }

    @Basic
    @Column(name = "surplus_diamond", nullable = false, precision = 2)
    public double getSurplusDiamond() {
        return surplusDiamond;
    }

    public void setSurplusDiamond(double surplusDiamond) {
        this.surplusDiamond = surplusDiamond;
    }

    @Basic
    @Column(name = "total_ticket", nullable = false, precision = 2)
    public double getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(double totalTicket) {
        this.totalTicket = totalTicket;
    }

    @Basic
    @Column(name = "total_diamond", nullable = false, precision = 2)
    public double getTotalDiamond() {
        return totalDiamond;
    }

    public void setTotalDiamond(double totalDiamond) {
        this.totalDiamond = totalDiamond;
    }

    @Basic
    @Column(name = "total_consume", nullable = false, precision = 2)
    public double getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(double totalConsume) {
        this.totalConsume = totalConsume;
    }

    @Basic
    @Column(name = "frozen_diamond", nullable = false, precision = 2)
    public double getFrozenDiamond() {
        return frozenDiamond;
    }

    public void setFrozenDiamond(double frozenDiamond) {
        this.frozenDiamond = frozenDiamond;
    }

    public void copy(UserPocket userPocket){
        super.copy(userPocket);
        this.surplusTicket = userPocket.getSurplusTicket();
        this.surplusDiamond = userPocket.getSurplusDiamond();
        this.totalTicket = userPocket.getTotalTicket();
        this.totalDiamond = userPocket.getTotalDiamond();
        this.totalConsume = userPocket.getTotalConsume();
        this.frozenDiamond = userPocket.getFrozenDiamond();
    }
}
