package top.xujm.core.plugins.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 通过拦截TargetDataSource注解切换数据源
 * 会使方法匹配切换数据源失效
 * order注解保证该AOP在@Transactional之前执行
 * @author Xujm
 * @date 2017/12/15
 */
@Aspect
@Order(-10)
@Component
public class DynamicDataSourceAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 在TargetDataSource注解方法之前拦截
     */
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
        DataSourceContextHolder.setTargetDataSource(targetDataSource.value().getCode());
        logger.debug("TargetDataSource连接数据库：{}=={}",targetDataSource.value().getName(),point.getSignature());
    }

}
