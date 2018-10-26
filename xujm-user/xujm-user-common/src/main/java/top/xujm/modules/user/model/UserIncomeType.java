package top.xujm.modules.user.model;

import top.xujm.core.base.BaseConstants;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_income_type", schema = BaseConstants.DATABASE_SCHEMA)
public class UserIncomeType {
    private int id;
    private String incomeCode;
    private byte isEnable;
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
    @Column(name = "income_code", nullable = false, length = 20)
    public String getIncomeCode() {
        return incomeCode;
    }

    public void setIncomeCode(String incomeCode) {
        this.incomeCode = incomeCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIncomeType that = (UserIncomeType) o;
        return id == that.id &&
                isEnable == that.isEnable &&
                Objects.equals(incomeCode, that.incomeCode) &&
                Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, incomeCode, isEnable, mark);
    }
}
