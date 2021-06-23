package com.nanhang.mybatis_plus.style.build;

/**
 * @author: immortal
 * @CreateDate: 2021/5/20 10:22
 * @Description:
 */
public abstract class Builder {
    protected Product product = new Product();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    public Product getResult(){
        return product;
    }
}
