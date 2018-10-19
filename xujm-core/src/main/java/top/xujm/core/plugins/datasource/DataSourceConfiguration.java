package top.xujm.core.plugins.datasource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xujm
 * @date 2017/12/15
 */
@Configuration
@EnableJpaRepositories(basePackages = {"top.xujm.modules.*.repository"}, entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager")
public class DataSourceConfiguration {

    private Class<? extends DataSource> dataSourceType;

    @Primary
    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * 有多少个从库就要配置多少个
     */
    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSourceOne() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * AbstractRoutingDataSource继承了AbstractDataSource ,AbstractDataSource又实现了DataSource
     * 所以可以直接丢去构建 SqlSessionFactory
     */
    @Bean(name="routingDataSource")
    public DynamicDataSource dataSourceProxy(){
        DynamicDataSource proxy = new DynamicDataSource();
        Map<Object,Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceEnum.MASTER.getCode(),masterDataSource());
        dataSourceMap.put(DataSourceEnum.SLAVE.getCode(),slaveDataSourceOne());
        //设置默认数据源
        proxy.setDefaultTargetDataSource(dataSourceMap.get(DataSourceEnum.MASTER.getCode()));
        proxy.setTargetDataSources(dataSourceMap);
        return proxy;
    }

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSourceProxy());
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan("top.xujm.modules.*.model");
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(mysqlEntityManagerFactory().getObject());
        return manager;
    }
}
