/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.email.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.email.MailConfig;

/**
 * 邮件工具类
 * 
 * @author wy
 * @version $Id: MailUtil.java, v 0.1 2013-1-22 上午11:37:48 wy Exp $
 */
public class MailUtil {

    /**邮件*/
    public static Map<String, MailConfig> configMap = new HashMap<String, MailConfig>();

    /**
     * 获取邮件配置
     * 
     * @param key
     * @return
     */
    public static MailConfig getMailConfig(String key) {

        if (configMap.get(key) != null) {
            return configMap.get(key);
        }
        init(key);
        return configMap.get(key);

    }

    /**
     * 初始化
     * 
     * @return
     */
    private synchronized static void init(String key) {
        if (configMap.get(key) != null) {
            return;
        }
        String prefix = "";
        if (StringUtils.isNotBlank(key)) {
            prefix = key + ".";
        }
        MailConfig mailConfig = new MailConfig();
        ResourceBundle resuourceBundle = ResourceBundle.getBundle("mail");
        mailConfig.setSmtpHost(resuourceBundle.getString(prefix + "mail.smtp.host"));
        String port = resuourceBundle.getString(prefix + "mail.smtp.port");
        if (StringUtils.isNotBlank(port)) {
            mailConfig.setSmtpPort(Integer.valueOf(port));
        }
        String auth = resuourceBundle.getString(prefix + "mail.smtp.auth");
        if (StringUtils.isNotBlank(auth)) {
            mailConfig.setAuth(Boolean.valueOf(auth));
        }
        String email = resuourceBundle.getString(prefix + "mail.smtp.from");
        String username = resuourceBundle.getString(prefix + "mail.user");
        String password = resuourceBundle.getString(prefix + "mail.pwd");
        String name = resuourceBundle.getString(prefix + "mail.name");
        mailConfig.setEmail(email);
        mailConfig.setName(name);
        mailConfig.setUsername(username);
        mailConfig.setPassword(password);
        mailConfig.check();
        //对于传入为空的字符串，默认为null
        configMap.put(StringUtils.trimToNull(key), mailConfig);
    }

    /**
     * 重置
     */
    public synchronized static void reset() {
        MailUtil.configMap.clear();
    }

}
