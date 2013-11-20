/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.pdf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.youlema.tools.jee.enums.PathType;
import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * pdf生成类
 * 
 * @author liyd
 * @version $Id: PdfGenerator.java, v 0.1 2012-11-17 下午3:52:21 liyd Exp $
 */
public class PdfTool {

    /** 字体集合 */
    private Set<String>   fontSet  = new HashSet<String>();

    /** 相对根路径 */
    private String        basePath = null;

    /** itext渲染器 */
    private ITextRenderer renderer = null;

    public PdfTool() {

        //重新注册，解决和xalan.jar冲突的问题
        System.setProperty("javax.xml.transform.TransformerFactory",
            "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
    }

    /**
     * 添加字体,生成的文件中如果有使用中文，用此方法添加使用的字体，否则可能会有中文不显示的问题，可以多次添加
     * 
     * @param font 字体绝对路径
     */
    public void addFont(String font) {

        fontSet.add(font);
    }

    /**
     * 指定资源文件根路径，如html中使用的图片目录所在位置
     * 
     * @param pathType 指定是本地路径还是网络路径
     * @param path 路径位置
     */
    public void setBasePath(PathType pathType, String path) {

        if (pathType == PathType.LOCAL) {
            this.basePath = "file:" + (path.startsWith("/") ? path : "/" + path);
        } else {
            this.basePath = path;
        }
    }

    /**
     * 根据html生成pdf
     * 
     * @param htmlFile html文件路径
     * @param pdfFile 生成的pdf文件路径
     */
    public void html2Pdf(String htmlFile, String pdfFile) {

        try {
            //创建itext渲染器
            renderer = new ITextRenderer();

            //设置需要生成的文档对象
            renderer.setDocument(new File(htmlFile));

            createPdf(renderer, pdfFile);

        } catch (Exception e) {
            throw new JEEToolsException(e);
        }

    }

    /**
     * 根据uri生成pdf
     * 
     * @param uri html网络路径
     * @param pdfFile 生成的pdf文件路径
     */
    public void uri2Pdf(String uri, String pdfFile) {

        try {
            //创建itext渲染器
            renderer = new ITextRenderer();

            //设置需要生成的文档对象
            renderer.setDocument(uri);

            createPdf(renderer, pdfFile);

        } catch (Exception e) {
            throw new JEEToolsException(e);
        }

    }

    /**
     * 根据uri生成pdf
     * 
     * @param uri html网络路径
     * @param pdfFile 生成的pdf文件路径
     * @param uriParams 表单参数
     */
    public void uri2Pdf(String uri, String pdfFile, Map<String, String> uriParams) {

        if (uriParams == null || uriParams.isEmpty()) {
            uri2Pdf(uri, pdfFile);
        }

        StringBuilder query = new StringBuilder();
        for (Entry<String, String> entry : uriParams.entrySet()) {
            if (StringUtils.isNotBlank(entry.getKey())) {
                query.append("&").append(entry.getKey()).append("=");
                query.append(encodeParam(entry.getValue()));
            }
        }

        String queryParam = uri.indexOf("?") != -1 ? (uri.endsWith("?") ? query.substring(1)
            : query.toString()) : "?" + query.substring(1);

        uri2Pdf(uri + queryParam, pdfFile);

    }

    /**
     * 根据string生成pdf
     * 
     * @param content pdf的内容
     * @param pdfFile 生成的pdf文件路径
     */
    public void string2pdf(String content, String pdfFile) {

        try {
            //创建itext渲染器
            renderer = new ITextRenderer();

            Document doc = Jsoup.parse(content);

            //设置需要生成的文档对象
            renderer.setDocumentFromString(doc.html());

            createPdf(renderer, pdfFile);

        } catch (Exception e) {
            throw new JEEToolsException(e);
        }
    }

    /**
     * 创建pdf文件
     * 
     * @param renderer
     * @param pdfFile
     */
    private void createPdf(ITextRenderer renderer, String pdfFile) throws Exception {

        OutputStream os = null;

        try {

            os = new FileOutputStream(pdfFile);

            //如果有指定字体，注册字体，一般解决中文显示问题
            if (CollectionUtils.isNotEmpty(fontSet)) {

                // 解决中文支持问题    
                ITextFontResolver fontResolver = renderer.getFontResolver();

                Iterator<String> iter = fontSet.iterator();

                //注册字体
                while (iter.hasNext()) {

                    fontResolver.addFont(iter.next(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                }

            }

            //如果有指定相对根路径，注册，一般解决图片的相对路径问题
            if (StringUtils.isNotBlank(basePath)) {
                renderer.getSharedContext().setBaseURL(basePath);
            }

            renderer.layout();
            renderer.createPDF(os);
            os.flush();

        } finally {
            IOUtils.closeQuietly(os);
        }

    }

    /**
     * 转换请求中特殊参数
     * 
     * @param param
     * @return
     */
    private String encodeParam(String param) {
        if (param == null) {
            return "";
        }
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return param;
        }
    }
}
