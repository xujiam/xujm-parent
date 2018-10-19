package top.xujm.core.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Xujm
 */
@Target({ElementType.METHOD,ElementType.FIELD})  //注解作用域
@Retention(RetentionPolicy.RUNTIME)  //注解作用时间
@Constraint(validatedBy = IsJsonStrValidator.class) //执行校验逻辑的类
public @interface IsJsonStr {

    String message() default "请输入JSON字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
