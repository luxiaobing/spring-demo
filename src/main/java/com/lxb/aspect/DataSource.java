package com.lxb.aspect;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-06-15 16:29
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value();
}
