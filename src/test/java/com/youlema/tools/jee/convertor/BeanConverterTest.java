package com.youlema.tools.jee.convertor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youlema.tools.jee.beanutils.BeanGeneralConvertor;
import com.youlema.tools.jee.pages.PageList;
import junit.framework.Assert;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;

import com.youlema.tools.jee.SourceBean;
import com.youlema.tools.jee.TargetBean;
import com.youlema.tools.jee.converter.BeanConverter;
import com.youlema.tools.jee.converter.DefinitionEnumConverter;
import com.youlema.tools.jee.enums.DefinitionEnumAware;

/**
 * Write class comments here
 * <p/>
 * User: liyd
 * Date: 13-5-8 下午5:37
 * version $Id: BeanConverterTest.java, v 0.1 Exp $
 */
public class BeanConverterTest {

    @Before
    public void init() {

    }

    @Test
    public void convert() throws Exception{

        SourceBean sourceBean = new SourceBean();
        //父类属性
        sourceBean.setKeyword("测试");
        sourceBean.setUserId(1111L);
        sourceBean.setUserName("liyd");
        sourceBean.setPassword("123456");
        sourceBean.setIsAuth(true);
        sourceBean.setAmount(BigDecimal.TEN);


        long beanGeneralConverterBegin = System.currentTimeMillis();

        for(int i=0;i<1000;i++){
            TargetBean targetBean = new TargetBean();
            BeanGeneralConvertor.convertBean2Bean(targetBean, sourceBean);
            Assert.assertNotNull(targetBean.getUserName());
        }

        System.out.println("BeanGeneralConvertor单个bean转换1000次 用时："+(System.currentTimeMillis() - beanGeneralConverterBegin));


        long convertBegin = System.currentTimeMillis();

        for(int i=0;i<1000;i++){
            TargetBean targetBean = new TargetBean();
            BeanConverter.setEnableAnnotation(true);
            BeanConverter.convert(targetBean, sourceBean,new String[] {"password"});
            Assert.assertNotNull(targetBean.getUserName());
        }

        System.out.println("BeanConverter单个bean转换1000次 用时："+(System.currentTimeMillis() - convertBegin));


        List<SourceBean> sourceBeanList = new ArrayList<SourceBean>();

        for (int i = 0; i < 1000; i++) {
            SourceBean bean = new SourceBean();
            bean.setKeyword("测试-" + i);
            bean.setUserId(1111L);
            bean.setUserName("liyd-" + i);
            bean.setIsAuth(true);
            bean.setAmount(BigDecimal.TEN);
            sourceBeanList.add(sourceBean);
        }


        long beanGeneralConverterBegin2 = System.currentTimeMillis();

        List<TargetBean> targetBeans = BeanGeneralConvertor.convertBeanList2BeanList(TargetBean.class, sourceBeanList);

        System.out.println("BeanGeneralConvertor对1000个元素的list转换 用时："+(System.currentTimeMillis() - beanGeneralConverterBegin2));


        long convertBegin2 = System.currentTimeMillis();

        BeanConverter.setEnableAnnotation(true);
        List<TargetBean> targetBeans1 = BeanConverter.convert(TargetBean.class, sourceBeanList);

        System.out.println("BeanConverter对1000个元素的list转换 用时："+(System.currentTimeMillis() - convertBegin2));


//        System.out.println("keyword:" + targetBean.getKeyword());
//        System.out.println("userId:" + targetBean.getUserId());
//        System.out.println("userName:" + targetBean.getUserName());
//        System.out.println("isAuth:" + targetBean.getIsAuth());
//        System.out.println("amount:" + targetBean.getAmount());
//        System.out.println("userType:" + targetBean.getUserType().getCode() + "-"
//                           + targetBean.getUserType().getDesc());
//        System.out.println("loginName:" + targetBean.getLoginName());

    }

    @Test
    public void convert2() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("KEYWORD", "测试");
        map.put("USER_ID", 1111L);
        map.put("USER_NAME", "liyd");
        map.put("IS_AUTH", true);
        map.put("AMOUNT", BigDecimal.TEN);
        map.put("USER_TYPE", "TEACHER");
        map.put("LOGIN_ID", "liyd-login");

        //开启注解方式
        BeanConverter.setEnableAnnotation(true);
        //注册枚举转换器
        BeanConverter.registerConverter(DefinitionEnumAware.class, new DefinitionEnumConverter());

        //开启骆驼命名方式转换，表示会将属性名按骆驼命名的方式转换回下划线分隔的方式
        //当属性名与map key完全一致的时候不需要，但是通常JavaBean的属性都按照骆驼命名法对数据库字段进行映射
        //通常用于JavaBean属性和数据库返回Map类型时字段key的对应，例如上面的map key
        //需要注意，这里不会对注解中的值再进行转换，所以这里loginName的注解需要改成
        //@ConvertMapping(origField = "LOGIN_ID")
        //private String     loginName;
        BeanConverter.setEnableCamelCase(true);
        TargetBean targetBean = BeanConverter.convert(new TargetBean(), map);

        System.out.println("keyword:" + targetBean.getKeyword());
        System.out.println("userId:" + targetBean.getUserId());
        System.out.println("userName:" + targetBean.getUserName());
        System.out.println("isAuth:" + targetBean.getIsAuth());
        System.out.println("amount:" + targetBean.getAmount());
//        System.out.println("userType:" + targetBean.getUserType().getCode() + "-"
//                           + targetBean.getUserType().getDesc());
//        System.out.println("loginName:" + targetBean.getLoginName());

    }

    @Test
    public void convert3() {

        List<SourceBean> sourceBeanList = new ArrayList<SourceBean>();

        for (int i = 0; i < 1000; i++) {
            SourceBean sourceBean = new SourceBean();
            sourceBean.setKeyword("测试-" + i);
            sourceBean.setUserId(1111L);
            sourceBean.setUserName("liyd-" + i);
            sourceBean.setIsAuth(true);
            sourceBean.setAmount(BigDecimal.TEN);

            sourceBeanList.add(sourceBean);
        }

        //开启注解方式
        BeanConverter.setEnableAnnotation(true);
        //注册枚举转换器
        BeanConverter.registerConverter(DefinitionEnumAware.class, new DefinitionEnumConverter());

        List<TargetBean> targetBeanList = BeanConverter.convert(TargetBean.class, sourceBeanList);

        for (TargetBean targetBean : targetBeanList) {
            System.out.println("keyword:" + targetBean.getKeyword());
            System.out.println("userId:" + targetBean.getUserId());
            System.out.println("userName:" + targetBean.getUserName());
            System.out.println("isAuth:" + targetBean.getIsAuth());
            System.out.println("amount:" + targetBean.getAmount());
        }
    }

    @Test
    public void convert4() {

        List<Map<String, Object>> sourceMapList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < 1000; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("KEYWORD", "测试");
            map.put("USER_ID", 1111L);
            map.put("USER_NAME", "liyd");
            map.put("IS_AUTH", true);
            map.put("AMOUNT", BigDecimal.TEN);
            map.put("USER_TYPE", "TEACHER");
            map.put("LOGIN_ID", "liyd-login");

            sourceMapList.add(map);
        }

        //开启注解方式
        BeanConverter.setEnableAnnotation(true);
        //注册枚举转换器
        BeanConverter.registerConverter(DefinitionEnumAware.class, new DefinitionEnumConverter());
        //开启骆驼命名方式转换，表示会将属性名按骆驼命名的方式转换回下划线分隔的方式
        //当属性名与map key完全一致的时候不需要，但是通常JavaBean的属性都按照骆驼命名法对数据库字段进行映射
        //通常用于JavaBean属性和数据库返回Map类型时字段key的对应，例如上面的map key
        //需要注意，这里不会对注解中的值再进行转换，所以这里loginName的注解需要改成
        //@ConvertMapping(origField = "LOGIN_ID")
        //private String     loginName;
        BeanConverter.setEnableCamelCase(true);
        List<TargetBean> targetBeanList = BeanConverter.convert(TargetBean.class, sourceMapList);

        for (TargetBean targetBean : targetBeanList) {
            System.out.println("keyword:" + targetBean.getKeyword());
            System.out.println("userId:" + targetBean.getUserId());
            System.out.println("userName:" + targetBean.getUserName());
            System.out.println("isAuth:" + targetBean.getIsAuth());
            System.out.println("amount:" + targetBean.getAmount());
//            System.out.println("userType:" + targetBean.getUserType().getCode() + "-"
//                               + targetBean.getUserType().getDesc());
//            System.out.println("loginName:" + targetBean.getLoginName());
        }
    }

}
