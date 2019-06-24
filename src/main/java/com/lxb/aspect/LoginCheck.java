package com.lxb.aspect;
import java.lang.annotation.*;


/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-06-01 12:44
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
    String value() default "";
    int expiretime() default 0;
}
