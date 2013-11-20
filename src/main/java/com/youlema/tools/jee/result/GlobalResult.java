/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.youlema.tools.jee.components.StringTools;

/**
 * 返回结果基础类
 * 
 * @author liyd
 * @version $Id: Result.java, v 0.1 2012-5-16 下午2:19:34 liyd Exp $
 */
public class GlobalResult implements Serializable {

    /** serialVersionUID */
    private static final long     serialVersionUID = -4466451299205328999L;

    /** 处理结果标志 */
    protected boolean             success          = false;

    /** 结果码*/
    protected String              resultCode       = "UNKNOWN_EXCEPTION";

    /** 结果信息*/
    protected String              resultMsg        = "发生未知异常";

    /** 结果数据存放map */
    protected Map<String, Object> dataMap;

    /**
     * 默认构造函数
     */
    public GlobalResult() {
        super();
    }

    /**
     * 构造函数
     * 
     * @param success 是否成功标识符
     */
    public GlobalResult(boolean success) {
        super();
        this.success = success;
    }

    /**
     * 构造函数
     * 
     * @param success 是否成功标识符
     * @param resultMsg 处理结果信息
     */
    public GlobalResult(boolean success, String resultMsg) {
        super();
        this.success = success;
        this.resultMsg = resultMsg;
    }

    /**
     * 添加数据
     * 
     * @param dataKey 数据key
     * @param data 数据
     */
    public void addData(String dataKey, Object data) {

        if (this.dataMap == null) {
            this.dataMap = new HashMap<String, Object>();
        }
        this.dataMap.put(dataKey, data);
    }

    /**
     * 获取数据
     * 
     * @param dataKey 数据key
     * @return
     */
    public Object getData(String dataKey) {

        if (this.dataMap != null) {
            return this.dataMap.get(dataKey);
        }
        return null;
    }

    /**
     * 获取ascii编码的结果信息
     * 
     * @return
     */
    public String getAsciiResultMsg() {
        return StringTools.native2Ascii(resultMsg);
    }

    /**
     * 重写toString方法
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Getter method for property <tt>dataMap</tt>.
     * 
     * @return property value of dataMap
     */
    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    /**
     * Setter method for property <tt>dataMap</tt>.
     * 
     * @param dataMap value to be assigned to property dataMap
     */
    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    /**
     * Getter method for property <tt>success</tt>.
     * 
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     * 
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     * 
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     * 
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>resultMsg</tt>.
     * 
     * @return property value of resultMsg
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     * Setter method for property <tt>resultMsg</tt>.
     * 
     * @param resultMsg value to be assigned to property resultMsg
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}
