package top.xujm.modules.security.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_user_login_mode", schema = "weking")
public class LoginMode {
    private int id;
    private String providerName;
    private String providerId;
    private String clientId;
    private String clientSecret;
    private String providerExtend;
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
    @Column(name = "provider_name", nullable = false, length = 20)
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Basic
    @Column(name = "provider_id", nullable = false, length = 11)
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Basic
    @Column(name = "client_id", nullable = false, length = 30)
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "client_secret", nullable = false, length = 50)
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Basic
    @Column(name = "provider_extend", nullable = false, length = 300)
    public String getProviderExtend() {
        return providerExtend;
    }

    public void setProviderExtend(String providerExtend) {
        this.providerExtend = providerExtend;
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
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        LoginMode loginMode = (LoginMode) o;
        return id == loginMode.id &&
                isEnable == loginMode.isEnable &&
                Objects.equals(providerName, loginMode.providerName) &&
                Objects.equals(providerId, loginMode.providerId) &&
                Objects.equals(clientId, loginMode.clientId) &&
                Objects.equals(clientSecret, loginMode.clientSecret) &&
                Objects.equals(providerExtend, loginMode.providerExtend);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, providerName, providerId, clientId, clientSecret, providerExtend, isEnable);
    }
}
