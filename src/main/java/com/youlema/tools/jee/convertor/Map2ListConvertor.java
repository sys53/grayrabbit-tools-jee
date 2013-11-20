/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.convertor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.annotation.ConvertMapping;
import com.youlema.tools.jee.exceptions.JEEToolsException;
import com.youlema.tools.jee.pages.PageList;

/**
 * map对象到list对象转换器
 * 
 * @author liyd
 * @version $Id: Map2ListConvertor.java, v 0.1 2012-10-25 下午5:46:28 liyd Exp $
 */
public final class Map2ListConvertor {

    private Map2ListConvertor() {
    }

    /**
     * 将mapList转换为beanList
     * <br>当map的key和bean属性名一致时进行转换
     * <br>当map的key和bean属性名不一致时可以用{@link ConvertMapping}注解进行映射,mapKey显示指定key名称
     * 
     * @param clazz
     * @param mapList
     * @return
     */
    public static <T> PageList<T> convert(Class<T> clazz, List<Map<String, Object>> mapList) {

        //返回的list列表
        PageList<T> retList = new PageList<T>();

        if (CollectionUtils.isEmpty(mapList)) {
            return retList;
        }

        //获取目标泛型对象所有字段列表
        Field[] fields = clazz.getDeclaredFields();

        if (fields == null || fields.length == 0) {
            return retList;
        }

        //处理每个map对象
        for (Map<String, Object> map : mapList) {

            try {

                retList.add(convertProperties(map, clazz, fields));

            } catch (Exception e) {
                throw new JEEToolsException(e);
            }

        }

        return retList;

    }

    /**
     * 转换属性
     * 
     * @param map
     * @param clazz
     * @param fields
     * @return
     * @throws Exception
     */
    private static <T> T convertProperties(Map<String, Object> map, Class<T> clazz, Field[] fields)
                                                                                                   throws Exception {

        //忽略空的map
        if (map == null || map.size() == 0) {
            return null;
        }

        //初始化泛型bean对象
        T t = clazz.newInstance();

        //循环处理类中的每个属性
        for (Field field : fields) {

            //属性值
            Object value = null;

            //属性名和key名相同的情况
            if (map.containsKey(field.getName())) {

                value = map.get(field.getName());
            }

            //如果有字段映射注解，进行值的拷贝
            if (field.isAnnotationPresent(ConvertMapping.class)) {

                ConvertMapping convertMapping = field.getAnnotation(ConvertMapping.class);

                String mapKey = convertMapping.mapKey();

                if (StringUtils.isBlank(mapKey)) {
                    continue;
                }

                value = map.get(mapKey);
            }

            if (value == null) {
                continue;
            }

            value = DataConvertor.convert(value, field.getType(), value.getClass());
            field.setAccessible(true);
            field.set(t, value);

        }

        return t;
    }

}
