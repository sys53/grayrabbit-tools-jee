/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.velocity;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author wy
 * @version $Id: VelocityToolTest.java, v 0.1 2013-1-23 下午2:25:28 wy Exp $
 */
public class VelocityToolTest {
    
    @Test
    public void create(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "大白兔");
        StringBuffer html = VelocityTool.create("${name}你好,今天时间${StringUtils.isNotBlank($name)}", params);
        System.out.println(html.toString());
    }
    
    @Test
    public void createVm(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "大白兔");
        StringBuffer html = VelocityTool.createUTF8("test.vm",params);
        System.out.println(html.toString());
    }

}
