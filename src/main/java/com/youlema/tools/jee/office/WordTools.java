/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.office;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.components.StringTools;
import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * 
 * @author liyd
 * @version $Id: WordGeneralTools.java, v 0.1 2013-1-17 上午9:48:20 liyd Exp $
 */
public class WordTools {

    /** word HTML格式文档模板名称 */
    private static final String WORD_HTML_TEMPLATE  = "htmlWord.xml";

    /** word word格式文件模板名称 */
    private static final String WORD_EMPTY_TEMPLATE = "emptyWord.xml";

    /** word文档内容 */
    private static final String WORD_CONTENT        = "${word_content}";

    /** HTTP */
    private static final String HTTP                = "http";

    /** 读取文件编码 */
    private static final String DEFAULT_CHARSET     = "GBK";

    /**
     * 以文本方式导出word
     * 
     * @param content 内容
     * @param os 文件输出流
     */
    public void exportText(String content, OutputStream os) {
        generalExport(StringTools.htmlEncode(content), os, DEFAULT_CHARSET, WORD_EMPTY_TEMPLATE);
    }

    /**
     * 根据uri地址，生成word文件
     * 
     * @param content 内容
     * @param os 文件输出流
     */
    public void export(String content, OutputStream os) {

        generalExport(content, os, DEFAULT_CHARSET, WORD_HTML_TEMPLATE);
    }

    /**
     * 根据uri地址，生成word文件
     * 
     * @param content 内容
     * @param os 输出流
     * @param charset 读取uri时的编码
     */
    public void export(String content, OutputStream os, String uriCharset) {

        generalExport(content, os, uriCharset, WORD_HTML_TEMPLATE);
    }

    /**
     * 根据uri地址，生成word文件
     * 
     * @param content 内容
     * @param os 输出流
     * @param charset 读取uri时的编码
     */
    private void generalExport(String content, OutputStream os, String charset,
                               String wordHtmlTemplate) {

        //uri路径的网页内容
        StringBuilder sb = new StringBuilder();

        //文件reader
        BufferedReader reader = null;

        try {

            if (StringUtils.startsWithIgnoreCase(content, HTTP)) {

                URL url = new URL(content);

                reader = new BufferedReader(new InputStreamReader(url.openStream(), charset));

                //读取url网页内容
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb.append(content);
            }

            //读取word模板
            String template = this.readFile(wordHtmlTemplate);

            //在模板中渲染word内容
            template = StringUtils.replace(template, WORD_CONTENT, sb.toString());

            os.write(template.getBytes());
        } catch (Exception e) {
            throw new JEEToolsException(e);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 读取文件
     * 
     * @param fileName 文件名
     * @return 文件内容字符串
     */
    private String readFile(String fileName) {

        //最终内容
        StringBuilder sb = new StringBuilder();

        InputStream is = null;
        InputStreamReader fileReader = null;
        BufferedReader br = null;
        try {
            is = this.getClass().getResourceAsStream(fileName);

            fileReader = new InputStreamReader(is);

            br = new BufferedReader(fileReader);

            //一行
            String line = null;

            while ((line = br.readLine()) != null) {

                sb.append(line);
            }
        } catch (Exception e) {
            throw new JEEToolsException(e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(fileReader);
            IOUtils.closeQuietly(br);
        }

        return sb.toString();
    }

}
