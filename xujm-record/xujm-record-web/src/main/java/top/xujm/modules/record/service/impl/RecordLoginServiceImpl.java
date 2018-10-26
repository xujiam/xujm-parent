package top.xujm.modules.record.service.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.xujm.core.base.BaseAspect;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.record.model.RecordLogin;
import top.xujm.modules.record.repository.RecordLoginRepository;
import top.xujm.modules.security.common.LibSecurityUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录日志记录
 * @author Xujm
 */
@Aspect
@Component
public class RecordLoginServiceImpl extends BaseAspect {

    @Autowired
    private RecordLoginRepository recordLoginRepository;

    @Autowired
    private RedisTemplate<String,?> redisTemplate;

    @Pointcut("execution(* top.xujm.modules.config.controller.ServerController.getAppConfig(..))")
    public void recordStartCut(){}

    @Before("recordStartCut()")
    public void recordStart(JoinPoint joinPoint){
        record();
    }


    @AfterReturning("execution(* top.xujm.generator.security.authentication.XujmAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
    public void recordLogin(JoinPoint joinPoint){
        record();
    }

    private void record(){
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        int userId = LibSecurityUtil.getUserId();
        long loginTime = LibDateUtil.getNowTime();
        RecordLogin recordLogin = new RecordLogin();
        recordLogin.setAppCode(request.getParameter("appCode"));
        recordLogin.setAppVersion(request.getParameter("appVersion"));
        recordLogin.setClientIp(LibSysUtil.getRequestIp(request));
        recordLogin.setDeviceModel(request.getParameter("deviceModel"));
        recordLogin.setLoginCode(request.getParameter("loginCode"));
        recordLogin.setImei(request.getParameter("imei"));
        recordLogin.setUserId(userId);
        recordLogin.setLoginTime(loginTime);
        recordLoginRepository.save(recordLogin);
        if(userId > 0){
            recordLoginTime(userId,loginTime);
        }

    }

    private void recordLoginTime(int userId,long loginTime){
        redisTemplate.opsForHash().put("userLoginTime", LibConverterUtil.toString(userId), LibConverterUtil.toString(loginTime));
    }

}
