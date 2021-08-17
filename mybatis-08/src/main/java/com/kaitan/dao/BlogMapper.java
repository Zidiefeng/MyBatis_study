package com.kaitan.dao;

import com.kaitan.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //insert data point
    int addBook(Blog blog);

    // search blog
    List<Blog> queryBlogIF(Map map);

    // query - use choose
    List<Blog> queryBlogChoose(Map map);

    // update blog
    int updateBlog(Map map);

    //query 1,2,3 blog
    List<Blog> queryBlogForeach(Map map);
}
