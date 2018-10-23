package top.xujm.modules.security.common.redis;

/**
 * @author Xujm
 */
public enum SecurityRedisCacheEnum {

    /**
     * 验证码缓存
     */
    CAPTCHA("captcha:%s");

    private String key;

    SecurityRedisCacheEnum(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
