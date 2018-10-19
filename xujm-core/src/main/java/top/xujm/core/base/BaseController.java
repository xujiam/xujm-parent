package top.xujm.core.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import top.xujm.core.exception.RemindException;
import top.xujm.core.utils.LibSysUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xujm
 */
public class BaseController{

    /**
     * 获取请求客户端真实IP
     */
    protected String getRequestIp(HttpServletRequest request) {
        return LibSysUtil.getRequestIp(request);
    }

    protected void throwException(BindingResult result){
        if (result.hasErrors()){
            if(result.getFieldError() != null){
                String message = String.format("%s%s",result.getFieldError().getField(),result.getFieldError().getDefaultMessage());
                throw new RemindException(BaseResultEnum.ERROR.getCode(),message);
            }
            throw new RemindException(BaseResultEnum.DATA_ERROR.getCode(),BaseResultEnum.DATA_ERROR.getMessage());
        }
    }

    protected void isNull(String data,String filed){
        if(StringUtils.isEmpty(data)){
            throw new RemindException(BaseResultEnum.ERROR.getCode()
                    ,String.format("%s is empty",filed));
        }
    }

    protected void isNull(Object data,String filed){
        if(data == null){
            throw new RemindException(BaseResultEnum.ERROR.getCode()
                    ,String.format("%s is empty",filed));
        }
    }

    protected void error(String msg){
            throw new RemindException(BaseResultEnum.ERROR.getCode()
                    ,msg);
    }

    protected void isNull(Integer data,String filed){
        if(data == null){
            throw new RemindException(BaseResultEnum.ERROR.getCode()
                    ,String.format("%s is empty",filed));
        }
    }
}
