/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.result;

/**
 * 泛型返回结果
 * 
 * @author liyd
 * @version $Id: GenericsResult.java, v 0.1 2012-5-18 下午3:14:34 liyd Exp $
 */
public class GenericsResult<T> extends GlobalResult {

    /** serialVersionUID */
    private static final long serialVersionUID = -3870742413167430296L;

    /** 返回数据 */
    protected T               resultData;

    /**
     * 默认构造函数
     */
    public GenericsResult() {
        super();
    }

    /**
     * 构造函数
     * 
     * @param success 是否成功标识符
     */
    public GenericsResult(boolean success) {
        super(success);
    }

    /**
     * 构造函数
     * 
     * @param success 是否成功标识符
     * @param resultMsg 处理结果信息
     */
    public GenericsResult(boolean success, String resultMsg) {
        super(success, resultMsg);
    }

    /**
     * Getter method for property <tt>resultData</tt>.
     * 
     * @return property value of resultData
     */
    public T getResultData() {
        return resultData;
    }

    /**
     * Setter method for property <tt>resultData</tt>.
     * 
     * @param resultData value to be assigned to property resultData
     */
    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

}
