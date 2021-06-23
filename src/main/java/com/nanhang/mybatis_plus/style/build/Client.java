package com.nanhang.mybatis_plus.style.build;

/**
 * @author: immortal
 * @CreateDate: 2021/5/20 10:36
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        ConcreteBuilder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.construct();
        product.show();
    }
}
