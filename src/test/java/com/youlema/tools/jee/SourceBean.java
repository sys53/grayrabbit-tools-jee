/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee;

import java.math.BigDecimal;

import com.youlema.tools.jee.pages.PagingOrder;

/**
 * 测试用java bean对象
 * @author liyd
 * @version $Id: SourceBean.java, v 0.1 2012-10-25 下午6:58:51 liyd Exp $
 */
public class SourceBean extends PagingOrder {

    private Long       userId;

    private String     userName;

    private String     password;

    private Boolean    isAuth;

    private BigDecimal amount;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>isAuth</tt>.
     * 
     * @return property value of isAuth
     */
    public Boolean getIsAuth() {
        return isAuth;
    }

    /**
     * Setter method for property <tt>isAuth</tt>.
     * 
     * @param isAuth value to be assigned to property isAuth
     */
    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
