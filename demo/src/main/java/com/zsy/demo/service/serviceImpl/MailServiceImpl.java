package com.zsy.demo.service.serviceImpl;


import com.zsy.demo.Domian.MailPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class MailServiceImpl {

    @Autowired
    RedisTemplate redisTemplate;

    public JavaMailSenderImpl mailSender;

    public void delCode(String mail){
        boolean bl=redisTemplate.delete(mail);
    }

    public int verifyCode(String mail,String code){
        ValueOperations ops= redisTemplate.opsForValue();
        MailPo mailPo = (MailPo) ops.get(mail);
        if(code.equals(mailPo.getCode()) ){
            return 1;
        }
        return 0;
    }

    public int sendSimpleMail(String email) {
        ValueOperations ops= redisTemplate.opsForValue();
        MailPo mailPo =new MailPo();
        mailPo.setMail(email);
        mailPo.setCode( sendCode() );
        ops.set(email, mailPo,1800, TimeUnit.SECONDS);
        return sendMail(mailPo);

    }
    private  String sendCode(){
        String code;
        code="";
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            code+=ch;
        }
        return code;
    }
    private int sendMail(MailPo mail){
        try {
            new Thread(){
                public void run() {
                    mailSender =new JavaMailSenderImpl();
                    mailSender.setHost("smtp.exmail.qq.com");
                    mailSender.setPort(465);
                    mailSender.setUsername("zhangsiyuan@vtnmc.cn");
                    mailSender.setPassword("Zsyay101");

                    Properties properties = new Properties();
                    properties.setProperty("mail.smtp.auth", "true");//开启认证
                    properties.setProperty("mail.debug", "true");//启用调试
                    properties.setProperty("mail.smtp.timeout", "1000");//设置链接超时
                    properties.setProperty("mail.smtp.port", Integer.toString(465));//设置端口
                    properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));//设置ssl端口
                    properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    mailSender.setJavaMailProperties(properties);


                    SimpleMailMessage simpMsg= new SimpleMailMessage();
                    simpMsg.setFrom("zhangsiyuan@vtnmc.cn");
                    simpMsg.setTo(mail.getMail() );
                    simpMsg.setSubject("rui做的注册邮件");
                    simpMsg.setText("您好\n这是一个使用企业邮箱做简单的邮箱注册。您的注册码为：["+ mail.getCode() +"],注意大小写哦。");
                    mailSender.send(simpMsg);
                }
            }.start();
            return 2;
        }catch (Exception e){
            return 3;
        }
    }
}
