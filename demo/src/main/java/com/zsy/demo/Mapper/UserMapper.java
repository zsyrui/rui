package com.zsy.demo.Mapper;

import com.zsy.demo.Domian.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();

    String byEmail(String email);

    String byName(String name);

    User byLogin(User user);

}