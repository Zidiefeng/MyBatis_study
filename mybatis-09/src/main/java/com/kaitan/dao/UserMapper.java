package com.kaitan.dao;

import com.kaitan.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User queryUsersById(@Param("id") int id);
}
