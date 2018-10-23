package top.xujm.modules.user.common;

/**
 * @author Xujm
 */
public enum UserIdTypeEnum {

    /**
     * 代表系统
     */
    SYSTEM(-1),
    /**
     * 代表游客
     */
    VISITOR(0);

    private int userId;

    UserIdTypeEnum(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
