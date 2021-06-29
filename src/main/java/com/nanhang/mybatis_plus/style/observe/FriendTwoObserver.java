package com.nanhang.mybatis_plus.style.observe;

/**
 * @author: immortal
 * @CreateDate: 2021/6/24 9:50
 * @Description:
 */
public class FriendTwoObserver implements Observer {
    @Override
    public void update(String message) {
        // 模拟处理业务逻辑
        System.out.println("FriendTwo 知道了你发动态了" + message);
    }
}
