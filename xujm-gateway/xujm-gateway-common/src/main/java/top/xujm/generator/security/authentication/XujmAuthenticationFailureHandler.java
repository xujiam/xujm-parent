package top.xujm.generator.security.authentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.language.Language;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.security.common.SecurityResultEnum;

/**
 * @author zhailiang
 *
 */
public class XujmAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException{
        Language.setLocale(request);
		if(exception instanceof BadCredentialsException){
            LibSysUtil.out(response,LibSysUtil.getResultJSON(SecurityResultEnum.ACCOUNT_PASSWORD_ERROR.getCode()
                    , Language.getMsg(SecurityResultEnum.ACCOUNT_PASSWORD_ERROR.getMessage())));
            return;
        }
        if(exception instanceof DisabledException){
            LibSysUtil.out(response,LibSysUtil.getResultJSON(SecurityResultEnum.ACCOUNT_DISABLE.getCode()
                    , Language.getMsg(SecurityResultEnum.ACCOUNT_DISABLE.getMessage())));
            return;
        }
        LibSysUtil.out(response,LibSysUtil.getResultJSON(BaseResultEnum.ERROR.getCode()
                , exception.getMessage()));
	}

}
