package com.youlema.tools.jee.exceptions;

/**
 * 条形码异常信息
 *
 * @author wangy
 * @version $Id: Barcode4jException.java, v 0.1 13-6-27 上午11:54 wangy Exp $
 */
public class Barcode4jException extends JEEToolsException {

    /** serialVersionUID */
    private static final long serialVersionUID = -810697212302129981L;

    /**
     * @param resultCode
     * @param resultMsg
     */
    public Barcode4jException(String resultCode, String resultMsg) {
        super(resultCode, resultMsg);
    }

    /**
     * @param resultMsg
     */
    public Barcode4jException(String resultMsg) {
        super("BARCODE4J_EXCEPTION", resultMsg);
    }

    /**
     * @param cause
     */
    public Barcode4jException(Throwable cause) {
        super(cause);
    }
}
