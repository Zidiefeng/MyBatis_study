package com.kaitan.dao;

import com.kaitan.pojo.User;
import com.kaitan.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test(){
        //获得sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //执行sql

        /*第一种方法*/
        //这里，我们的mapper配置文件绑定了UserDao接口，那么调用这个接口的class，
            //即UserDao.class,则可以获得mapper,来执行里面的方法了
            //理解，UserMapper.xml只是UserDao接口的实现类
            //拿到UserDao类就好了
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }

        /*第二种方法*/
        //过时了,不推荐
        //
/*
        List<User> userList = sqlSession.selectList("com.kaitan.dao.UserDao.getUserList");

        for (User user : userList) {
            System.out.println(user);
        }
*/

        sqlSession.close();

    }
}
