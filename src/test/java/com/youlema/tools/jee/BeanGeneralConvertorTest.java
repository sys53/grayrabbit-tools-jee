/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee;

import org.junit.Test;

import com.youlema.tools.jee.beanutils.BeanGeneralConvertor;

/**
 * 
 * @author liyd
 * @version $Id: BeanGeneralConvertorTest.java, v 0.1 2013-3-28 上午10:41:38 liyd Exp $
 */
public class BeanGeneralConvertorTest {


    @Test
    public void convertBean2Bean(){

        SourceBean bean1 = new SourceBean();
        bean1.setUserId(1L);
        bean1.setUserName("liyd");


        SourceBean bean2 = BeanGeneralConvertor.convertBean2Bean(new SourceBean(), bean1);

        System.out.println(bean1.getIsAuth());
        System.out.println(bean2.getIsAuth());


    }

}
