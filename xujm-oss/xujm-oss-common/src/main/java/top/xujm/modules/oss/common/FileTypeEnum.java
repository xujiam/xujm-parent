package top.xujm.modules.oss.common;



/**
 * @author Xujm
 */
public enum FileTypeEnum {


    /**
     * 图片
     */
    IMAGE((byte)1,"jpg,gif,png,jpeg"),
    /**
     * 视频
     */
    VIDEO((byte)2,"mp4"),

    ZIP((byte)3,"zip,tar.gz"),

    APK((byte)4,"apk");

    private Byte fileType;
    /**
     * 所支持的格式
     */
    private String format;

    FileTypeEnum(Byte fileType, String format){
        this.fileType = fileType;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public Byte getFileType() {
        return fileType;
    }

    public static FileTypeEnum getEnum(int value) {
        FileTypeEnum fileTypeEnum = null;
        FileTypeEnum[] fileType = FileTypeEnum.values();
        for (FileTypeEnum info : fileType) {
            if (info.getFileType() == value) {
                fileTypeEnum = info;
            }
        }
        return fileTypeEnum;
    }


}
