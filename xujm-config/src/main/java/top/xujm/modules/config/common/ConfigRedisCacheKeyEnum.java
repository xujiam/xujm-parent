package top.xujm.modules.config.common;

/**
 * @author Xujm
 */
public enum ConfigRedisCacheKeyEnum {

    /**
     * APP配置
     */
    APP_CONFIG("appConfig");

    private String key;

    public String getKey(int liveId) {
        return String.format(key,liveId);
    }

    public String getKey() {
        return key;
    }

    ConfigRedisCacheKeyEnum(String key){
        this.key = key;
    }

}
