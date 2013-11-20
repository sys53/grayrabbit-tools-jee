package com.youlema.tools.jee.barcode;

import org.apache.avalon.framework.configuration.Configuration;

import java.io.OutputStream;

/**
 * 条形码生成器
 *
 * @author wangy
 * @version $Id: BarcodeGenerate.java, v 0.1 13-6-26 下午4:56 wangy Exp $
 */
public interface BarcodeGenerate {

    /**
     * 生成条形码
     * 
     * @param message
     * @param configuration
     * @param mimeTypes {@link org.krysalis.barcode4j.tools.MimeTypes}
     * @return
     */
    public byte[] generateBarCode(String message, Configuration configuration,
                                        String mimeTypes);


    /**
     * 生成jpg格式的128的条形码
     *
     * @param message
     * @return
     */
    public byte[] generateJpgBarCode128(String message);

}
