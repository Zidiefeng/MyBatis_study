package com.kaitan.dao;

import com.kaitan.pojo.User;

public interface UserMapper {

    //根据id查询用户
    User getUserById(int id);

}
