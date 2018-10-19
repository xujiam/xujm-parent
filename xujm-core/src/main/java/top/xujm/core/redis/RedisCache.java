package top.xujm.core.redis;


/**
 * @author Xujm
 */
public class RedisCache {

    /**
     * 组装key
     */
    protected String getKey(String prefix,String badge){
        return String.format(prefix,badge);
    }

    protected String getKey(String prefix,int badge){
        return String.format(prefix,badge);
    }


}
