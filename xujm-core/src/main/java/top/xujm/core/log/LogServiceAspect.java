package top.xujm.core.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xujm.core.base.BaseAspect;

/**
 * @author Xujm
 */
public class LogServiceAspect extends BaseAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 切入点
     */
    @Pointcut("execution(* top.xujm.modules.*.sevice.*.*(..))")
    public void entryPoint() {
        // 无需内容
    }

    @Before("entryPoint()")
    public void before(JoinPoint joinPoint) {
        logger.info("LogServiceAspect:" + getTargetName(joinPoint)
                + ",method：" + getMethodName(joinPoint) + ",param:"+ getParamNameValue(joinPoint));
    }

    @AfterReturning(value = "entryPoint()",returning = "rvt")
    public void after(JoinPoint joinPoint,Object rvt) {
        logger.info("LogServiceAspect:" + getMethodName(joinPoint)
                + ",method：" + getMethodName(joinPoint) + ",return: "+ rvt.toString());
    }


}
