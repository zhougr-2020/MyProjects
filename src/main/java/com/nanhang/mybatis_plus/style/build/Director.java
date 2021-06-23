package com.nanhang.mybatis_plus.style.build;

/**
 * @author: immortal
 * @CreateDate: 2021/5/20 10:34
 * @Description:
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
