package top.xujm.core.plugins.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

/**
 * 根据service方法名匹配切换数据源
 * 如果配置了注解TargetDataSource切换将会失效
 * @author Xujm
 * @date 2017/12/16
 */
@Aspect
@Order(-10)//保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String[] readMethodPattern = new String[]{"find*","get*","select*"};

    /**
     *  定义切入点，提供一个方法，这个方法的名字就是改切入点的id
     */
    @Pointcut("execution(* top.xujm.*.service.impl.*.*(..))")
    private void allMethod(){}
    //针对指定的切入点表达式选择的切入点应用前置通知

    @Before("allMethod()")
    public void before(JoinPoint call) {
        String methodName = call.getSignature().getName();
        for(String methodPattern:readMethodPattern){
            if(isMatch(methodName,methodPattern)){
                DataSourceContextHolder.setTargetDataSource(DataSourceEnum.SLAVE.getCode());
                logger.debug("【后置通知】连接从库");
                return;
            }
        }
    }

    //访问命名切入点来应用后置通知
    @AfterReturning("allMethod()")
    public void afterReturn() {
        System.out.println("【注解-后置通知】:方法正常结束了");
    }
    //应用最终通知
    @After("allMethod()")
    public void after(){
        System.out.println("【注解-最终通知】:不管方法有没有正常执行完成,"
                + "一定会返回的");
    }
    //应用异常抛出后通知
    @AfterThrowing("allMethod()")
    public void afterThrowing() {
        System.out.println("【注解-异常抛出后通知】:方法执行时出异常了");
    }
    //应用周围通知
    //@Around("allMethod()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable{
        Object result = null;
        this.before(call);//相当于前置通知
        try {
            result = call.proceed();
            this.afterReturn(); //相当于后置通知
        } catch (Throwable e) {
            this.afterThrowing();  //相当于异常抛出后通知
            throw e;
        }finally{
            this.after();  //相当于最终通知
        }
        return result;
    }

    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

}
