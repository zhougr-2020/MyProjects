package com.nanhang.mybatis_plus.style.strategy;

import java.util.HashMap;
import java.util.Map;

public class Context {

		// 缓存所有的策略，当前是无状态的，可以共享策略类对象
    private static final Map<String, CarStrategy> strategies = new HashMap<>();

    // 第一种写法
    static {
        strategies.put("one", new CarStrategyOne());
    }

    public static CarStrategy getStrategy(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return strategies.get(type);
    }

    // 第二种写法
    public static CarStrategy getStrategySecond(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        if (type.equals("one")) {
            return new CarStrategyOne();
        }
        return null;
    }


    public static void main(String[] args) {
        // 测试结果
        CarStrategy strategyOne = Context.getStrategy("one");
        strategyOne.algorithm("1档");
         // 结果：当前档位1档
        CarStrategy strategyTwo = Context.getStrategySecond("one");
        strategyTwo.algorithm("1档");
        // 结果：当前档位1档
    }

}
