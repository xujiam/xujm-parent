package top.xujm.generator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Xujm
 */
@Component
public class XujmBasicClientHandle {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailsService clientDetailsService;

    private static BASE64Decoder base64Decoder = new BASE64Decoder();


    public ClientDetails loadClientDetails(HttpServletRequest request) throws IOException {
        String basic = "Basic ";
        //获取请求头里Authorization信息
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(basic)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        //构造OAuth2Request 第一步获取clientId,base64解码获取clientId、clientSecret
        String[] tokens = extractAndDecodeHeader(header);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        //构造OAuth2Request 第二步，根据clientId 获取ClientDetails对象
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在，clientId:"+clientId);
        }else if(!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())){
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配，clientId:"+clientId);
        }
        return clientDetails;
    }

    /**
     * base64解码请求头 Basic aW1vb2M6aW1vb2NzZWNyZXQ=
     * Decodes the header into a username and password.
     */
    private String[] extractAndDecodeHeader(String header)throws IOException {
        byte[] decoded;
        try {
            //解码后格式   用户名:密码
            decoded = base64Decoder.decodeBuffer(header.substring(6));
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, "UTF-8");
        int delimit = token.indexOf(":");
        if (delimit == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        //返回的数组是   [用户名(就是client_id),clientSecret] 其实就是配置的
        return new String[] { token.substring(0, delimit), token.substring(delimit + 1) };
    }

}
