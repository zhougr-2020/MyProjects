package com.nanhang.mybatis_plus.style.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author: immortal
 * @CreateDate: 2021/4/20 14:38
 * @Description: 动态拼接
 */
public class PayServiceTrends implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final String SUFFIX = "Pay";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Result toPay(String payCode) {
        return ((IPay) applicationContext.getBean(payCode + SUFFIX)).pay();
    }
}
