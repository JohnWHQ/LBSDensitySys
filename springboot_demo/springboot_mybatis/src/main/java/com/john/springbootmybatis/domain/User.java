package com.john.springbootmybatis.domain;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String otherInfo;

    public User(){

    }

    public User(Integer id, String name, String password, String otherInfo) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.otherInfo = otherInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
