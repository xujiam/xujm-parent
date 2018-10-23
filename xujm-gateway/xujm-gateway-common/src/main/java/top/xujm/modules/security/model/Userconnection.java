package top.xujm.modules.security.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_user_UserConnection", schema = "weking")
public class Userconnection {
    private String userId;
    private String providerId;
    private String providerUserId;
    private int rank;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;

    @Basic
    @Column(name = "userId", nullable = false, length = 30)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "providerId", nullable = false, length = 30)
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Id
    @Column(name = "providerUserId", nullable = false, length = 100)
    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    @Basic
    @Column(name = "rank", nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "displayName", nullable = true, length = 255)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "profileUrl", nullable = true, length = 512)
    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Basic
    @Column(name = "imageUrl", nullable = true, length = 512)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "accessToken", nullable = false, length = 512)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "secret", nullable = true, length = 512)
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Basic
    @Column(name = "refreshToken", nullable = true, length = 512)
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Basic
    @Column(name = "expireTime", nullable = true)
    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Userconnection that = (Userconnection) o;
        return rank == that.rank &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(providerId, that.providerId) &&
                Objects.equals(providerUserId, that.providerUserId) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(profileUrl, that.profileUrl) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(secret, that.secret) &&
                Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, providerId, providerUserId, rank, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime);
    }
}
