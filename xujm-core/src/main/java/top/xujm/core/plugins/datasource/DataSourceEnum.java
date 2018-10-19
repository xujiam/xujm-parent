package top.xujm.core.plugins.datasource;

/**
 * @author Xujm
 * @date 2017/12/15
 */
public enum DataSourceEnum {
    /**
     * 主库
     */
    MASTER("master","主库"), SLAVE("slave","从库"),CREATE("create","建库");

    final private String code;

    final private String name;

    DataSourceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName(){
        return name;
    }

    public static String getNameByCode(String code){
        for(DataSourceEnum item : DataSourceEnum.values()){
            if(item.getCode().equals(code)){
                return item.getName();
            }
        }
        return "";
    }

}
