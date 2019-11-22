package com.zsy.demo.service.serviceImpl;

import com.zsy.demo.Domian.User;
import com.zsy.demo.Domian.UserVo;
import com.zsy.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {
    @Autowired
    UserService userService;

    public int verify(UserVo userVo){
        User user=new User();
        BeanUtils.copyProperties(userVo,user);
        user =userService.byLogin(user);
        if(user==null){
            return 1;    // 用户名/邮箱不存在
        }
        if(user.getPassword().equals( userVo.getPassword() )){
            return 100;// 登录成功
        }
        return 0;//密码不正确
    }
}
