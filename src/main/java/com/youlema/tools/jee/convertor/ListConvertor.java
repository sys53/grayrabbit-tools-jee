/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.convertor;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.youlema.tools.jee.annotation.ConvertMapping;
import com.youlema.tools.jee.exceptions.JEEToolsException;
import com.youlema.tools.jee.pages.PageList;

/**
 * List对象转换器
 * 
 * @author liyd
 * @version $Id: ListConvertor.java, v 0.1 2012-10-25 下午5:36:47 liyd Exp $
 */
public final class ListConvertor {

    private ListConvertor() {
    }

    /**
     * 转换一个list对象到另一个指定的list泛型对象
     * <br>通常在需要将List列表改变为另一个List列表时使用，如PO列表到VO列表的相互转换
     * <br>当列表中的bean对象属性名不一致时，可以使用{@link ConvertMapping}注解进行映射(映射的属性数据类型必须一致)
     * <br>此方法依赖于com.youlema.tools.jee.convertor.ObjectConvertor.convert(T, Object)
     * 
     * @param clazz 目标对象class (list泛型对象class)
     * @param list 要转换的list对象
     * @return 转换后的泛型list对象
     */
    public static <T> PageList<T> convert(Class<T> clazz, List<?> list) {

        //返回的list列表
        PageList<T> retList = new PageList<T>();

        //拷贝分页信息
        if (list != null && list instanceof PageList) {

            retList.setPageTools(((PageList<?>) list).getPageTools());
        }

        if (CollectionUtils.isEmpty(list)) {
            return retList;
        }

        Iterator<?> iter = list.iterator();

        while (iter.hasNext()) {

            try {

                //获取目标对象所有字段
                Field[] fields = clazz.getDeclaredFields();

                //循环调用转换单个对象时传入目标对象属性列表，提高性能
                retList.add(ObjectConvertor.convert(clazz.newInstance(), iter.next(), fields));

            } catch (Exception e) {
                throw new JEEToolsException(e);
            }

        }

        return retList;

    }
}
