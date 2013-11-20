/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.convertor;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.annotation.ConvertMapping;
import com.youlema.tools.jee.beanutils.BeanUtil;
import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * Bean对象类型转换器
 * 
 * @author liyd
 * @version $Id: ObjectConvertor.java, v 0.1 2012-10-25 下午5:24:04 liyd Exp $
 */
public class ObjectConvertor {

    private ObjectConvertor() {
    }

    /**
     * 转换一个bean到另一个对象
     * <br>通常在需要将一个对象改变为另一个对象时使用，如PO到VO，VO到PO等
     * <br>当属性名不一致时，可以使用{@link ConvertMapping}注解进行映射,origField显示指定属性名(映射的属性数据类型必须一致)
     * <br>默认已经注册Date、BigDecimal转换器，需要其它类型转换请在使用前自行注册
     * <br>注册方法参考apache的BeanUtils属性拷贝类型转换器注册
     * 
     * @param dest 目标对象
     * @param orig 源对象
     * @param fields 目标对象属性列表 为null将自动去获取，循环调用此方法时传入此参数可提高性能
     * @return 目标对象
     */
    public static <T> T convert(T dest, Object orig, Field[] fields) {

        if (orig == null) {
            return dest;
        }

        //注册类型转换器
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new BooleanConverter(null), Boolean.class);

        //拷贝相同的属性
        BeanUtil.copyProperties(dest, orig);

        //目标对象所有字段
        Field[] destFields = fields;

        if (destFields == null || destFields.length == 0) {

            //获取目标对象所有字段
            destFields = dest.getClass().getDeclaredFields();
        }

        //拷贝注解定义的映射属性值
        convertAnnotationMappingProperties(dest, orig, destFields);

        return dest;

    }

    /**
     * 拷贝注解定义的映射属性值
     * 
     * @param dest 目标对象
     * @param orig 源对象
     * @param fields 目标对象属性列表
     */
    private static void convertAnnotationMappingProperties(Object dest, Object orig, Field[] fields) {

        if (fields == null) {
            return;
        }

        try {
            for (Field field : fields) {

                //如果有字段映射注解，进行值的拷贝
                if (field.isAnnotationPresent(ConvertMapping.class)) {

                    ConvertMapping convertMapping = field.getAnnotation(ConvertMapping.class);

                    String origFieldName = convertMapping.origField();

                    if (StringUtils.isBlank(origFieldName)) {
                        continue;
                    }

                    Field f = orig.getClass().getDeclaredField(origFieldName);

                    f.setAccessible(true);
                    Object origFieldValue = f.get(orig);

                    field.setAccessible(true);
                    field.set(dest, origFieldValue);
                }
            }
        } catch (Exception e) {
            throw new JEEToolsException(e);
        }

    }

}
