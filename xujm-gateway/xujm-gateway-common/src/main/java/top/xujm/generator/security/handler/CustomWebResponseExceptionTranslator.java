package top.xujm.generator.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.security.common.SecurityResultEnum;

/**
 * @author Xujm
 */
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e){
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity.status(HttpStatus.OK)
                .body(LibSysUtil.getResultJSON(SecurityResultEnum.REFRESH_TOKEN_ERROR.getCode(),oAuth2Exception.getMessage()));
    }
}
