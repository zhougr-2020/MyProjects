package com.nanhang.mybatis_plus.lambdaInterface;

/**
 * @author: immortal
 * @CreateDate: 2021/6/22 10:31
 * @Description:
 */
@FunctionalInterface
public interface UserInterface<T> {

    void query(String name);
}
