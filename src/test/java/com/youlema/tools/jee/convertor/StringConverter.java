package com.youlema.tools.jee.convertor;

import com.youlema.tools.jee.converter.AbstractDataTypeConverter;

/**
 * Write class comments here
 * <p/>
 * User: liyd
 * Date: 13-5-13 下午5:22
 * version $Id: StringConverter.java, v 0.1 Exp $
 */
public class StringConverter extends AbstractDataTypeConverter{

    /**
     * 转换到指定类型
     *
     * @param targetClass 目标类型Class
     * @param value 转换的值
     *
     * @return 最终处理结果
     */
    @Override
    protected Object convertToType(Class<?> targetClass, Object value) {
        return String.valueOf(value) + "-sss";
    }
}
