package top.xujm.modules.oss.common;

/**
 * @author Xujm
 */
public enum OssResultEnum {

    /**
     * 上传类型不支持
     */
    OSS_ERROR(5000,"oss.type.code.error"),

    UPLOAD_ERROR(5001,"oss.upload.error"),

    GENERATOR_ERROR(5002,"oss.generator.error"),

    FILE_TYPE_ERROR(5003,"oss.file.type.error");


   private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    OssResultEnum(int code,String msg){
        this.code = code;
        this.message = msg;
    }
}
