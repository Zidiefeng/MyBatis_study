package com.kaitan.dao;

import com.kaitan.pojo.User;
import com.kaitan.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest {

    @Test
    public void getUserLike(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserLike("%李%");
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();//固定代码
    }

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
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
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

    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();//固定代码
    }

    @Test
    public void getUserById2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_id",2);
        User user = mapper.getUserById2(map);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void addUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int result = mapper.addUser(new User(4,"妮妮","123123123"));
/*        if (result>0){
            System.out.println("successfully inserted user");
        }*/
        sqlSession.commit();
        sqlSession.close();//固定代码
    }

    @Test
    public void addUser2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_id",5);
        map.put("user_name","hello");
        map.put("user_pwd",111111);

        mapper.addUser2(map);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int result = mapper.updateUser(new User(4,"妮妮","000000"));
        sqlSession.commit();
        sqlSession.close();//固定代码
    }

    @Test
    public void deleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int result = mapper.deleteUser(4);
        sqlSession.commit();
        sqlSession.close();//固定代码
    }

}
