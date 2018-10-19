package top.xujm.core.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xujm.core.utils.LibSysUtil;

/**
 * 全局异常处理控制器增强器
 * 针对自定义异常返回前端自定义数据
 * @author Xujm
 */
@RestControllerAdvice
public class ExceptionAdvice {


    @ExceptionHandler(RemindException.class)
    @ResponseStatus(HttpStatus.OK)
    public JSONObject handleRemindException(RemindException ex) {
        return LibSysUtil.getResultJSON(ex.getCode(),ex.getMessage());
    }

}
