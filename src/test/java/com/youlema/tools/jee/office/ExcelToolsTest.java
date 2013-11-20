/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.office;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.youlema.tools.jee.Student;

/**
 * 
 * @author liyd
 * @version $Id: ExcelGeneralToolsTest.java, v 0.1 2013-1-16 下午5:19:45 liyd Exp $
 */
public class ExcelToolsTest {

    @Test
    public void export1() {

        try {
            ExcelTools tools = new ExcelTools(new DefaultExcelStyleCreate());

            //数组，将导出成select控件
            String[] sex = new String[] { "男", "女" };

            //图片
            byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/liyd/work/1.jpg"));

            //列标题
            String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期", "图片" };
            //导出属性
            String[] exportFields = { "id", "name", "age", "sex", "birthday", "bytes" };
            //数据
            List<Student> dataset = new ArrayList<Student>();
            dataset.add(new Student(10000001L, "张一", 20, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张二", 20, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张三", 20, sex, new Date(), bytes));
            dataset.add(new Student(20000002L, "李四", 24, sex, new Date(), bytes));
            dataset.add(new Student(30000003L, "王五", 22, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张六", 20, sex, new Date(), bytes));
            dataset.add(new Student(20000002L, "李七", 24, sex, new Date(), bytes));
            dataset.add(new Student(30000003L, "王八", 22, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张九", 20, sex, new Date(), bytes));
            dataset.add(new Student(20000002L, "李十", 24, sex, new Date(), bytes));

            //导出的文件,如果已有将会覆盖
            OutputStream out = new FileOutputStream("/Users/liyd/work/abc.xls");

            tools.export("测试", headers, exportFields, dataset, out, "yyyy-MM-dd HH:mm:ss", 5);

            System.out.println("excel导出成功！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void addSheet1() {

        try {
            ExcelTools tools = new ExcelTools();

            //显示指定使用默认的excel样式创建类,使用默认时可以省略
            tools.setExcelStyleCreate(new DefaultExcelStyleCreate());

            //数组，将导出成select控件
            String[] sex = new String[] { "男", "女" };

            //图片
            byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/liyd/work/1.jpg"));

            //列标题
            String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
            //导出属性
            String[] exportFields = { "id", "name", "age", "sex", "birthday" };
            //数据
            List<Student> dataset = new ArrayList<Student>();
            dataset.add(new Student(10000001L, "张一", 20, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张二", 20, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张三", 20, sex, new Date(), bytes));
            dataset.add(new Student(20000002L, "李四", 24, sex, new Date(), bytes));
            dataset.add(new Student(30000003L, "王五", 22, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张六", 20, sex, new Date(), bytes));
            dataset.add(new Student(20000002L, "李七", 24, sex, new Date(), bytes));
            dataset.add(new Student(30000003L, "王八", 22, sex, new Date(), bytes));
            dataset.add(new Student(10000001L, "张九", 20, sex, new Date(), bytes));
            dataset.add(new Student(20000002L, "李十", 24, sex, new Date(), bytes));

            //导出的文件,如果已有将会覆盖
            tools.addSheet("添加的sheet", headers, exportFields, dataset, "yyyy-MM-dd HH:mm:ss",
                "/Users/liyd/work/abc.xls");

            System.out.println("excel添加sheet成功！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
