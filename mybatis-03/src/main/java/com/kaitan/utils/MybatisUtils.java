package com.kaitan.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        //获取sqlSessionFactory对象
        try {
            String resource = "mybatis-config.xml"; //config file
            InputStream inputStream = Resources.getResourceAsStream(resource); //通过config file
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession(){
/*        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;*/
        return sqlSessionFactory.openSession();
    }
}
