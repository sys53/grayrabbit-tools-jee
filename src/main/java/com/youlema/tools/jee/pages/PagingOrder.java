/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.pages;

import java.io.Serializable;

/**
 * 分页信息查询基础类
 *  
 * @author liyd
 * @version $Id: PagingOrder.java, v 0.1 2012-4-19 下午2:11:28 liyd Exp $
 */
public class PagingOrder implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -4921829492683484223L;

    /** 每页显示条数 */
    protected int             pageSize         = 20;

    /** 当前页码 */
    protected int             pageNum          = 1;

    /** 关键字 */
    protected String          keyword;

    /**
     * Getter method for property <tt>pageSize</tt>.
     * 
     * @return property value of pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     * 
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter method for property <tt>pageNum</tt>.
     * 
     * @return property value of pageNum
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * Setter method for property <tt>pageNum</tt>.
     * 
     * @param pageNum value to be assigned to property pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Getter method for property <tt>keyword</tt>.
     * 
     * @return property value of keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Setter method for property <tt>keyword</tt>.
     * 
     * @param keyword value to be assigned to property keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
