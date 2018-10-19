package top.xujm.core.enums;

/**
 * @author Xujm
 */
public enum BooleanTypeEnum {

    TRUE((byte)1),

    FALSE((byte)0);

    private byte type;

    BooleanTypeEnum(byte type){
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
