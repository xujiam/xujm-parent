package top.xujm.generator.security.authentication;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.generator.security.XujmBasicClientHandle;
import top.xujm.modules.security.common.LibSecurityUtil;

/**
 * @author Xujm
 *
 */
public class XujmAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ClientDetailsService clientDetailsService;
    @Autowired
	private DefaultTokenServices defaultTokenServices;
    @Autowired
	private XujmBasicClientHandle wekingBasicClientHandle;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException{
		ClientDetails clientDetails = wekingBasicClientHandle.loadClientDetails(request);
		 // 构造OAuth2Request 第三步，new TokenRequest
		 // 第一个参数是个map，放的是每个授权模式特有的参数，spring-security会根据这些参数构建Authentication
		 // 我们这里已获取到了Authentication，弄个空的map就可
		TokenRequest tokenRequest = new TokenRequest(new HashMap<>(0), clientDetails.getClientId(), clientDetails.getScope(), "custom");
		 //构造OAuth2Request 第四步，创建 OAuth2Request
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		//构造OAuth2Request 第五步，创建 OAuth2Authentication
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		//构造OAuth2Request 第六步，authorizationServerTokenServices创建 OAuth2AccessToken
		defaultTokenServices.setClientDetailsService(clientDetailsService);
        OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(oAuth2Authentication);
        JSONObject resObj = JSONObject.parseObject(objectMapper.writeValueAsString(accessToken));
        JSONObject object = LibSecurityUtil.getResultJSON(resObj);
		LibSysUtil.out(response,object);
	}




}
