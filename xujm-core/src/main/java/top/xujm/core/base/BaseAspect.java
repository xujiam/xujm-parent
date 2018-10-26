package top.xujm.core.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xujm
 */
public class BaseAspect {

    /**
     * 获得当前触发类的路径
     * com.xujm.login.controller.LoginController
     */
    protected String getTargetName(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass().getName();
    }

    /**
     * 获得当前触发方法
     */
    protected String getMethodName(JoinPoint joinPoint){
        return joinPoint.getSignature().getName();
    }

    /**
     * 获得当前调用所有参数
     */
    protected String getParams(JoinPoint joinPoint){
        return getParams(joinPoint,"&");
    }

    /**
     * 获得当前调用所有参数
     */
    protected String getParams(JoinPoint joinPoint,String symbol){
        Object[] arguments = getParamValues(joinPoint);
        StringBuilder paramsBuf = new StringBuilder();
        for (Object arg : arguments) {
            paramsBuf.append(arg);
            paramsBuf.append(symbol);
        }
        return paramsBuf.toString();
    }

    /**
     * 获得当前调用所有参数名值对
     */
    protected String getParamNameValue(JoinPoint joinPoint){
        return getParamNameValueMap(joinPoint).toString();
    }

    /**
     * 获得当前调用所有参数名值对Map
     */
    protected Map<String,Object> getParamNameValueMap(JoinPoint joinPoint){
        Object[] arguments = getParamValues(joinPoint);
        String[] paramName = getParamNames(joinPoint);
        if(paramName == null){
            return null;
        }
        int size = paramName.length;
        Map<String,Object> paramMap = new HashMap<>(size);
        for (int i=0;i<size;i++) {
            paramMap.put(paramName[i],arguments[i]);
        }
        return paramMap;
    }

    /**
     * 获得当前调用所有参数名
     */
    protected String[] getParamNames(JoinPoint joinPoint){
        MethodSignature argument = (MethodSignature)joinPoint.getSignature();
        return argument.getParameterNames();
    }
    /**
     * 获得当前调用所有参数值
     */
    protected Object[] getParamValues(JoinPoint joinPoint){
        return joinPoint.getArgs();
    }


}
