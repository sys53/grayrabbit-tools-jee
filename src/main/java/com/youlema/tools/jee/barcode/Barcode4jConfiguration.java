package com.youlema.tools.jee.barcode;

import java.io.File;
import java.io.InputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youlema.tools.jee.exceptions.Barcode4jException;

/**
 * 条形码配置信息
 *
 * @author wangy
 * @version $Id: Barcode4jConfiguration.java, v 0.1 13-6-26 下午5:55 wangy Exp $
 */
public class Barcode4jConfiguration {

    /**日志*/
    private static final Logger LOG = LoggerFactory.getLogger(Barcode4jConfiguration.class);

    /**
     * 从xml中构建配置
     * 
     * @param inputStream
     * @return
     */
    public static Configuration buildFromXml(InputStream inputStream) {
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
        Configuration cfg = null;
        try {
            cfg = builder.build(inputStream);
        } catch (Exception e) {
            LOG.error("从InputStream创建条形码配置出错", e);
            throw new Barcode4jException("从InputStream创建条形码配置出错");
        }
        return cfg;
    }

    /**
     * 从xml中构建配置
     *
     * @param file
     * @return
     */
    public static Configuration buildFromXml(File file) {
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
        Configuration cfg = null;
        try {
            cfg = builder.buildFromFile(file);
        } catch (Exception e) {
            LOG.error("从File创建条形码配置出错", e);
            throw new Barcode4jException("从File创建条形码配置出错");
        }
        return cfg;
    }

    /**
     * 创建code39的配置
     *
     * @return
     */
    public static Configuration buildCode39() {
        InputStream inputStream = Barcode4jConfiguration.class
            .getResourceAsStream("/com/youlema/tools/jee/barcode/template/code39.xml");
        return buildFromXml(inputStream);
    }

    /**
     * 创建code128的配置
     *
     * @return
     */
    public static Configuration buildCode128() {
        InputStream inputStream = Barcode4jConfiguration.class
            .getResourceAsStream("/com/youlema/tools/jee/barcode/template/code128.xml");
        return buildFromXml(inputStream);
    }

    /**
     * 二维码
     *
     * @return
     */
    public static Configuration buildCodeDatamatrix() {
        InputStream inputStream = Barcode4jConfiguration.class
            .getResourceAsStream("/com/youlema/tools/jee/barcode/template/datamatrix.xml");
        return buildFromXml(inputStream);
    }

    /**
     * 用代码创建不同类型的条形码
     *
     * @param type
     * @return
     */
    public static Configuration build(String type) {
        DefaultConfiguration cfg = new DefaultConfiguration("barcode");
        DefaultConfiguration barcodeType = new DefaultConfiguration(type);

        /** *********属性设置************* */
        addChild(barcodeType, "bar-height", 15);
        addChild(barcodeType, "module-width", "0.19");
        addChild(barcodeType, "quiet-zone", 10);
        addChild(barcodeType, "wide-factor", "2.5");
        addChild(barcodeType, "interchar-gap-width", 1);

        DefaultConfiguration humanReadable = new DefaultConfiguration("human-readable");
        addChild(humanReadable, "placement", "bottom");
        addChild(humanReadable, "font-name", "Helvetica");
        addChild(humanReadable, "font-size", "3mm");

        barcodeType.addChild(humanReadable);
        cfg.addChild(barcodeType);

        return cfg;
    }

    /**
     * 添加子节点 
     * @param parent
     * @param attrName
     * @param attrValue
     */
    private static void addChild(DefaultConfiguration parent, String attrName, Object attrValue) {
        DefaultConfiguration attr;
        attr = new DefaultConfiguration(attrName);
        if (attrValue instanceof String) {
            attr.setValue((String) attrValue);
        } else {
            attr.setValue((Integer) attrValue);
        }
        parent.addChild(attr);
    }

}
