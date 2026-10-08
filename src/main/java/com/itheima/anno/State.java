package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {StateValidation.class} //指定提供校验规则的类
)
@Target({ElementType.FIELD})//元注解
@Retention(RetentionPolicy.RUNTIME)

public @interface State {
    //错误提示信息
    String message() default "state参数的值只能是已发布或者草稿";
    //分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};
}
