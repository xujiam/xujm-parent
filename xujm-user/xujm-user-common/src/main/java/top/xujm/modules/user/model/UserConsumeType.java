package top.xujm.modules.user.model;

import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_consume_type", schema = "weking")
public class UserConsumeType {
    private int id;
    private String consumeCode;
    private double transRate;
    private byte roundWay;
    private byte isEnable;
    private byte isFrozen;
    private String mark;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "consume_code")
    public String getConsumeCode() {
        return consumeCode;
    }

    public void setConsumeCode(String consumeCode) {
        this.consumeCode = consumeCode;
    }

    @Basic
    @Column(name = "trans_rate", nullable = false, precision = 2)
    public double getTransRate() {
        return transRate;
    }

    public void setTransRate(double transRate) {
        this.transRate = transRate;
    }

    @Basic
    @Column(name = "round_way", nullable = true, length = 100)
    public byte getRoundWay() {
        return roundWay;
    }

    public void setRoundWay(byte roundWay) {
        this.roundWay = roundWay;
    }

    @Basic
    @Column(name = "is_frozen", nullable = false)
    public byte getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(byte isFrozen) {
        this.isFrozen = isFrozen;
    }

    @Basic
    @Column(name = "is_enable", nullable = false)
    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
    }

    @Basic
    @Column(name = "mark", nullable = true, length = 100)
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

}
