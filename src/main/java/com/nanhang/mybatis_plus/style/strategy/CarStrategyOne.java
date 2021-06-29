package com.nanhang.mybatis_plus.style.strategy;

import com.nanhang.mybatis_plus.style.factory.Result;

/**
 * @author: immortal
 * @CreateDate: 2021/6/24 9:05
 * @Description:
 */
public class CarStrategyOne implements CarStrategy {
    @Override
    public Result algorithm(String param) {
        return new Result(0,"success","当前档位为"+param);
    }
}
