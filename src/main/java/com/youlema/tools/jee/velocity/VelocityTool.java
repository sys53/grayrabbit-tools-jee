/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.velocity;

import com.youlema.tools.jee.exceptions.JEEToolsException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * veolicty生成html引擎
 * 
 * @author wy
 * @version $Id: VelocityTool.java, v 0.1 2013-1-23 上午11:16:02 wy Exp $
 */
public class VelocityTool {

    /**日志*/
    private final static Logger   LOG      = LoggerFactory.getLogger(VelocityTool.class);

    /**默认模版文件夹*/
    private final static String   TEMPLATE = "templates";

    /**这个需要重新实例化一个*/
    private static VelocityEngine VELOCITY_ENGINE;

    /**
     * 实例化
     */
    public static VelocityEngine getInstance() {

        if (VELOCITY_ENGINE != null) {
            return VELOCITY_ENGINE;
        }
        init();
        return VELOCITY_ENGINE;
    }

    /**
     * 初始化
     */
    public synchronized static void init() {
        if (VELOCITY_ENGINE != null) {
            return;
        }
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("velocity.properties"));
            String path = properties.getProperty(Velocity.FILE_RESOURCE_LOADER_PATH);
            if (path == null) {
                properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, Thread.currentThread()
                    .getContextClassLoader().getResource("").toURI().getPath()
                                                                           + TEMPLATE + "/");
            }
            VELOCITY_ENGINE = new VelocityEngine(properties);
        } catch (Exception e) {
            LOG.error("velocity初始化失败", e);
            throw new JEEToolsException("velocity初始化失败");
        }
    }

    /**
     * 通过模版返回html代码
     * 
     * @param pathname 模版路径
     * @param encoding
     * @param params
     * @return
     */
    public static StringBuffer create(String pathname, String encoding, Map<String, Object> params) {
        VelocityEngine velocity = getInstance();
        File vmFile = new File(velocity.getProperty(Velocity.FILE_RESOURCE_LOADER_PATH) + pathname);
        if (!vmFile.exists()) {
            throw new JEEToolsException("找不到" + pathname + "的vm");
        }
        StringWriter sw = new StringWriter();
        try {
            Template template = velocity.getTemplate(pathname, encoding);
            VelocityContext context = contextInit(params);
            template.merge(context, sw);
            return sw.getBuffer();
        } catch (ResourceNotFoundException e) {
            LOG.error("转换html的velocity的文件找不到", e);
        } catch (ParseErrorException e) {
            LOG.error("转换html的velocity的格式化错误", e);
            e.printStackTrace();
        } catch (Exception e) {
            LOG.error("转换html的velocity错误", e);
        } finally {
            try {
                sw.close();
            } catch (IOException e) {
                LOG.error("velocity的StringWriter关闭异常");
            }
        }
        throw new JEEToolsException("转换html的velocity错误");
    }

    /**
     * 通过模版返回html代码
     * 
     * @param template  模版字符串
     * @param params
     * @return
     */
    public static StringBuffer create(String template, Map<String, Object> params) {

        StringWriter sw = new StringWriter();
        try {
            //初始化上下文
            VelocityContext context = contextInit(params);
            getInstance().evaluate(context, sw, "", template);
            return sw.getBuffer();
        } catch (ResourceNotFoundException e) {
            LOG.error("转换html的velocity的资源文件找不到", e);
        } catch (ParseErrorException e) {
            LOG.error("转换html的velocity的格式化错误", e);
        } catch (Exception e) {
            LOG.error("转换html的velocity错误", e);
        } finally {
            try {
                sw.close();
            } catch (IOException e) {
                LOG.error("velocity的StringWriter关闭异常");
            }
        }
        throw new JEEToolsException("转换html的velocity错误");
    }

    /**
     * 初始化数据
     * 
     * @param params
     * @return
     */
    private static VelocityContext contextInit(Map<String, Object> params) {
        VelocityContext context = new VelocityContext();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
        }
        return context;
    }

    /**
     * 通过模版返回html代码
     * 
     * @param pathname
     * @param params
     * @return
     */
    public static StringBuffer createUTF8(String pathname, Map<String, Object> params) {
        return create(pathname, "UTF-8", params);
    }

}
