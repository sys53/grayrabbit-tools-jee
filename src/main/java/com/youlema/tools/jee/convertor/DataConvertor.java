/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.convertor;

import java.math.BigDecimal;

/**
 * 数据类型转换器
 * 
 * @author liyd
 * @version $Id: DataConvertor.java, v 0.1 2012-10-26 上午11:18:42 liyd Exp $
 */
public final class DataConvertor {

    private DataConvertor() {
    }

    /**
     * 转换数据类型
     * 
     * @param value
     * @param destType
     * @param origType
     * @return
     */
    public static Object convert(Object value, Class<?> destType, Class<?> origType) {

        if (value == null) {
            return value;
        }
        //如果等，直接返回值
        if (destType == origType) {
            return value;
        }

        if (destType == BigDecimal.class) {
            return new BigDecimal(String.valueOf(value));
        } else if (destType == Integer.class) {
            return (int) Math.floor(Double.valueOf(String.valueOf(value)));
        }

        return value;
    }

}
