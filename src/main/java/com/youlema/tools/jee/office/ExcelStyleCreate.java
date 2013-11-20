/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.office;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel样式创建方法接口
 * 
 * @author liyd
 * @version $Id: ExcelStyleCreate.java, v 0.1 2013-1-17 上午10:43:55 liyd Exp $
 */
public interface ExcelStyleCreate {

    /**
     * 按指定的标题创建一个sheet
     * 
     * @param workbook
     *            工作薄对象
     * @param sheetTitle
     *            sheet标题
     * @return
     */
    public HSSFSheet createSheet(HSSFWorkbook workbook, String sheetTitle);

    /**
     * 生成sheet列标题行
     * 
     * @param workbook
     *            工作薄对象
     * @param sheet
     *            sheet对象
     * @param headers
     *            列标题
     */
    public void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] headers);

    /**
     * 创建行
     * 
     * @param t
     *            数据对象
     * @param workbook
     *            工作薄
     * @param sheet
     *            sheet对象
     * @param rowIndex
     *            行索引
     * @param exportFields
     *            列属性排版数组
     * @param dateFormat
     *            日期格式
     * @param propertyMap
     *            存放属性信息map
     * @param patriarch
     *            画图管理器
     * @throws Exception
     */
    public void createRow(Object t, HSSFWorkbook workbook, HSSFSheet sheet, int rowIndex,
                          String[] exportFields, String dateFormat,
                          Map<String, PropertyDescriptor> propertyMap, HSSFPatriarch patriarch);

    /**
     * 创建列
     * 
     * @param workbook
     *            工作薄
     * @param sheet
     *            sheet对象
     * @param row
     *            行对象
     * @param value
     *            列值
     * @param rowIndex
     *            行索引
     * @param cellIndex
     *            列索引
     * @param dateFormat
     *            日期格式
     * @param patriarch
     *            画图管理器
     */
    public void createCell(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row, Object value,
                           int rowIndex, int cellIndex, String dateFormat, HSSFPatriarch patriarch);

    /**
     * 创建日期列
     * 
     * @param cell
     *            列对象
     * @param value
     *            列的值
     * @param dateFormat
     *            日期输出格式
     */
    public void createDateCellStyle(HSSFCell cell, Object value, String dateFormat);

    /**
     * 创建图片列
     * 
     * @param workbook
     *            工作薄
     * @param sheet
     *            sheet对象
     * @param row
     *            行对象
     * @param rowIndex
     *            行索引
     * @param cellIndex
     *            列索引
     * @param value
     *            列的值
     * @param patriarch
     *            sheet对应的画图管理器
     */
    public void createPictureCellStyle(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row,
                                       int rowIndex, int cellIndex, byte[] value,
                                       HSSFPatriarch patriarch);

    /**
     * 创建下拉列表列
     * 
     * @param sheet
     *            sheet对象
     * @param cell
     *            cell对象
     * @param firstRowIndex
     *            开始行索引
     * @param lastRowIndex
     *            结束行索引
     * @param firstCellIndex
     *            开始列索引
     * @param lastCellIndex
     *            结束列索引
     * @param cellvalue
     *            列的值
     */
    public void createSelectCellStyle(HSSFSheet sheet, HSSFCell cell, int firstRowIndex,
                                      int lastRowIndex, int firstCellIndex, int lastCellIndex,
                                      String[] cellvalue);

    /**
     * 创建默认格式列
     * 
     * @param cell
     *            列对象
     * @param value
     *            列的值
     */
    public void createDefaultCellStyle(HSSFCell cell, Object value);

}
