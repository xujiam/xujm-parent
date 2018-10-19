package top.xujm.core.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring应用上下文环境管理类
 * @author Xujm
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name){
        try {
            return getApplicationContext().getBean(name);
        }catch (NoSuchBeanDefinitionException e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz){
        try {
            return getApplicationContext().getBean(clazz);
        }catch (NoSuchBeanDefinitionException e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 通过接口类获得所有子类bean
     * Map<String,A> map = SpringContextUtil.getBeanOfType(A.class);
     * for (A a : map.values()) {
     *    a.a();
     * }
     */
    public static <T> Map<String, T> getBeanOfType(Class<T> clazz) {
        try {
            return getApplicationContext().getBeansOfType(clazz);
        }catch (NoSuchBeanDefinitionException e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name,Class<T> clazz){
        try {
            return getApplicationContext().getBean(name, clazz);
        }catch (NoSuchBeanDefinitionException e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


}
