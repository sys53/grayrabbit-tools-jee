/*
 * Youlema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */

package com.youlema.tools.jee.url;

import org.junit.Test;

/**
 * Write class comments here
 * <p/>
 * User: liyd
 * Date: 13-4-2 上午11:13
 * version $Id: URLRewriteUtilTest.java, v 0.1 Exp $
 */
public class URLRewriteUtilTest {

    @Test
    public void getRewriteUrl1() {

        String origUrl = "/mc/product/tour_product_show.action?productId=4&lineId=9&tourLineId=32";

        Long begin1 = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {
            String rewriteUrl = URLRewriteUtil.getRewriteUrl(origUrl);
            System.out.println(rewriteUrl);
        }

        System.out.println("所花时间：" + (System.currentTimeMillis() - begin1));

        Long begin2 = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {

            String rewriteUrl = URLRewriteUtil.getRelativeRewriteUrl(origUrl);
            System.out.println(rewriteUrl);
        }

        System.out.println("所花时间：" + (System.currentTimeMillis() - begin2));

    }

    @Test
    public void getRewriteUrl() {

        String origUrl = "http://192.168.0.249:81/mc/theme/theme_show.action?showLineProducts.topic=3&showLineProducts.orderBy=1&showLineProducts.pageNum=1&toPage=1";

        String rewriteUrl = URLRewriteUtil.getRewriteUrl(origUrl);

        System.out.println(rewriteUrl);

    }

    @Test
    public void getOrigUrl1() {

        String rewriteUrl = "http://192.168.0.249:81/search-50-杭州-51-false-52-has-53-杭州-63-1.html";

        String origUrl = URLRewriteUtil.getOrigUrl(rewriteUrl);

        System.out.println(origUrl);
    }

    @Test
    public void getOrigUrl2() {

        String rewriteUrl = "http://www.youlema.com/1001-1000-4-1001-9-1002-32.html";

        String origUrl = URLRewriteUtil.getOrigUrl(rewriteUrl);

        System.out.println(origUrl);
    }

    @Test
    public void getRelativeRewriteUrl() {

        String origUrl = "/mc/product/tour_product_show.action?productId=4&lineId=9&tourLineId=32";

        String rewriteUrl = URLRewriteUtil.getRelativeRewriteUrl(origUrl);

        System.out.println(rewriteUrl);

    }

    @Test
    public void getRelativeOrigUrl() {

        String rewriteUrl = "/1000-1000-4-1001-12-1002-32.html";

//        String origUrl = URLRewriteUtil.getRelativeOrigUrl(rewriteUrl);

//        System.out.println(origUrl);

    }

}
