package com.zsy.demo.Domian;

import java.io.Serializable;

public class MailPo implements Serializable {
    private String mail;
    private String code;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
