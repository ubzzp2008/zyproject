package com.fl.web.utils;


import com.fl.web.model.system.User;

import java.util.Date;

/**
 * 在线用户对象
 *
 * @author JueYue
 * @version 1.0
 * @date 2013-9-28
 */
public class Client implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    /**
     * 用户IP
     */
    private String ip;
    /**
     * 登录时间
     */
    private Date loginDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
