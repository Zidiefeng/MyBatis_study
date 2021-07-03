package com.kaitan.dao;

import com.kaitan.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();

    //根据id查询用户
    User getUserById(int id);

    //insert一个用户
    int addUser(User user);

    //使用map add用户
    User addUser2(Map<String,Object> map);

    //update一个用户
    int updateUser(User user);

    //delete一个用户
    int deleteUser(int id);

}
