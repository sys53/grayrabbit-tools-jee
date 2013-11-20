/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.youlema.tools.jee.enums.PathType;
import com.youlema.tools.jee.pdf.utils.PdfTool;

/**
 * 
 * @author liyd
 * @version $Id: PdfGeneratorTest.java, v 0.1 2012-11-17 下午4:13:45 liyd Exp $
 */
public class PdfGeneratorTest {

//    @Test
    public void genHtml2Pdf() {

        PdfTool pdfGenerator = new PdfTool();
        pdfGenerator.addFont("/Users/liyd/Desktop/web/simsun.ttc");
        pdfGenerator.setBasePath(PathType.LOCAL, "/Users/liyd/Desktop/web/");
        pdfGenerator.uri2Pdf("http://localhost:8080/tbss/login.action",
            "/Users/liyd/Desktop/web/404.pdf");
    }

        @Test
    public void uri2Pdf() {

        PdfTool pdfGenerator = new PdfTool();

        pdfGenerator.addFont("/Users/liyd/Desktop/simsun.ttf");
//        pdfGenerator.setBasePath(PathType.LOCAL, "/Users/liyd/Desktop/pdf/");
        pdfGenerator.uri2Pdf("http://www.baidu.com",
            "/Users/liyd/Desktop/pdf/404.pdf");
    }

//    @Test
    public void uri2PdfParams() {

        PdfTool pdfGenerator = new PdfTool();

        Map<String, String> map = new HashMap<String, String>();
        map.put("test", "中文");
        pdfGenerator.addFont("/Users/liyd/Desktop/web/simsun.ttc");
        pdfGenerator.setBasePath(PathType.LOCAL, "/Users/liyd/Desktop/web/");
        pdfGenerator.uri2Pdf("http://zhidao.baidu.com/question/5931369.html",
            "/Users/liyd/Desktop/web/404.pdf", map);
    }

//    @Test
    public void string2pdf() {
        PdfTool pdfGenerator = new PdfTool();

//        pdfGenerator.addFont("/Users/liyd/Desktop/web/simsun.ttc");
//        pdfGenerator.setBasePath(PathType.LOCAL, "/Users/liyd/Desktop/web/");
        pdfGenerator
            .string2pdf(
                "<body style=\"font-family: SimSun\"><table><tr><td>角彩色的胸掏腰包的胸换胸掏腰包遥</td></tr></table></body>",
                "/Users/liyd/Desktop/404.pdf");
    }

}
