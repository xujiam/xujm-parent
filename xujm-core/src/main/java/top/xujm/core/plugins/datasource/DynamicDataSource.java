package top.xujm.core.plugins.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 路由
 * @author Xujm
 * @date 2017/12/15
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    public DynamicDataSource(){

    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getTargetDataSource();
    }
}
