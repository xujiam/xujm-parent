package top.xujm.modules.user.common;

/**
 * @author Xujm
 */
public enum UserFollowStateEnum {

    /**
     * 未关注
     */
    NOT_FOLLOW((byte)0),

    FOLLOW((byte)1),

    MUTUAL_FOLLOW((byte)2);

    private Byte state;

    UserFollowStateEnum(byte state){
        this.state = state;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
