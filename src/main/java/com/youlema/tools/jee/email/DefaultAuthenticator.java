/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 如果需要身份认证,默认验证器  
 * 
 * @author wy
 * @version $Id: JeeAuthenticator.java, v 0.1 2013-1-22 下午3:42:00 wy Exp $
 */
public class DefaultAuthenticator extends Authenticator {

    /**用户名*/
    private String username;

    /**密码*/
    private String password;

    /**
     * 默认构造
     * 
     * @param username
     * @param password
     */
    public DefaultAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /** 
     * @see javax.mail.Authenticator#getPasswordAuthentication()
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
