package top.xujm.modules.config.common;

/**
 * @author Xujm
 */
public enum ConfigLocalEnum {

    /**
     * 配置适用所有
     */
    ALL(0),
    API(1),
    APP(2),
    ADMIN(3);

    private int type;

    ConfigLocalEnum(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
