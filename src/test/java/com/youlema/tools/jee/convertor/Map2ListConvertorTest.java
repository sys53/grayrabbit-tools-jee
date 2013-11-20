/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.convertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youlema.tools.jee.SourceBean;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author liyd
 * @version $Id: Map2ListConvertorTest.java, v 0.1 2012-10-25 下午6:58:22 liyd Exp $
 */
public class Map2ListConvertorTest {
    
    
    private List<Map<String, Object>> mapList;

    @Before
    public void init() {
        mapList = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("userId", 111L);
        map1.put("userName", "liyd");
        map1.put("desc", "测试用户");

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("userId", 222L);
        map2.put("userName", "liyd2");
        map2.put("desc", "测试用户2");

        mapList.add(map1);
        mapList.add(map2);
    }

    @Test
    public void map2ListConvert() {
        
        List<SourceBean> list = Map2ListConvertor.convert(SourceBean.class, mapList);
        
        for(SourceBean bean : list){
            
            System.out.println(bean.getUserId());
            System.out.println(bean.getUserName());
            System.out.println("------------------");
        }

    }

}
