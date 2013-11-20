/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.email;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author wy
 * @version $Id: MailSendTest.java, v 0.1 2013-1-22 下午4:49:31 wy Exp $
 */
public class MailSendTest {

    @Test
    public void sendFile() {
        MailSend mailSend = new MailSend();
        File file = new File("c:\\123.png");
        LinkedHashMap<String, File> map = new LinkedHashMap<String, File>();
        map.put("图片附件", file);
        mailSend.sendMail("245436803@qq.com", "测试邮件", "<b>标题加粗了没</b>", map);

    }

    @Test
    public void send() {
        MailSend mailSend = new MailSend();
        mailSend.sendMail("245436803@qq.com", "测试邮件", "<b>标题加粗了没</b>");
    }

    @Test
    public void sendTest() {
        MailSend mailSend = new MailSend("test");
        mailSend.sendMail("245436803@qq.com", "测试邮件", "<b>标题加粗了没</b>");
    }

    /**
     * 批量
     */
    @Test
    public void sendMailsFile() {
        MailSend mailSend = new MailSend();
        List<String> list = new ArrayList<String>();
        list.add("245436803@qq.com");
        list.add("178174339@qq.com");
        mailSend.sendMails(list, "测试邮件", "<b>标题加粗了没</b>");
        //获取发送使用的邮箱
        mailSend.getSendFromEmail();
    }

    @Test
    public void sendOnceMail() {
        MailSend mailSend = new MailSend();
        List<String> list = new ArrayList<String>();
        list.add("245436803@qq.com");
        list.add("178174339@qq.com");
        mailSend.sendOnceMails(list, "测试邮件", "<b>标题加粗了没</b>");
        //获取发送使用的邮箱
        mailSend.getSendFromEmail();
    }

}
