package top.xujm.core.base;

/**
 * @author Xujm
 */
public interface BaseConstants {

    /**
     * 返回结果集的参数定义
     */
    String RESULT_CODE = "code";
    String RESULT_MESSAGE = "message";
    String RESULT_LIST = "list";
    String RESULT_DATA = "data";
    String RESULT_ENCRYPT = "result";

    /**
     * 表名前缀
     */
    String TABLE_PREFIX = "xujm";

    String DATABASE_SCHEMA = "xujm";
    /**
     * 表名连接符
     */
    String TABLE_LINK_SYMBOL = "_";

    /**
     * 默认手机区号
     */
    String DEFAULT_AREA_CODE = "86";
    /**
     * 中国手机区号
     */
    String CHINA_AREA_CODE = "86";

    /**
     * 默认代表空字符串(用于缓存查询无数据是替代防击穿)
     */
    String DEFAULT_EMPTY = "nil";
    /**
     * 默认代表成功字符串
     */
    String DEFAULT_SUCCESS = "success";

    /**
     * 默认换行符号
     */
    String DEFAULT_WRAP = "\n";
    /**
     * 默认状态字段
     */
    String DEFAULT_STATUS = "status";

    /**
     * 成功状态码
     */
    int SUCCESS_CODE = 0;

    /**
     * 默认数据列表偏移数
     */
    int DEFAULT_INDEX = 0;
    /**
     * 默认数据列表每页条数
     */
    int DEFAULT_COUNT = 10;

}
