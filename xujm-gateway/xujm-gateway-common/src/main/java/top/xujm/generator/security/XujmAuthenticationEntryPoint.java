package top.xujm.generator.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.security.common.SecurityResultEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Xujm
 */
@Component
public class XujmAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,HttpServletResponse response,AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject object;
        if(authException instanceof InsufficientAuthenticationException){
            object = LibSysUtil.getResultJSON(SecurityResultEnum.AUTH_ERROR.getCode(),authException.getMessage());
        }else{
            object = LibSysUtil.getResultJSON(BaseResultEnum.ERROR.getCode(),authException.getMessage());
        }
        LibSysUtil.out(response,object);
    }
}