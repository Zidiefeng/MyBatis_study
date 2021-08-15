import com.kaitan.dao.BlogMapper;
import com.kaitan.pojo.Blog;
import com.kaitan.utils.IDutils;
import com.kaitan.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyTest {
    @Test
    public void addInitBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(IDutils.getId());
        blog.setTitle("Mybatis so easy");
        blog.setAuthor("Kaitan");
        blog.setCreateTime(new Date());
        blog.setViews(99999);
        mapper.addBook(blog);

        blog.setId(IDutils.getId());
        blog.setTitle("java so easy");
        mapper.addBook(blog);

        blog.setId(IDutils.getId());
        blog.setTitle("sql so easy");
        mapper.addBook(blog);

        sqlSession.close();
    }

    @Test
    public void queryBlogIF(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        map.put("title","java so easy");

        List<Blog> blogs = mapper.queryBlogIF(map);

        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void queryBlogChoose(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        map.put("title","java so easy");
        map.put("views","99999");

        List<Blog> blogs = mapper.queryBlogChoose(map);

        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void updateBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

//        map.put("title","JAVA so easy");
//        map.put("views","100000");
        map.put("author","kk");
        map.put("id","bc778785d99e4360b3f68951b77772ae");

        mapper.updateBlog(map);
    }

}
