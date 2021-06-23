package com.nanhang.mybatis_plus.style.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: immortal
 * @CreateDate: 2021/4/20 9:26
 * @Description:  注解
 */
@Component
public class PayService implements ApplicationListener<ContextRefreshedEvent> {
    private static Map<String, IPay> payMap = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PayCode.class);
        if (beansWithAnnotation != null) {
            payMap = new HashMap<>();
            beansWithAnnotation.forEach((key, value) -> {
                String payCode = value.getClass().getAnnotation(PayCode.class).value();
                payMap.put(payCode, (IPay) value);
            });
        }
    }

    public Result pay(String code) {
        return payMap.get(code).pay();
    }
}
