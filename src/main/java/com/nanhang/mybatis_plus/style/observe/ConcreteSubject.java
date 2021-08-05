package com.nanhang.mybatis_plus.style.observe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConcreteSubject implements Subject {

    // 订阅者容器
    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void attach(Observer observer) {
        // 添加订阅关系
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        // 移除订阅关系
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {

        ThreadLocal<Integer> integerThreadLocal = ThreadLocal.withInitial(() -> 6);
        Integer num = integerThreadLocal.get();

        // 通知订阅者们
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}