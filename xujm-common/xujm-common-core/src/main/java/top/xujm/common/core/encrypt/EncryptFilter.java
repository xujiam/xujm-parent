package top.xujm.common.core.encrypt;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import top.xujm.common.core.constant.ConfigConstant;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibSysUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xujm
 */
@Order(-100)
public class EncryptFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Map<String, Object> extraParams = new HashMap<>(0);
        String data = request.getParameter("data");
        if(LibConverterUtil.toBoolean(ResourceConfig.getConfig(ConfigConstant.dataEncrypt))){
            if(StringUtils.isNotEmpty(data)){
                //data = WkUtils.decryptString(data);
            }
        }else {
            if(StringUtils.isNotEmpty(data)){
                data = LibSysUtil.urlDecode(data);
            }
        }
        if(StringUtils.isNotEmpty(data)){
            extraParams = JSONObject.parseObject(data);
        }
        //利用原始的request对象创建自己扩展的request对象并添加自定义参数
        RequestParameterWrapper requestParameterWrapper = new RequestParameterWrapper(request);
        requestParameterWrapper.addParameters(extraParams);
        filterChain.doFilter(requestParameterWrapper, servletResponse);
    }


    @Override
    public void destroy() {

    }


}
