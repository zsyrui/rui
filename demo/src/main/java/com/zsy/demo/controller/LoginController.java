package com.zsy.demo.controller;

import com.zsy.demo.Domian.User;
import com.zsy.demo.Domian.UserVo;
import com.zsy.demo.service.serviceImpl.LoginServiceImpl;
import com.zsy.demo.service.serviceImpl.MailServiceImpl;
import com.zsy.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/dome")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    MailServiceImpl mailServiceImpl;

    @Autowired
    LoginServiceImpl loginService;

    @PostMapping("/register")
    public int register(@RequestBody UserVo userVo){
        if( mailServiceImpl.verifyCode( userVo.getEmail(),userVo.getCode() )==1 ){
            User user=new User();
            BeanUtils.copyProperties(userVo,user);
            String username=userService.byName(userVo.getName() );
            if(username != null && username.equals(userVo.getName() )  ){
                return 3;
            }
            if(userService.addUser(user)==1 ){
                mailServiceImpl.delCode( userVo.getEmail() );
                return 1;
            }
            return 0;
        }
        return 100;
    }
    @GetMapping("/register")
    public int code( String email){
        if(userService.byEmail(email)==2){
            return mailServiceImpl.sendSimpleMail(email);
        }
        return 1;
    }
    @PostMapping("/login")
    public int login(@RequestBody UserVo userVo){
        int i=loginService.verify(userVo);
        return i;
    }
}


