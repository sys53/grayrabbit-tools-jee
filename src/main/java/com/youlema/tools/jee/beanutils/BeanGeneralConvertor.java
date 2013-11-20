/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.beanutils;

import java.util.List;
import java.util.Map;

import com.youlema.tools.jee.annotation.ConvertMapping;
import com.youlema.tools.jee.convertor.ListConvertor;
import com.youlema.tools.jee.convertor.Map2ListConvertor;
import com.youlema.tools.jee.convertor.ObjectConvertor;
import com.youlema.tools.jee.pages.PageList;

/**
 * bean对象通用转换器 依赖于apache的BeanUtils
 * 
 * @author liyd
 * @version $Id: BeanGeneralConvertor.java, v 0.1 2012-10-20 下午3:27:08 liyd Exp $
 */
public class BeanGeneralConvertor {

    /**
     * 默认无参构造方法
     */
    private BeanGeneralConvertor() {
    }

    /**
     * 转换一个list对象到另一个指定的list泛型对象
     * <br>通常在需要将List列表改变为另一个List列表时使用，如PO列表到VO列表的相互转换
     * <br>当列表中的bean对象属性名不一致时，可以使用{@link ConvertMapping}注解进行映射(映射的属性数据类型必须一致)
     * <br>此方法依赖于convertObj(T, Object)
     * 
     * @param clazz 目标对象class (list泛型对象class)
     * @param list 要转换的list对象
     * @return 转换后的泛型list对象
     */
    public static <T> PageList<T> convertBeanList2BeanList(Class<T> clazz, List<?> list) {

        return ListConvertor.convert(clazz, list);

    }

    /**
     * 将mapList转换为beanList
     * <br>当map的key和bean属性名一致时进行转换
     * <br>当map的key和bean属性名不一致时可以用{@link ConvertMapping}注解进行映射,mapKey显示指定key名称
     * 
     * @param clazz 目标对象class (list泛型对象class)
     * @param mapList 要转换的map list对象
     * @return 转换后的泛型list对象
     */
    public static <T> PageList<T> convertMapList2BeanList(Class<T> clazz,
                                                          List<Map<String, Object>> mapList) {

        return Map2ListConvertor.convert(clazz, mapList);
    }

    /**
     * 转换一个bean到另一个对象
     * <br>通常在需要将一个对象改变为另一个对象时使用，如PO到VO，VO到PO等
     * <br>当属性名不一致时，可以使用{@link ConvertMapping}注解进行映射(映射的属性数据类型必须一致)
     * <br>默认已经注册Date、BigDecimal转换器，需要其它类型转换请在使用前自行注册
     * <br>注册方法参考apache的BeanUtils属性拷贝类型转换器注册
     * 
     * @param dest 目标对象
     * @param orig 源对象
     * @return 目标对象
     */
    public static <T> T convertBean2Bean(T dest, Object orig) {

        return ObjectConvertor.convert(dest, orig, null);
    }

    /**
     * 转换一个bean到另一个对象 命名不规范，后续版本中可能会去掉，使用 <b>convertBean2Bean</b> 代替
     * <br>通常在需要将一个对象改变为另一个对象时使用，如PO到VO，VO到PO等
     * <br>当属性名不一致时，可以使用{@link ConvertMapping}注解进行映射(映射的属性数据类型必须一致)
     * <br>默认已经注册Date、BigDecimal转换器，需要其它类型转换请在使用前自行注册
     * <br>注册方法参考apache的BeanUtils属性拷贝类型转换器注册
     * 
     * @param dest 目标对象
     * @param orig 源对象
     * @return 目标对象
     */
    @Deprecated
    public static <T> T convertObj(T dest, Object orig) {

        return ObjectConvertor.convert(dest, orig, null);
    }

    /**
     * 转换一个list对象到另一个指定的list泛型对象,命名不规范，后续版本中可能会去掉，使用 <b>convertBeanList2BeanList</b> 代替
     * <br>通常在需要将List列表改变为另一个List列表时使用，如PO列表到VO列表的相互转换
     * <br>当列表中的bean对象属性名不一致时，可以使用{@link ConvertMapping}注解进行映射(映射的属性数据类型必须一致)
     * <br>此方法依赖于convertObj(T, Object)
     * 
     * @param clazz 目标对象class (list泛型对象class)
     * @param list 要转换的list对象
     * @return 转换后的泛型list对象
     */
    @Deprecated
    public static <T> PageList<T> convertList(Class<T> clazz, List<?> list) {

        return ListConvertor.convert(clazz, list);

    }

}
