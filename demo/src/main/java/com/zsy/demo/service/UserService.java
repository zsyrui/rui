package com.zsy.demo.service;

import com.zsy.demo.Domian.User;
import com.zsy.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public int addUser(User user){
        return userMapper.insert(user);
    }
    public int deleteUser(Integer id){
        return userMapper.deleteByPrimaryKey(id);
    }
    public List<User> getAllUser(){
        return userMapper.selectAll();
    }

    public int byEmail(String email){
        if(userMapper.byEmail(email)==null){
            return 2;
        }else {
            return 1;
        }

    }

    public String byName(String name){
        String username=userMapper.byName(name);
        return username;
    }

    public User byLogin(User user){
        User users=userMapper.byLogin(user);
        return users;
    }
}
