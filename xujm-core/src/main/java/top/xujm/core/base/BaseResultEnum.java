package top.xujm.core.base;

/**
 * @author Xujm
 */
public enum BaseResultEnum {

    /**
     * 成功，其他code失败
     */
    SUCCESS(0),

    /**
     * 默认返回错误code值
     * 用于过滤器中产生异常无法完成自定义code
     */
    ERROR(-1),

    /**
     * 功能关闭
     */
    FUNCTION_CLOSE(900,"platform.function.close"),

    /**
     * 配置有误
     */
    CONFIG_ERROR(901,"platform.config.error"),

    /**
     * 数据有误
     */
    DATA_ERROR(902,"platform.data.error"),

    /**
     * 经纬度有误
     */
    LOCATION_ERROR(903,"platform.location.error"),


    ACCESS_DENIED(904,"platform.access.denied");

    private int code;

    private String message;

    BaseResultEnum(int code){
        this.code = code;
    }

    BaseResultEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
