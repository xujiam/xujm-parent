package top.xujm.core.valid;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Xujm
 */
public class IsJsonStrValidator implements ConstraintValidator<IsJsonStr,String> {

    @Override
    public void initialize(IsJsonStr constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(s)){
            return true;
        }
        try {
            JSONObject.parseObject(s);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }
}
