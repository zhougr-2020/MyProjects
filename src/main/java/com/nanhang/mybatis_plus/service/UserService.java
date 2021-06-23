package com.nanhang.mybatis_plus.service;

import com.nanhang.mybatis_plus.pojo.User;
import com.nanhang.mybatis_plus.style.factory.Result;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findOne(Integer id);

    int save();

    User findByUser(String username);

//    List<Menu> getAllMenu();

    void update(List<User> list);

    boolean insert (Integer number);

    Result check(User user);
}
