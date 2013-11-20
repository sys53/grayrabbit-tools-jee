/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.office;

import java.io.FileOutputStream;

import org.junit.Test;

/**
 * 
 * @author liyd
 * @version $Id: WordToolsTest.java, v 0.1 2013-1-21 下午4:15:08 liyd Exp $
 */
public class WordToolsTest {

    
    public void export() throws Exception {
        WordTools wordTools = new WordTools();
        wordTools.export("<font color='red'>这是一个测试文档</font>", new FileOutputStream(
            "/Users/liyd/Downloads/testword.doc"));
    }
    
    public void export2() throws Exception {
        WordTools wordTools = new WordTools();
        wordTools.export("http://www.baidu.com", new FileOutputStream(
            "/Users/liyd/Downloads/testword.doc"));
    }
    @Test
    public void exportText() throws Exception {
        WordTools wordTools = new WordTools();
        wordTools.exportText("<font color='red'>这是一个测试文档</font>", new FileOutputStream(
            "/Users/liyd/Downloads/testword.doc"));
    }

}
