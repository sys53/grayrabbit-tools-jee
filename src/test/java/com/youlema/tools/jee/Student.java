/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee;

import java.util.Date;

/**
 * 
 * @author liyd
 * @version $Id: Student.java, v 0.1 2013-1-16 下午5:22:40 liyd Exp $
 */
public class Student {

    private Long     id;

    private String   name;

    private int      age;

    private String[] sex;

    private Date     birthday;

    private byte[]   bytes;

    public Student() {
    }

    public Student(Long id, String name, int age, String[] sex, Date birthday,byte[] bytes) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.bytes = bytes;
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>age</tt>.
     * 
     * @return property value of age
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter method for property <tt>age</tt>.
     * 
     * @param age value to be assigned to property age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public String[] getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(String[] sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>birthday</tt>.
     * 
     * @return property value of birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Setter method for property <tt>birthday</tt>.
     * 
     * @param birthday value to be assigned to property birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Getter method for property <tt>bytes</tt>.
     * 
     * @return property value of bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Setter method for property <tt>bytes</tt>.
     * 
     * @param bytes value to be assigned to property bytes
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
