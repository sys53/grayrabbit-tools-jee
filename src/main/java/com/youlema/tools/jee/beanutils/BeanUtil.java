/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.beanutils;

import com.youlema.tools.jee.exceptions.JEEToolsException;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * bean操作辅助类
 * 
 * @author liyd
 * @version $Id: BeanUtil.java, v 0.1 2012-10-9 下午8:23:03 liyd Exp $
 */
public final class BeanUtil {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

    private BeanUtil() {
    }

    /**
     * bean属性值拷贝
     * 
     * @param dest 目标对象
     * @param orig 源对象
     */
    public static void copyProperties(Object dest, Object orig) {

        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            LOG.error("属性转换失败", e);
            throw new JEEToolsException(e);
        }

    }

    /**
     * 获取类的属性键值map key=属性名 value=属性值
     * 如果传入的对象为null或对象没有属性返回一个空的map，不会返回null
     * 
     * @param obj bean对象
     * @return 转换后的map对象
     */
    private static Map<String, Object> getPropertiesValueMap(Map<String, Object> map, Object obj,
                                                             Class clazz) {
        try {
            if (Object.class.equals(clazz.getSuperclass())) {
                return map;
            } else {
                map.putAll(getPropertiesValueMap(map, obj, obj.getClass().getSuperclass()));
            }
            //获取类的所有属性
            Field[] fields = clazz.getDeclaredFields();

            if (fields == null || fields.length == 0) {
                return map;
            }

            for (Field field : fields) {

                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            LOG.error("获取类的属性键值对失败", e);
            throw new JEEToolsException(e);
        }

        return map;
    }

    /**
     * 获取类的属性键值map key=属性名 value=属性值
     * 如果传入的对象为null或对象没有属性返回一个空的map，不会返回null
     * 如果父类不为Object则一直获取父类所有属性值
     *
     * @param obj bean对象
     * @return 转换后的map对象
     */
    public static Map<String, Object> getPropertiesValueMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        return getPropertiesValueMap(map, obj, obj.getClass());
    }

}
