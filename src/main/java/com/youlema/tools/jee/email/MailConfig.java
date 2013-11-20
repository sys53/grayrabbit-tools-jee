/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.email;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.exceptions.MailException;

/**
 * 邮件配置信息
 * 
 * @author wy
 * @version $Id: MailConfig.java, v 0.1 2013-1-22 上午11:36:56 wy Exp $
 */
public class MailConfig implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -4608376089983637041L;

    /**发送邮件*/
    private String            email;

    /**邮件名称*/
    private String            username;

    /**密码*/
    private String            password;

    /**发送的stmt*/
    private String            smtpHost;

    /**发送端口*/
    private Integer           smtpPort;

    /**SMTP服务器是否需要用户认证*/
    private Boolean           auth             = false;

    /**邮件上显示的发送人名称*/
    private String            name;

    /**
     * 检查数据
     */
    public void check() {

        if (StringUtils.isBlank(email)) {
            throw new MailException("mail.properties中email不能为空!");
        }
        if (StringUtils.isBlank(username)) {
            throw new MailException("mail.properties中username不能为空!");
        }
        if (StringUtils.isBlank(password)) {
            throw new MailException("mail.properties中password不能为空!");
        }
        if (StringUtils.isBlank(smtpHost)) {
            throw new MailException("mail.properties中smtpHost不能为空!");
        }
        if (StringUtils.isBlank(name)) {
            throw new MailException("mail.properties中name不能为空!");
        }
        if (smtpPort <= 0) {
            throw new MailException("mail.properties中smtpPort必须大于0!");
        }
    }

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>username</tt>.
     * 
     * @return property value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for property <tt>username</tt>.
     * 
     * @param username value to be assigned to property username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for property <tt>password</tt>.
     * 
     * @return property value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for property <tt>password</tt>.
     * 
     * @param password value to be assigned to property password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for property <tt>smtpPort</tt>.
     * 
     * @return property value of smtpPort
     */
    public Integer getSmtpPort() {
        return smtpPort;
    }

    /**
     * Setter method for property <tt>smtpPort</tt>.
     * 
     * @param smtpPort value to be assigned to property smtpPort
     */
    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * Getter method for property <tt>auth</tt>.
     * 
     * @return property value of auth
     */
    public Boolean getAuth() {
        return auth;
    }

    /**
     * Setter method for property <tt>auth</tt>.
     * 
     * @param auth value to be assigned to property auth
     */
    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    /**
     * Getter method for property <tt>smtpHost</tt>.
     * 
     * @return property value of smtpHost
     */
    public String getSmtpHost() {
        return smtpHost;
    }

    /**
     * Setter method for property <tt>smtpHost</tt>.
     * 
     * @param smtpHost value to be assigned to property smtpHost
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

}
