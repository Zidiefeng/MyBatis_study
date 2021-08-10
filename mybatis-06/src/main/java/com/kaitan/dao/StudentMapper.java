package com.kaitan.dao;

import com.kaitan.pojo.Student;

import java.util.List;

public interface StudentMapper {
    //查询所有学生信息，以及对应的老师信息
    public List<Student> getStudent();
    public List<Student> getStudentAlt();

}
