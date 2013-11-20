/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.pages;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 包含分页信息的List
 * 
 * @author liyd
 * @version $Id: PageList.java, v 0.1 2012-4-18 下午5:08:32 selfly Exp $
 */
public class PageList<E> extends ArrayList<E> {

    /** serialVersionUID */
    private static final long serialVersionUID = 6110720806295292900L;

    private PagingTools       pagingTools;

    /**
     * 创建一个<code>PageList</code>。
     */
    public PageList() {
        pagingTools = new PagingTools();
    }

    /**
     * 创建<code>PageList</code>，并将指定<code>Collection</code>中的内容复制到新的list中。
     *
     * @param c 要复制的<code>Collection</code>
     */
    public PageList(Collection<E> c) {
        this(c, null);
    }

    /**
     * 创建<code>PageList</code>，并将指定<code>Collection</code>中的内容复制到新的list中。
     *
     * @param c 要复制的<code>Collection</code>
     */
    public PageList(Collection<E> c, PagingTools pageTools) {
        super(c);
        this.pagingTools = (pageTools == null) ? new PagingTools() : pageTools;
    }

    /**
     * 取得分页器，可从中取得有关分页和页码的所有信息。
     *
     * @return 分页器对象
     */
    public PagingTools getPageTools() {
        return this.pagingTools;
    }

    /**
     * 设置分页器。
     *
     * @param paginator 要设置的分页器对象
     */
    public void setPageTools(PagingTools pageTools) {
        if (pageTools != null) {
            this.pagingTools = pageTools;
        }
    }

    /**
     * @deprecated use getPaginator() instead
     */
    public int getPageSize() {
        return pagingTools.getItemsPerPage();
    }

    /**
     * @deprecated use getPaginator() instead
     */
    public int getTotalItems() {
        return pagingTools.getItems();
    }

    /**
     * @deprecated use getPaginator() instead
     */
    public int getTotalPage() {
        return pagingTools.getPages();
    }

    /**
     * @deprecated use getPaginator() instead
     */
    public void setPageSize(int i) {
        pagingTools.setItemsPerPage(i);
    }

    /**
     * @deprecated use getPaginator() instead
     */
    public void setTotalItem(int i) {
        pagingTools.setItems(i);
    }

    /**
     * @deprecated use getPaginator() instead
     */
    public void setTotalPage(int i) {
        setTotalItem(i * getPageSize());
    }

}
