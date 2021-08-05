package com.nanhang.mybatis_plus.style.strategy;

import com.nanhang.mybatis_plus.style.factory.Result;

/**
 * @author: immortal
 * @CreateDate: 2021/6/24 9:01
 * @Description:
 */
public interface CarStrategy {

    // 定义策略执行方法
    Result algorithm(String param);
}
