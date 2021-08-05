package com.nanhang.mybatis_plus.annotation;

import java.lang.annotation.*;

/**
 * @author: immortal
 * @CreateDate: 2021/3/1 11:29
 * @Description: 登陆效验
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {

     boolean check() default true;

     String checkName() default "system";
}
