package com.fl.web.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @version V0.1
 * @项目名称：springBootDemo
 * @类名称：EasyPoiUtil
 * @类描述：
 * @创建人：justin
 * @创建时间：2018-05-10 10:51
 */
public abstract class EasyPoiUtil {
    public static final Logger logger = LoggerFactory.getLogger(EasyPoiUtil.class);


    /**
     * @param list      空对象集合
     * @param title     文件标题 可空
     * @param sheetName sheet名字 可空
     * @param pojoClass 实体对象
     * @param fileName  文件名字 不能为空
     * @desc：下载xls模板
     * @author：justin
     * @date：2018-05-10 13:59
     */
    public static void templateXls(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * @param list      空对象集合
     * @param title     文件标题 可空
     * @param sheetName sheet名字 可空
     * @param pojoClass 实体对象
     * @param fileName  文件名字 不能为空
     * @desc：下载xlsx模板
     * @author：justin
     * @date：2018-05-10 13:59
     */
    public static void templateXlsx(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
//        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams(title, sheetName, ExcelType.XSSF), pojoClass, list);
        if (workbook != null) {
            fileName = fileName + "_" + DateUtils.formatDate() + ".xlsx";
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * @param list      对象集合 不能为空
     * @param title     文件标题 可空
     * @param sheetName sheet名字 可空
     * @param pojoClass 实体对象
     * @param fileName  文件名字 不能为空
     * @desc：导出xlsx文件
     * @author：justin
     * @date：2018-05-10 11:58
     */
    public static void exportExcelXlsx(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
    }

    /**
     * @param list      对象集合 不能为空
     * @param title     文件标题 可空
     * @param sheetName sheet名字 可空
     * @param pojoClass 实体对象
     * @param fileName  文件名字 不能为空
     * @desc：默认导出为xls的格式
     * @author：justin
     * @date：2018-05-10 11:57
     */
    public static void exportExcelXls(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
//        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            fileName = fileName + "_" + DateUtils.formatDate() + ".xlsx";
            downLoadExcel(fileName, response, workbook);
        }


    }

    /**
     * @desc：文件下载
     * @author：justin
     * @date：2018-05-10 14:08
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Access-Control-Expose-Headers", "filename");//自定义head的属性返回前台
            response.setHeader("filename", URLEncoder.encode(fileName, "utf-8"));
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            logger.error("模板不能为空", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            logger.error("excel文件不能为空", e.getMessage());
            throw new Exception(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception(e);
        }
        return list;
    }

    public static <T> ExcelImportResult<T> importExcelMore(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        //加入验证
        params.setNeedVerfiy(true);
        ExcelImportResult<T> list = null;
        try {
            list = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            logger.error("excel文件不能为空", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return list;
    }


}
