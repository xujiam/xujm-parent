package top.xujm.modules.user.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_user_extend_key", schema = "weking")
public class UserExtendKey {
    private int id;
    private String extendKey;
    private String extendVerify;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "extend_key", nullable = true, length = 16)
    public String getExtendKey() {
        return extendKey;
    }

    public void setExtendKey(String extendKey) {
        this.extendKey = extendKey;
    }

    @Basic
    @Column(name = "extend_verify", nullable = true, length = 100)
    public String getExtendVerify() {
        return extendVerify;
    }

    public void setExtendVerify(String extendVerify) {
        this.extendVerify = extendVerify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserExtendKey that = (UserExtendKey) o;
        return id == that.id &&
                Objects.equals(extendKey, that.extendKey) &&
                Objects.equals(extendVerify, that.extendVerify);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, extendKey, extendVerify);
    }
}
