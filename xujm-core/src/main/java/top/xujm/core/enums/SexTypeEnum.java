package top.xujm.core.enums;

/**
 * @author Xujm
 */
public enum SexTypeEnum {

    /**
     * 男
     */
    MAN((byte)1),
    /**
     * 女
     */
    WOMAN((byte)2),
    /**
     * 未知
     */
    UNKNOWN((byte)0);

    private byte value;

    public byte getValue() {
        return value;
    }

    SexTypeEnum(byte value){
        this.value = value;
    }

    public static SexTypeEnum getEnum(int value) {
        SexTypeEnum sexTypeEnum = null;
        SexTypeEnum[] sexType = SexTypeEnum.values();
        for (SexTypeEnum info : sexType) {
            if (info.getValue() == value) {
                sexTypeEnum = info;
            }
        }
        return sexTypeEnum;
    }

}
