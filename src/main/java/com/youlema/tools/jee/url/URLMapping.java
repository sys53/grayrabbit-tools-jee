package com.youlema.tools.jee.url;

/**
 * Write class comments here
 * <p/>
 * User: liyd
 * Date: 13-4-12 上午10:46
 * version $Id: URLMapping.java, v 0.1 Exp $
 */
public class URLMapping {

    /** The Key. */
    private String   key;

    /** The Value. */
    private String   value;

    /** The Split key. */
    private String[] splitKey;

    /** The System key. Only action mapping*/
    private String   systemKey;

    /** The System value. Only action mapping */
    private String   systemValue;

    /** The final value. action is final path,parameter is transport value. */
    private String   finalValue;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getSplitKey() {
        return splitKey;
    }

    public void setSplitKey(String[] splitKey) {
        this.splitKey = splitKey;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }
}
