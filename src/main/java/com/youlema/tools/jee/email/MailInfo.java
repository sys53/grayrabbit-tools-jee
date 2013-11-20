/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.email;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 邮件信息
 * 
 * @author wy
 * @version $Id: MailInfo.java, v 0.1 2013-1-22 下午9:19:17 wy Exp $
 */
public class MailInfo implements Serializable {

    /** serialVersionUID */
    private static final long           serialVersionUID = -2078809816940580024L;

    /** 接收邮件  */
    private String                      email;

    /** 主题  */
    private String                      subject;

    /** 内容  */
    private String                      content;

    /** 附件  */
    private LinkedHashMap<String, File> fileMap;

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
     * Getter method for property <tt>subject</tt>.
     * 
     * @return property value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter method for property <tt>subject</tt>.
     * 
     * @param subject value to be assigned to property subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter method for property <tt>content</tt>.
     * 
     * @return property value of content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     * 
     * @param content value to be assigned to property content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter method for property <tt>fileMap</tt>.
     * 
     * @return property value of fileMap
     */
    public LinkedHashMap<String, File> getFileMap() {
        return fileMap;
    }

    /**
     * Setter method for property <tt>fileMap</tt>.
     * 
     * @param fileMap value to be assigned to property fileMap
     */
    public void setFileMap(LinkedHashMap<String, File> fileMap) {
        this.fileMap = fileMap;
    }

}
