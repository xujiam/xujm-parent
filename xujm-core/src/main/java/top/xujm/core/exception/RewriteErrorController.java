package top.xujm.core.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xujm.core.base.BaseConstants;
import top.xujm.core.base.BaseResultEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
@RestController
public class RewriteErrorController extends BasicErrorController {

    public RewriteErrorController(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    public RewriteErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public RewriteErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.ALL));
        Exception ex = (Exception)request.getAttribute("javax.servlet.error.exception");
        if(ex instanceof RemindException){
            body.put(BaseConstants.RESULT_CODE, ((RemindException) ex).getCode());
        }else{
            body.put(BaseConstants.RESULT_CODE, BaseResultEnum.ERROR.getCode());
        }
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
