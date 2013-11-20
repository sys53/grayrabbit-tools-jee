/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.components;

import org.junit.Test;

/**
 * 
 * @author liyd
 * @version $Id: StringToolsTest.java, v 0.1 2013-1-23 上午10:34:03 liyd Exp $
 */
public class StringToolsTest {

    @Test
    public void native2Ascii() {

        String asc = StringTools.native2Ascii("供应商已提交落实，该供应商无需审核，可直接进行落实完成操作！");
        System.out.println(asc);
    }

    
    
    @Test
    public void replaceIndexStr() {

        String str = "ccbbccddccff";
        
       String result = StringTools.replaceIndexStr(str, "cc", "22", 4);

       System.out.println(result);
    }
}
