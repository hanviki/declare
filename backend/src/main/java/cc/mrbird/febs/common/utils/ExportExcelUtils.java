package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.entity.ExportAfferentCustomExcel;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableField;

import com.wuwenze.poi.annotation.ExcelField;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExportExcelUtils {
    public static void exportCustomExcel(HttpServletResponse response, List<?> list, String customDataJson, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        boolean isTrue = false;
        for (Object item : list) {
            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {
                isTrue = true;
                Field field = objClass.getDeclaredField(export.getDataIndex());
                field.setAccessible(true);
                fieldValue = field.get(item);
                // 如果类型是Boolean 是封装类
                /**
                 if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                 fieldValue = (Boolean) field.get(item) == true ? "是" : "否";
                 }*/

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                if (field.getGenericType().toString().equals("boolean")) {
                    fieldValue = (boolean) field.get(item) == true ? "是" : "否";
                }
                // 如果类型是Date
                if (field.getGenericType().toString().equals("class java.util.Date")) {
                    fieldValue = (Date) field.get(item);
                    if (fieldValue != null) {
                        fieldValue = formatter.format(fieldValue);
                    }
                }
                if (fieldValue == null) fieldValue = "";
                row.put(export.getTitle(), fieldValue.toString());
            }
            if (isTrue) rows.add(row);
            isTrue = false;
        }

        if (sheelName.equals("") || sheelName == null) {
            sheelName = "Sheet1";
        }
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
        // 合并单元格后的标题行，使用默认标题样式
//                writer.merge(rows.size() - 1, "一班成绩单");

        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // 一次性写出内容，使用默认样式，强制输出标题
        if (list.size() == 0) {
            List<String> rowHead = new ArrayList<>();
            for (ExportAfferentCustomExcel export : exportList) {
                rowHead.add(export.getTitle());
            }
            writer.writeHeadRow(rowHead);
        } else {
            rowCount = rows.size();
            writer.write(rows, true);
        }
        //设置所有列为自动宽度，不考虑合并单元格
        writer.autoSizeColumnAll();

        //标题Row高度
        writer.setRowHeight(0, 25);

        //内容Row高度
        for (int i = 1; i <= rowCount; i++) {
            writer.setRowHeight(i, 20);
        }

        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("宋体");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);

        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    public static void exportCustomExcelCutome(HttpServletResponse response, List<?> list, String customDataJson, List<DcaBAuditdynamic> dynamicData, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        boolean isTrue = false;
        for (Object item : list) {

            String userAcoount = "";
            List<String> userList = new ArrayList<>();

            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {
                isTrue = true;

                if (export.getIsDynamic() == 0) {

                    if (export.getDataIndex().equals("xl")) {
                        fieldValue = ((DcaBUser) item).getXl();
                    } else if (export.getDataIndex().equals("birthdaystr")) {
                        fieldValue = ((DcaBUser) item).getBirthdaystr();
                    } else if (export.getDataIndex().equals("positionName")) {
                        fieldValue = ((DcaBUser) item).getPositionName();
                    } else if (export.getDataIndex().equals("gwdj")) {
                        fieldValue = ((DcaBUser) item).getGwdj();
                    } else if (export.getDataIndex().equals("zygwDate")) {
                        fieldValue = ((DcaBUser) item).getZygwDate();
                    } else {
                        Field field = objClass.getDeclaredField(export.getDataIndex());
                        field.setAccessible(true);
                        fieldValue = field.get(item);


                        // 如果类型是Boolean 是封装类
                        /**
                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                         fieldValue = (Boolean) field.get(item) == true ? "是" : "否";
                         }*/

                        // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                        // 反射找不到getter的具体名
                        if (field.getGenericType().toString().equals("boolean")) {
                            fieldValue = (boolean) field.get(item) == true ? "是" : "否";
                        }
                        // 如果类型是Date
                        if (field.getGenericType().toString().equals("class java.util.Date")) {
                            fieldValue = (Date) field.get(item);
                            if (fieldValue != null) {
                                fieldValue = formatter.format(fieldValue);
                            }
                        }
                    }
                    if (export.getDataIndex().equals("userAccount")) {
                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
                        userList.add(userAcoount);
                    }
                } else {
                    List<DcaBAuditdynamic> listUser = dynamicData.stream().filter(p -> userList.contains(p.getUserAccount()) && p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
                    if (listUser.size() > 0) {
                        fieldValue = listUser.get(0).getAuditResult();
                    } else {
                        fieldValue = null;
                    }
                }
                if (fieldValue == null) fieldValue = "";
                row.put(export.getTitle(), fieldValue.toString());
            }

            if (isTrue) rows.add(row);
            isTrue = false;
        }

        if (sheelName.equals("") || sheelName == null) {
            sheelName = "Sheet1";
        }
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
        // 合并单元格后的标题行，使用默认标题样式
//                writer.merge(rows.size() - 1, "一班成绩单");

        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // 一次性写出内容，使用默认样式，强制输出标题
        if (list.size() == 0) {
            List<String> rowHead = new ArrayList<>();
            for (ExportAfferentCustomExcel export : exportList) {
                rowHead.add(export.getTitle());
            }
            writer.writeHeadRow(rowHead);
        } else {
            rowCount = rows.size();
            writer.write(rows, true);
        }
        //设置所有列为自动宽度，不考虑合并单元格
        writer.autoSizeColumnAll();

        //标题Row高度
        writer.setRowHeight(0, 25);

        //内容Row高度
        for (int i = 1; i <= rowCount; i++) {
            writer.setRowHeight(i, 20);
        }

        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("宋体");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);

        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    public static void exportCustomExcelCutome2(HttpServletResponse response, List<?> list, String customDataJson, List<DcaBAuditdynamic> dynamicData, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        boolean isTrue = false;
        for (Object item : list) {

            String userAcoount = "";
            List<String> userList = new ArrayList<>();

            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {
                isTrue = true;

                if (export.getIsDynamic() == 0) {

                    if (export.getDataIndex().equals("xl")) {
                        fieldValue = ((DcaBUser) item).getXl();
                    } else if (export.getDataIndex().equals("birthdaystr")) {
                        fieldValue = ((DcaBUser) item).getBirthdaystr();
                    } else if (export.getDataIndex().equals("positionName")) {
                        fieldValue = ((DcaBUser) item).getPositionName();
                    } else if (export.getDataIndex().equals("gwdj")) {
                        fieldValue = ((DcaBUser) item).getGwdj();
                    } else if (export.getDataIndex().equals("zygwDate")) {
                        fieldValue = ((DcaBUser) item).getZygwDate();
                    } else {
                        Field field = objClass.getDeclaredField(export.getDataIndex());
                        field.setAccessible(true);
                        fieldValue = field.get(item);


                        // 如果类型是Boolean 是封装类
                        /**
                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                         fieldValue = (Boolean) field.get(item) == true ? "是" : "否";
                         }*/

                        // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                        // 反射找不到getter的具体名
                        if (field.getGenericType().toString().equals("boolean")) {
                            fieldValue = (boolean) field.get(item) == true ? "是" : "否";
                        }
                        // 如果类型是Date
                        if (field.getGenericType().toString().equals("class java.util.Date")) {
                            fieldValue = (Date) field.get(item);
                            if (fieldValue != null) {
                                fieldValue = formatter.format(fieldValue);
                            }
                        }
                    }
                    if (export.getDataIndex().equals("userAccount")) {
                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
                        userList.add(userAcoount);
                    }
                } else {
                    List<DcaBAuditdynamic> listUser = dynamicData.stream().filter(p -> userList.contains(p.getUserAccount()) && p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
                    if (listUser.size() > 0) {
                        fieldValue = listUser.get(0).getAuditResult();
                    } else {
                        fieldValue = null;
                    }
                }
                if (fieldValue == null) fieldValue = "不审核";
                row.put(export.getTitle(), fieldValue.toString());
            }

            if (isTrue) rows.add(row);
            isTrue = false;
        }

        if (sheelName.equals("") || sheelName == null) {
            sheelName = "Sheet1";
        }
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
        // 合并单元格后的标题行，使用默认标题样式
//                writer.merge(rows.size() - 1, "一班成绩单");

        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // 一次性写出内容，使用默认样式，强制输出标题
        if (list.size() == 0) {
            List<String> rowHead = new ArrayList<>();
            for (ExportAfferentCustomExcel export : exportList) {
                rowHead.add(export.getTitle());
            }
            writer.writeHeadRow(rowHead);
        } else {
            rowCount = rows.size();
            writer.write(rows, true);
        }
        //设置所有列为自动宽度，不考虑合并单元格
        writer.autoSizeColumnAll();

        //标题Row高度
        writer.setRowHeight(0, 25);

        //内容Row高度
        for (int i = 1; i <= rowCount; i++) {
            writer.setRowHeight(i, 20);
        }

        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("宋体");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);

        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
     * 大报表的导出
     * @param response
     * @param list
     * @param customDataJson
     * @param sheelName
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static void exportCustomExcelCutome3(HttpServletResponse response, List<?> list, String customDataJson, String tempUrl,int startRowCount) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        boolean isTrue = false;
        int index=0;
        for (Object item : list) {
            index=index+1;
            String userAcoount = "";
            List<String> userList = new ArrayList<>();

            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {
                isTrue = true;

                if (export.getIsDynamic() == 0) {

                    if (export.getDataIndex().equals("indexHao")) {//不会执行代码
                        fieldValue = String.valueOf(index);
                    }  else {
                        Field field = objClass.getDeclaredField(export.getDataIndex());
                        field.setAccessible(true);
                        fieldValue = field.get(item);


                        // 如果类型是Boolean 是封装类
                        /**
                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                         fieldValue = (Boolean) field.get(item) == true ? "是" : "否";
                         }*/

                        // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                        // 反射找不到getter的具体名
                        if (field.getGenericType().toString().equals("boolean")) {
                            fieldValue = (boolean) field.get(item) == true ? "是" : "否";
                        }
                        // 如果类型是Date
                        if (field.getGenericType().toString().equals("class java.util.Date")) {
                            fieldValue = (Date) field.get(item);
                            if (fieldValue != null) {
                                fieldValue = formatter.format(fieldValue);
                            }
                        }
                    }
                    if (export.getDataIndex().equals("userAccount")) {
                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
                        userList.add(userAcoount);
                    }
                } else {
                    List<DcaBAuditdynamic> dynamicData =((DcaBReport) item).getDcaBAuditdynamicList();
                    List<DcaBAuditdynamic> listUser = dynamicData.stream().filter(p -> p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
                    if (listUser.size() > 0) {
                        fieldValue = listUser.get(0).getAuditResult();
                    } else {
                        fieldValue = null;
                    }
                }
                if (fieldValue == null) fieldValue = "";
                row.put(export.getTitle(), fieldValue.toString().replace("#","\r\n"));
            }

            if (isTrue) rows.add(row);
            isTrue = false;
        }

        // 通过工具类创建writer
        InputStream inputStream = new FileInputStream(tempUrl);
        ExcelReader reader = ExcelUtil.getReader(inputStream,0);
        ExcelWriter writer = reader.getWriter();
        // 合并单元格后的标题行，使用默认标题样式
//                writer.merge(rows.size() - 1, "一班成绩单");
        for (int i = 0; i < startRowCount; i++) {
            writer.passCurrentRow();
        }
        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // 一次性写出内容，使用默认样式，强制输出标题
        if (list.size()> 0) {
            rowCount = rows.size();
            writer.write(rows, false);
        }
        //设置所有列为自动宽度，不考虑合并单元格
      //  writer.autoSizeColumnAll();

        //标题Row高度
       // writer.setRowHeight(0, 25);

        //内容Row高度
        for (int i = startRowCount; i <= rowCount; i++) {
            writer.setRowHeight(i, 80);
        }

        StyleSet style = writer.getStyleSet();
        /**
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("宋体");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);*/

        CellStyle cellStyle2 = style.getCellStyle();
        cellStyle2.setWrapText(true);
       // cellStyle.setWrapText(true);

        /**
        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }*/
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
}
