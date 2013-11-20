/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.exceptions;

/**
 * 邮件异常
 * 
 * @author wy
 * @version $Id: MailException.java, v 0.1 2013-1-22 下午2:28:30 wy Exp $
 */
public class MailException extends JEEToolsException {

    /**  */
    private static final long serialVersionUID = -8106972116102129981L;

    /**
     * @param resultCode
     * @param resultMsg
     */
    public MailException(String resultCode, String resultMsg) {
        super(resultCode, resultMsg);
    }

    /**
     * @param resultMsg
     */
    public MailException(String resultMsg) {
        super("MAIL_EXCEPTION", resultMsg);
    }

    /**
     * @param cause
     */
    public MailException(Throwable cause) {
        super(cause);
    }

}
