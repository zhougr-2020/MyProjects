package com.nanhang.mybatis_plus.style.factory;

import org.springframework.stereotype.Service;

/**
 * @author: immortal
 * @CreateDate: 2021/4/20 9:19
 * @Description:
 */
@Service
@PayCode(value = "Ali",name="支付宝")
public class AliPay implements IPay {
    @Override
    public Result pay() {
        System.out.println("支付宝支付!!!!!!!!!!!!!!!!!!");
        return new Result();
    }
}
