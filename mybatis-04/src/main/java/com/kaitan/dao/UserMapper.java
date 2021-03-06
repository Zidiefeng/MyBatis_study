package com.kaitan.dao;

import com.kaitan.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    //根据id查询用户
    User getUserById(int id);

    //sql分页
    List<User> getUserByLimit(Map<String,Integer> map);

    //用面向对象 分页
    List<User> getUserByRowBounds();
}
