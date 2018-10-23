package top.xujm.modules.user.common;

/**
 * @author Xujm
 */
public enum UserRedisCacheKeyEnum {

    /**
     * 用户储存
     */
    USER("user:%d",7);



    private String key;
    /**
     * 过期时间(天)
     */
    private long expireTime;

    public String getKey(int userId) {
        return String.format(key,userId);
    }

    public String getKey() {
        return key;
    }

    public long getExpireTime() {
        return expireTime;
    }

    UserRedisCacheKeyEnum(String key,long expireTime){
        this.key = key;
        this.expireTime = expireTime;
    }
}
