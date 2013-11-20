/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.serializer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * 序列化辅助类
 * 
 * @author liyd
 * @version $Id: SerializerUtil.java, v 0.1 2012-8-16 下午4:11:08 liyd Exp $
 */
public final class SerializerUtil {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SerializerUtil.class);

    private SerializerUtil() {
    }

    /**
     * 将对象序列化成字符串
     * 
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {

        if (obj == null) {
            return null;
        }

        ByteArrayOutputStream baops = null;
        ObjectOutputStream oos = null;
        try {
            baops = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baops);
            oos.writeObject(obj);

            //产生编码问题，用base64保证完整性
            return Base64.encode(baops.toByteArray());

        } catch (IOException e) {
            LOG.error("将对象序列化成字符串失败", e);
            throw new JEEToolsException("将对象序列化成字符串失败");
        } finally {
            IOUtils.closeQuietly(baops);
            IOUtils.closeQuietly(oos);
        }
    }

    /**
     * 将字符串反序列化成对象
     * 
     * @param strObj
     * @return
     */
    public static Object stringToObject(String strObj) {

        if (StringUtils.isBlank(strObj)) {
            return null;
        }

        ObjectInputStream ois = null;

        try {
            byte[] bytes = strObj.getBytes();
            ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(
                Base64.decode(bytes))));
            Object obj = ois.readObject();
            return obj;
        } catch (Exception e) {
            LOG.error("将字符串反序列化成对象失败", e);
            throw new JEEToolsException("将字符串反序列化成对象失败");
        } finally {
            IOUtils.closeQuietly(ois);
        }

    }
}
