package com.youlema.tools.jee;

import com.youlema.tools.jee.enums.DefinitionEnumAware;

/**
 * Write class comments here
 * <p/>
 * User: liyd
 * Date: 13-5-14 上午11:42
 * version $Id: UserEnum.java, v 0.1 Exp $
 */
public enum UserEnum implements DefinitionEnumAware {

    TEACHER("TEACHER", "教师"),

    STUDENT("STUDENT", "学生");

    private String code;

    private String desc;

    private UserEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * Get target enum by code
     *
     * @param code the code
     * @return the target enum
     */
    @Override
    public DefinitionEnumAware getTargetEnum(String code) {
        for (DefinitionEnumAware e : values()) {

            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Get all enums.Apply to custom, such as enumerating the need to sort
     *
     * @return the definition enum aware [ ]
     */
    @Override
    public DefinitionEnumAware[] getAllEnums() {
        return values();
    }

    /**
     * Get the enum code.
     *
     * @return the code
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Get the enum description.
     *
     * @return the desc
     */
    @Override
    public String getDesc() {
        return this.desc;
    }
}
