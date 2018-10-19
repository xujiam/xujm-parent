package top.xujm.core.plugins.datasource;

import java.lang.annotation.*;

/**
 * @author Xujm
 * @date 2017/12/15
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    DataSourceEnum value();

}
