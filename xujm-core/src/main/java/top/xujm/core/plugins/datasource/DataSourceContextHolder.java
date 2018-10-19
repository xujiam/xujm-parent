package top.xujm.core.plugins.datasource;

/**
 * 多数据源/读写分离
 * @author Xujm
 * @date 2017/12/15
 */
public class DataSourceContextHolder {
    /**
     * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> dataSourceLocal = new ThreadLocal<>();

    public static ThreadLocal<String> getDataSourceLocal() {
        return dataSourceLocal;
    }

    /**
     * 从库 可以有多个
     */
    public static void slave() {
        dataSourceLocal.set(DataSourceEnum.SLAVE.getCode());
    }

    /**
     * 主库 只有一个
     */
    public static void master() {
        dataSourceLocal.set(DataSourceEnum.MASTER.getCode());
    }


    public static void setTargetDataSource(String code) {
        dataSourceLocal.set(code);
    }

    public static String getTargetDataSource() {
        return dataSourceLocal.get();
    }
}
