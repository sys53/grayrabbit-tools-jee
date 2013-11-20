/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.office.utils;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * 
 * @author liyd
 * @version $Id: ExcelToolsUtil.java, v 0.1 2013-1-17 上午11:43:47 liyd Exp $
 */
public final class ExcelToolsUtil {

    private ExcelToolsUtil() {
    }

    /**
     * 生成excel参数检查
     * 
     * @param sheetTitle
     *            sheet标题
     * @param headers
     *            列标题
     * @param exportFields
     *            排版属性数组
     * @param dataset
     *            导出数据集
     * @param out
     *            导出流对象
     */
    public static void checkGeneralExportParams(String sheetTitle, String[] headers,
                                                String[] exportFields,
                                                Collection<? extends Object> dataset,
                                                OutputStream out) {

        //检查常规参数
        checkNormalParmas(sheetTitle, headers, exportFields, dataset);

        if (out == null) {

            throw new JEEToolsException("参数[out]导出Excel表格的文件流对象为null");
        }

    }

    /**
     * excel添加sheet参数检查
     * 
     * @param sheetTitle
     * @param headers
     * @param exportFields
     * @param dataset
     * @param filePath
     */
    public static void checkAddSheetParams(String sheetTitle, String[] headers,
                                           String[] exportFields,
                                           Collection<? extends Object> dataset, String dateFormat,
                                           String filePath) {

        //常规参数
        checkNormalParmas(sheetTitle, headers, exportFields, dataset);

        if (StringUtils.isBlank(dateFormat)) {

            throw new JEEToolsException("参数[dateFormat]日期格式不能为空");
        }

        if (StringUtils.isBlank(filePath)) {
            throw new JEEToolsException("参数[filePath]导出Excel表格文件路径不能为空");
        }
    }

    /**
     * 检查常规参数
     * 
     * @param sheetTitle
     * @param headers
     * @param exportFields
     * @param dataset
     */
    private static void checkNormalParmas(String sheetTitle, String[] headers,
                                          String[] exportFields,
                                          Collection<? extends Object> dataset) {

        if (StringUtils.isBlank(sheetTitle)) {

            throw new JEEToolsException("参数[sheetTitle]导出Excel表格sheet标题不能为空");
        }

        if (headers == null || headers.length == 0) {

            throw new JEEToolsException("参数[headers]导出Excel表格列标题不能为空");
        }

        if (exportFields == null || exportFields.length == 0) {

            throw new JEEToolsException("参数[exportFields]数据对象属性名称数组不能为空");
        }

        if (dataset == null || dataset.size() == 0) {

            throw new JEEToolsException("参数[dataset]导出Excel表格数据不能为空");
        }

        if (headers.length != exportFields.length) {

            throw new JEEToolsException("参数[headers,exportFields]定义的Excel列标题和排版的属性不一致");
        }

        Field[] fields = dataset.iterator().next().getClass().getDeclaredFields();

        if (fields == null || fields.length == 0) {

            throw new JEEToolsException("数据对象中没有可以导出的属性");
        }

        List<String> list = new ArrayList<String>();

        for (Field field : fields) {
            list.add(field.getName());
        }

        if (!list.containsAll(Arrays.asList(exportFields))) {

            throw new JEEToolsException("参数[exportFields]定义的属性在数据对象中不存在");
        }

    }

}
