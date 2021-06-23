package com.nanhang.mybatis_plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanhang.mybatis_plus.pojo.User;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    User findOne(Integer id);


    void updateUsers(List<User> list);

    void insertList(List<User> list);
}
