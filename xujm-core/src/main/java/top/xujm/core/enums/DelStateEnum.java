package top.xujm.core.enums;

/**
 * @author Xujm
 * 代表删除状态的code定义
 */
public enum DelStateEnum {


    DEL((byte) 1),

    NOT((byte) 0);

    private byte state;

    public byte getState() {
        return state;
    }

    DelStateEnum(byte state){
        this.state = state;
    }


}
