import com.kaitan.dao.UserMapper;
import com.kaitan.pojo.User;
import com.kaitan.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //mapper.addUser(new User(13,"Hell0","123321"));
        //mapper.updateUser(new User(2,"Kakaka","131313"));
        mapper.deleteUser(13);

        sqlSession.close();
        /*
        List<User> users = mapper.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
        */

    }
}
