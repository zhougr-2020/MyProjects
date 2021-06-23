package com.nanhang.mybatis_plus.style.build;

/**
 * @author: immortal
 * @CreateDate: 2021/5/20 10:28
 * @Description:
 */
public class ConcreteBuilder extends Builder {
    @Override
    public void buildPartA() {
        product.setPartA("builder PartA");
    }

    @Override
    public void buildPartB() {
       product.setPartB("builder PartB");
    }

    @Override
    public void buildPartC() {
        product.setPartC("builder PartC");

    }
}
