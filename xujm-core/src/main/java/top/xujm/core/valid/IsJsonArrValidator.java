package top.xujm.core.valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Xujm
 */
public class IsJsonArrValidator implements ConstraintValidator<IsJsonArr,String> {

    @Override
    public void initialize(IsJsonArr constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(s)){
            return true;
        }
        try {
            JSON.parseArray(s);
            return true;
        }catch (JSONException e){
            return false;
        }
    }
}
