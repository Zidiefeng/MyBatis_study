package com.kaitan.dao;

import com.kaitan.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper {
    List<Teacher> getTeachers();
    //获取指定老师下的所有学生 和 老师的信息
    Teacher getTeacher(@Param("tid") int id);
    Teacher getTeacherAlt(@Param("tid") int id);
}

