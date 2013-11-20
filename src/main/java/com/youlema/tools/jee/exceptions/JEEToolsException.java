/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.youlema.tools.jee.exceptions;


/**
 * 工具包自定义exception
 * 
 * @author liyd
 * @version $Id: JEEToolsException.java, v 0.1 2012-4-6 上午11:30:46 liyd Exp $
 */
public class JEEToolsException extends GlobalException {

    /** serialVersionUID */
    private static final long serialVersionUID = 1466391909346137480L;

    /**
     * 创建一个<code>JEEToolsException</code>对象
     */
    public JEEToolsException() {
        super();
    }

    /**
     * 创建一个<code>JEEToolsException</code>对象
     * 
     * @param resultCode   异常结果码
     */
    public JEEToolsException(String resultMsg) {
        super(resultMsg);
    }

    /**
     * 创建一个<code>JEEToolsException</code>对象
     * 
     * @param resultCode     异常结果码  
     * @param resultMsg      异常结果信息
     */
    public JEEToolsException(String resultCode, String resultMsg) {
        super(resultCode, resultMsg);
    }

    /**
     * 创建一个<code>JEEToolsException</code>
     * 
     * @param cause      异常原因
     */
    public JEEToolsException(Throwable cause) {
        super(cause);
    }
}
