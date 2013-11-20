package com.youlema.tools.jee.barcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.krysalis.barcode4j.tools.MimeTypes;

/**
 * @author wangy
 * @version $Id: GenerateTest.java, v 0.1 13-6-27 下午2:44 wangy Exp $
 */
public class GenerateTest {

    @Test
    public void testGenerate() {

        BarcodeGenerate generate = new DefaultBarcodeGenerate();

        byte[] bytes = generate.generateBarCode("YWL1301140913121231313123",
            Barcode4jConfiguration.buildCode128(), MimeTypes.MIME_JPEG);
        try {
            File file = new File("/Users/wangy/Documents/11.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testGenerateJpg() {
        BarcodeGenerate generate = new DefaultBarcodeGenerate();

        byte[] bytes = generate.generateJpgBarCode128("YWL1301140921231313123");
        try {
            File file = new File("/Users/wangy/Documents/111.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
