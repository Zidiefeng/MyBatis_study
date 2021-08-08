package com.kaitan.pojo;


import lombok.Getter;

public class User {
    private int id;
    @Getter
    private String name;
    private String password; //db中是pwd
}
