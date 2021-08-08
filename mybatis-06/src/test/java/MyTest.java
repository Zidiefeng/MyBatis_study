import com.kaitan.dao.TeacherMapper;
import com.kaitan.pojo.Teacher;
import com.kaitan.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class MyTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }
}
