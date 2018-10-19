package top.xujm.core.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.xujm.core.base.BaseAspect;


/**
 * AOP处理控制类日志
 * @author Xujm
 */
@Component
@Aspect
public class LogControllerAspect extends BaseAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 切入点
     */
    @Pointcut("execution(* top.xujm.modules.*.controller.*.*(..))")
    public void entryPoint() {
        // 无需内容
    }

    @Before("entryPoint()")
    public void before(JoinPoint joinPoint) {
        logger.info("LogControllerAspectByBefore:" + getTargetName(joinPoint)
                + ",method：" + getMethodName(joinPoint) + ",param:"+ getParamNameValue(joinPoint));
    }

    @AfterReturning(value = "entryPoint()",returning = "rvt")
    public void after(JoinPoint joinPoint,Object rvt) {
        String ret = "";
        if(rvt != null){
            ret = rvt.toString();
        }
        logger.info("LogControllerAspectByAfter:" + getMethodName(joinPoint)
                + ",method：" + getMethodName(joinPoint) + ",return: "+ ret);
    }



}

