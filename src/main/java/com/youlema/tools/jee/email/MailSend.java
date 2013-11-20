/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.slf4j.Logger;

import com.youlema.tools.jee.email.utils.MailUtil;
import com.youlema.tools.jee.exceptions.MailException;

/**
 * 邮件发送
 * 
 * @author wy
 * @version $Id: MailSend.java, v 0.1 2013-1-22 上午10:32:10 wy Exp $
 */
public class MailSend {

    /**日志*/
    private final static Logger LOG        = org.slf4j.LoggerFactory.getLogger(MailSend.class);

    private MailConfig          mailConfig = null;

    /**
     * 无参构造，默认为无前缀的邮箱
     */
    public MailSend() {
        this(null);
    }

    /**
     * 设置指定的邮箱发送
     * 
     * @param prefix
     */
    public MailSend(String prefix) {
        mailConfig = MailUtil.getMailConfig(prefix);
        LOG.info("设置使用邮箱:{}发送", mailConfig.getEmail());
    }

    /**
     * 获取发送邮件地址
     * 
     * @return
     */
    public String getSendFromEmail() {
        return mailConfig.getEmail();
    }

    /**
     * 发送邮件 
     * 
     * @param email 接收者
     * @param subject 邮件主题
     * @param content 内容
     */
    public void sendMail(String email, String subject, String content) {
        sendMail(getMailSession(), email, subject, content, null);
    }

    /**
     * 发送邮件 
     * 
     * @param email 接收者
     * @param subject 邮件主题
     * @param content 内容
     * @param fileMap
     */
    public void sendMail(String email, String subject, String content,
                         LinkedHashMap<String, File> fileMap) {
        sendMail(getMailSession(), email, subject, content, fileMap);
    }

    /**
     * 一次发送
     *
     * @param emailList
     * @param subject
     * @param content
     * @return
     */
    public boolean sendOnceMails(List<String> emailList, String subject, String content) {

        return sendOnceMails(emailList, subject, content, null);
    }

    /**
     * 群发邮件列表
     *
     * @param emailList
     * @param subject 邮件主题
     * @param content 内容
     * @param fileMap 邮件附件
     */
    public boolean sendOnceMails(List<String> emailList, String subject, String content,
                                 LinkedHashMap<String, File> fileMap) {

        sendMail(getMailSession(), emailList, subject, content, fileMap, true);

        return true;

    }

    /**
     * 群发邮件列表
     * 
     * @param emailList
     * @param subject 邮件主题
     * @param content 内容
     */
    public List<Boolean> sendMails(List<String> emailList, String subject, String content) {
        return sendMails(emailList, subject, content, null);
    }

    /**
     * 群发邮件列表
     * 
     * @param emailList
     * @param subject 邮件主题
     * @param content 内容
     * @param fileMap 邮件附件
     */
    public List<Boolean> sendMails(List<String> emailList, String subject, String content,
                                   LinkedHashMap<String, File> fileMap) {

        Session session = getMailSession();
        //线程池处理
        ExecutorService exe = Executors.newFixedThreadPool(emailList.size() < 10 ? emailList.size()
            : 10, new MailThreadFactory(getSendFromEmail()));

        List<Callable<Boolean>> taskList = new ArrayList<Callable<Boolean>>();

        List<Boolean> list = new ArrayList<Boolean>(emailList.size());

        for (String email : emailList) {
            taskList.add(new MailSendTask(session, email, subject, content, fileMap));
        }

        try {
            List<Future<Boolean>> futureList = exe.invokeAll(taskList);
            for (Future<Boolean> f : futureList) {
                list.add(f.get());
            }
            return list;
        } catch (InterruptedException e) {
            LOG.error("发送邮件InterruptedException异常", e);
            throw new MailException("发送邮件InterruptedException异常");
        } catch (ExecutionException e) {
            LOG.error("发送邮件ExecutionException异常", e);
            throw new MailException("发送邮件ExecutionException异常");
        } finally {
            //关闭线程池
            exe.shutdown();
        }
    }

    /**
     * 群发邮件，支持不同人不同主题,不同内容
     * 
     * @param mailList
     * @return
     */
    public List<Boolean> sendMails(List<MailInfo> mailList) {

        //线程池处理
        ExecutorService exe = Executors.newFixedThreadPool(mailList.size() < 10 ? mailList.size()
            : 10, new MailThreadFactory(getSendFromEmail()));

        List<Callable<Boolean>> taskList = new ArrayList<Callable<Boolean>>();
        Session session = getMailSession();
        List<Boolean> list = new ArrayList<Boolean>(mailList.size());
        for (MailInfo m : mailList) {
            taskList.add(new MailSendTask(session, m.getEmail(), m.getSubject(), m.getContent(), m
                .getFileMap()));
        }

        try {
            List<Future<Boolean>> futureList = exe.invokeAll(taskList);
            for (Future<Boolean> f : futureList) {
                list.add(f.get());
            }
            return list;
        } catch (InterruptedException e) {
            LOG.error("发送邮件InterruptedException异常", e);
            throw new MailException("发送邮件InterruptedException异常");
        } catch (ExecutionException e) {
            LOG.error("发送邮件ExecutionException异常", e);
            throw new MailException("发送邮件ExecutionException异常");
        } finally {
            //关闭线程池
            exe.shutdown();
        }

    }

    /**
     * 发送邮件
     * 
     * @param session
     * @param email 接收者
     * @param subject 邮件主题
     * @param content 内容
     * @param fileMap 发送的文件
     */
    public void sendMail(Session session, String email, String subject, String content,
                         LinkedHashMap<String, File> fileMap) {

        List<String> emails = new ArrayList<String>();
        emails.add(email);
        sendMail(session, emails, subject, content, fileMap, true);
    }

    /**
     * 发送邮件
     * 
     * @param session
     * @param emails 接收者,多个
     * @param subject 邮件主题
     * @param content 内容
     * @param fileMap 发送的文件
     * @param onceSend 是否一起发，如果不是，将单独发送
     */
    public void sendMail(Session session, List<String> emails, String subject, String content,
                         LinkedHashMap<String, File> fileMap, boolean onceSend) {

        if (emails == null || emails.size() == 0) {
            throw new MailException("邮件接收人列表不能为空");
        }
        try {
            // 根据session创建一个邮件消息    
            Message mailMessage = new MimeMessage(session);
            // 创建邮件发送者地址    
            Address from = new InternetAddress(mailConfig.getEmail(), mailConfig.getName(), "UTF-8");
            // 设置邮件消息的发送者    
            mailMessage.setFrom(from);

            // 设置邮件消息的主题    
            mailMessage.setSubject(subject);
            // 设置邮件消息发送的时间    
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart    
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容    
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);

            //添加附件
            if (fileMap != null && !fileMap.isEmpty()) {
                for (Entry<String, File> entry : fileMap.entrySet()) {
                    MimeBodyPart fileMimeBodyPart = new MimeBodyPart();
                    //得到数据源  
                    FileDataSource fds = new FileDataSource(entry.getValue());
                    //得到附件本身并至入BodyPart  
                    fileMimeBodyPart.setDataHandler(new DataHandler(fds));
                    //得到文件名同样至入BodyPart 
                    fileMimeBodyPart.setFileName(MimeUtility.encodeText(entry.getKey()
                                                                        + getFileExt(entry
                                                                            .getValue())));
                    mainPart.addBodyPart(fileMimeBodyPart);
                }
            }
            // 将MiniMultipart对象设置为邮件内容    
            mailMessage.setContent(mainPart);

            if (emails.size() == 1) {
                // 创建邮件的接收者地址，并设置到邮件消息中    
                Address to = new InternetAddress(emails.get(0));
                //Message.RecipientType.TO属性表示接收者的类型为TO    
                mailMessage.setRecipient(Message.RecipientType.TO, to);
                Transport.send(mailMessage);
                LOG.info("发送邮件[{}]给[{}]完毕", subject, emails.get(0));
            } else if (onceSend) {
                StringBuilder emailSb = new StringBuilder();
                for (String email : emails) {
                    // 创建邮件的接收者地址，并设置到邮件消息中    
                    Address to = new InternetAddress(email);
                    //Message.RecipientType.TO属性表示接收者的类型为TO    
                    mailMessage.addRecipient(Message.RecipientType.TO, to);
                    emailSb.append(email).append(",");
                }
                Transport.send(mailMessage);
                LOG.info("发送邮件[{}]给[{}]完毕", subject, emailSb);
            } else {
                for (String email : emails) {
                    // 创建邮件的接收者地址，并设置到邮件消息中    
                    Address to = new InternetAddress(email);
                    //Message.RecipientType.TO属性表示接收者的类型为TO    
                    mailMessage.setRecipient(Message.RecipientType.TO, to);
                    Transport.send(mailMessage);
                    LOG.info("发送邮件[{}]给[{}]完毕", subject, email);
                }
            }

            // 发送邮件    
        } catch (AuthenticationFailedException e) {
            LOG.error("配置邮件服务器信息错误", e);
            throw new MailException("配置邮件服务器信息错误");
        } catch (MessagingException e) {
            LOG.error("邮件发送失败", e);
            throw new MailException("邮件发送失败");
        } catch (UnsupportedEncodingException e) {
            LOG.error("邮件标题转码失败", e);
            throw new MailException("邮件标题转码失败");
        }

    }

    /**
     * 获取mail的session
     * 
     * @return
     */
    private Session getMailSession() {

        Authenticator authenticator = null;
        //如果需要身份认证，则创建一个密码验证器 
        if (mailConfig.getAuth()) {
            authenticator = new DefaultAuthenticator(mailConfig.getUsername(),
                mailConfig.getPassword());
        }

        Properties props = new Properties();
        //mail.store.protocol：用于检索邮件的协议
        //mail.transport.protocol：用于传送邮件的协议
        //mail.host：默认的主机名，默认是本地计算机。
        //mail.user：默认的用户名。
        //mail.from：默认的返回地址。
        //mail.debug：是否输出DEBUG信息。默认为False。
        //mail.protocol.host：指定协议的主机名，没有指定就用mail.host。例如：mail.smtp.host=smtp.163.com
        //mail.protocol.user：指定协议的用户名，没有指定就用mail.user。例如：mail.smtp.user=user
        //mail.protocol.from：指定协议的返回地址，没有指定就用mail.from。例如：mail.smtp.from=user@163.com
        //mail.smtp.auth：如果是True，就会登录SMTP服务器，获得授权才能发送邮件。默认为False。

        props.setProperty("mail.transport.protocol", "stmp");
        props.put("mail.smtp.host", mailConfig.getSmtpHost());
        props.put("mail.smtp.port", mailConfig.getSmtpPort());
        props.setProperty("mail.smtp.user", mailConfig.getUsername());
        props.setProperty("mail.smtp.password", mailConfig.getPassword());
        props.setProperty("mail.smtp.auth", mailConfig.getAuth().toString());
        props.setProperty("mail.from", mailConfig.getEmail());

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(props, authenticator);
        return sendMailSession;
    }

    /**
     * 获取附件的扩展名，如 .jpg
     * 
     * @param file
     * @return
     */
    private String getFileExt(File file) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        if (index > 0) {
            return name.substring(index);
        }
        return "";
    }

    /**
     * 邮件发送任务
     * 
     * @author wy
     * @version $Id: MailSendTask.java, v 0.1 2013-1-22 下午6:03:25 wy Exp $
     */
    class MailSendTask implements Callable<Boolean> {

        /**关联session*/
        private Session                     session;

        /**接收*/
        private String                      email;

        /**邮件主题*/
        private String                      subject;

        /**内容*/
        private String                      content;

        /**关联的文件*/
        private LinkedHashMap<String, File> fileMap;

        /**
         * 构造
         * 
         * @param session
         * @param email 接收者
         * @param subject 邮件主题
         * @param content 内容
         * @param fileMap 发送的文件
         */
        public MailSendTask(Session session, String email, String subject, String content,
                            LinkedHashMap<String, File> fileMap) {

            this.session = session;
            this.email = email;
            this.subject = subject;
            this.content = content;
            this.fileMap = fileMap;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                sendMail(session, email, subject, content, fileMap);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }

    /**
     * 邮件发送服务线程池
     * 参考 {@link Executors#defaultThreadFactory()}
     */
    static class MailThreadFactory implements ThreadFactory {

        static final AtomicInteger poolNumber   = new AtomicInteger(1);
        final ThreadGroup          group;
        final AtomicInteger        threadNumber = new AtomicInteger(1);
        final String               namePrefix;

        /**
         * 
         * @param email
         */
        MailThreadFactory(String email) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "[" + email + "]邮件发送服务-" + poolNumber.getAndIncrement() + "-线程-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
