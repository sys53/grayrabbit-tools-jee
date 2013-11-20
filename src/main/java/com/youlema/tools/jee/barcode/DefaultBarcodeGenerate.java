package com.youlema.tools.jee.barcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.avalon.framework.configuration.Configuration;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.eps.EPSCanvasProvider;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youlema.tools.jee.exceptions.Barcode4jException;

/**
 * 默认的条形码生成
 *
 * @author wangy
 * @version $Id: DefaultBarcodeGenerate.java, v 0.1 13-6-27 下午1:46 wangy Exp $
 */
public class DefaultBarcodeGenerate implements BarcodeGenerate {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultBarcodeGenerate.class);

    /**
     * 生成条形码
     *
     * @param message
     * @param configuration
     * @param mimeTypes     {@link org.krysalis.barcode4j.tools.MimeTypes}
     * @return
     */
    @Override
    public byte[] generateBarCode(String message, Configuration configuration, String mimeTypes) {

        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream(4096);
            //图片反转 0 90 180 270
            int orientation = 0;

            BarcodeUtil util = BarcodeUtil.getInstance();
            BarcodeGenerator gen = util.createBarcodeGenerator(configuration);

            if (mimeTypes.equals(MimeTypes.MIME_SVG)) {
                //Create Barcode and render it to SVG
                SVGCanvasProvider svg = new SVGCanvasProvider(false, orientation);
                gen.generateBarcode(svg, message);
                org.w3c.dom.DocumentFragment frag = svg.getDOMFragment();

                //Serialize SVG barcode
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer trans = factory.newTransformer();
                Source src = new javax.xml.transform.dom.DOMSource(frag);
                Result res = new javax.xml.transform.stream.StreamResult(bout);
                trans.transform(src, res);
            } else if (mimeTypes.equals(MimeTypes.MIME_EPS)) {
                EPSCanvasProvider eps = new EPSCanvasProvider(bout, orientation);
                gen.generateBarcode(eps, message);
                eps.finish();
            } else {
                int dpi = 300; //dpi
                BitmapCanvasProvider bitmap = new BitmapCanvasProvider(bout, mimeTypes, dpi,
                    BufferedImage.TYPE_BYTE_GRAY, true, orientation);
                gen.generateBarcode(bitmap, message);
                bitmap.finish();
            }
            return bout.toByteArray();
        } catch (Exception e) {
            LOG.error("创建条形码出错", e);
            throw new Barcode4jException("创建条形码出错");
        } finally {
            try {
                if (bout != null) {
                    bout.flush();
                    bout.close();
                }
            } catch (IOException e) {
                LOG.error("关闭ByteArrayOutputStream时异常", e);
            }
        }
    }

    /**
     * 生成jpg格式的128的条形码
     *
     * @param message
     * @return
     */
    @Override
    public byte[] generateJpgBarCode128(String message) {
        return generateBarCode(message, Barcode4jConfiguration.buildCode128(), MimeTypes.MIME_JPEG);
    }
}
