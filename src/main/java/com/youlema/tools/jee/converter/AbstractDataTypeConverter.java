package com.youlema.tools.jee.converter;

/**
 * 数据类型转换抽象类
 * <p/>
 * User: liyd
 * Date: 13-5-9 下午1:47
 * version $Id: AbstractDataTypeConverter.java, v 0.1 Exp $
 */
public abstract class AbstractDataTypeConverter implements DataTypeConverter {

    /** The default value specified to our Constructor, if any.  */
    private Object defaultValue = null;

    /**
     * Instantiates a new Abstract data type converter.
     */
    public AbstractDataTypeConverter() {

    }

    /**
     * Instantiates a new Abstract data type converter.
     *
     * @param defaultValue the default value
     */
    public AbstractDataTypeConverter(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Convert the input object into an output object of the
     * specified type.
     *
     * @param targetClass Data type to which this value should be converted
     * @param value The input value to be converted
     * @return The converted value.
     */
    public Object convert(Class<?> targetClass, Object value) {

        return convertToType(targetClass, value);
    }

    /**
     * Convert the input object into an output object of the
     * specified type.
     * <p>
     * Typical implementations will provide a minimum of
     * <code>String --> type</code> conversion.
     *
     * @param targetClass Data type to which this value should be converted.
     * @param value The input value to be converted.
     * @return The converted value.
     */
    protected abstract Object convertToType(Class<?> targetClass, Object value);

}
