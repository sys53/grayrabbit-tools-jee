/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.office;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * 
 * @author liyd
 * @version $Id: DefaultExcelStyleCreate.java, v 0.1 2013-1-17 上午10:43:20 liyd Exp $
 */
public class DefaultExcelStyleCreate implements ExcelStyleCreate {
    /**
     * 按指定的标题创建一个sheet
     * 
     * @param workbook
     *            工作薄对象
     * @param sheetTitle
     *            sheet标题
     * @return
     */
    public HSSFSheet createSheet(HSSFWorkbook workbook, String sheetTitle) {

        // 按指定标题创建sheet
        HSSFSheet sheet = workbook.createSheet(sheetTitle);

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);

        return sheet;
    }

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
    public void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] headers) {

        // 创建表格标题行
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            HSSFFont font = workbook.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            text.applyFont(font);
            cell.setCellValue(text);
        }
    }

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
                          Map<String, PropertyDescriptor> propertyMap, HSSFPatriarch patriarch) {

        HSSFRow row = sheet.createRow(rowIndex);

        for (int i = 0; i < exportFields.length; i++) {

            // 得到对应属性信息
            PropertyDescriptor pd = propertyMap.get(exportFields[i]);

            // 得到属性的get方法
            Method getMethod = pd.getReadMethod();

            if (getMethod == null) {

                throw new JEEToolsException("属性[" + pd.getName() + "]没有相对应的get方法");
            }

            // 属性值
            Object value = null;
            try {
                value = getMethod.invoke(t, new Object[] {});
            } catch (Exception e) {
                throw new JEEToolsException("执行属性方法[" + getMethod + "]出现错误");
            }

            // 创建一列
            createCell(workbook, sheet, row, value, rowIndex, i, dateFormat, patriarch);

        }
    }

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
                           int rowIndex, int cellIndex, String dateFormat, HSSFPatriarch patriarch) {

        // 创建一个列
        HSSFCell cell = row.createCell(cellIndex);

        // 判断值的类型后进行强制类型转换
        if (value == null) {

            cell.setCellValue("");

        } else if (value instanceof Date) {

            // 设置日期
            createDateCellStyle(cell, value, dateFormat);

        } else if (value instanceof byte[]) {

            // 设置图片
            createPictureCellStyle(workbook, sheet, row, rowIndex, cellIndex, (byte[]) value,
                patriarch);

        } else if (value instanceof String[]) {

            // 设置下拉列表
            createSelectCellStyle(sheet, cell, rowIndex, rowIndex, cellIndex, cellIndex,
                (String[]) value);

        } else {

            // 默认格式列
            createDefaultCellStyle(cell, value);
        }

    }

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
    public void createDateCellStyle(HSSFCell cell, Object value, String dateFormat) {

        // 设置日期
        Date date = (Date) value;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        cell.setCellValue(sdf.format(date));
    }

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
                                       HSSFPatriarch patriarch) {

        // 有图片时，设置行高为60px;
        row.setHeightInPoints(60);
        // 设置图片所在列宽度为80px,注意这里单位的一个换算
        sheet.setColumnWidth(cellIndex, (int) (35.7 * 80));

        byte[] bsValue = (byte[]) value;

        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) cellIndex,
            rowIndex, (short) cellIndex, rowIndex);
        anchor.setAnchorType(HSSFClientAnchor.MOVE_DONT_RESIZE);
        patriarch.createPicture(anchor,
            workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));

    }

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
                                      String[] cellvalue) {

        CellRangeAddressList regions = new CellRangeAddressList(firstRowIndex, lastRowIndex,
            firstCellIndex, lastCellIndex);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(cellvalue);
        HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
        // 加入数据有效性到当前sheet对象
        sheet.addValidationData(dataValidate);

        // 设置默认显示的值
        cell.setCellValue(cellvalue[0]);

    }

    /**
     * 创建默认格式列
     * 
     * @param cell
     *            列对象
     * @param value
     *            列的值
     */
    public void createDefaultCellStyle(HSSFCell cell, Object value) {

        String textValue = value.toString();

        boolean matcher = textValue.matches(ExcelTools.REGEX_NUMBER);

        // 是数字并且长度小于12时，当作double处理，当长度大于等于12时数字会显示成科学计数法，所以导成字符串
        if (matcher && textValue.length() < 12) {

            cell.setCellValue(Double.parseDouble(textValue));

        } else {
            HSSFRichTextString richString = new HSSFRichTextString(textValue);
            cell.setCellValue(richString);
        }

    }

}
