import com.kaitan.dao.UserMapper;
import com.kaitan.pojo.User;
import com.kaitan.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest {
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.queryUsersById(1);
        System.out.println(user1);

        sqlSession.clearCache();

        User user2 = mapper.queryUsersById(1);
        System.out.println(user2);
        System.out.println(user1==user2);
        sqlSession.close();

    }

    @Test
    public void testCache1(){
        SqlSession sqlSession1 = MybatisUtils.getSqlSession();
        SqlSession sqlSession2 = MybatisUtils.getSqlSession();
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user1 = mapper1.queryUsersById(1);
        System.out.println(user1);

        System.out.println("-------");
        User user2 = mapper2.queryUsersById(1);
        System.out.println(user2);

        System.out.println(user1==user2);
        sqlSession1.close();
        sqlSession2.close();

    }

    @Test
    public void testCache2(){
        SqlSession sqlSession1 = MybatisUtils.getSqlSession();
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        User user1 = mapper1.queryUsersById(1);
        System.out.println(user1);
        sqlSession1.close();

        System.out.println("-------");

        SqlSession sqlSession2 = MybatisUtils.getSqlSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = mapper2.queryUsersById(1);
        System.out.println(user2);
        sqlSession2.close();

        System.out.println(user1==user2);
    }
}
