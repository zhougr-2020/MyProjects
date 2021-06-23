package com.nanhang.mybatis_plus.style.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: immortal
 * @CreateDate: 2021/4/20 9:22
 * @Description:
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PayCode {
    String value();

    String name();
}
