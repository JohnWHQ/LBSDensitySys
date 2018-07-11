package com.john.springbootmybatis.service;

import com.john.springbootmybatis.dao.UserDao;
import com.john.springbootmybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public int addUser(User user){
        return userDao.addUser(user);
    }

    public User findById(Integer id){
        return userDao.selectById(id);
    }

    public int updatePwdById(User user){
        return userDao.updatePassword(user);
    }

    public int deleteById(Integer id){
        return userDao.deleteById(id);
    }

    public List<User> selectByLimit(Integer id, Integer limitN){
        return  userDao.selectByLimit(id, limitN);
    }

}
