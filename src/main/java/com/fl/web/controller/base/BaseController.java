package com.fl.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.fl.web.utils.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：BaseController
 * @类描述：cmd 执行 java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="需要加密的内容" password=hcsci algorithm=PBEWithMD5AndDES
 * @创建人：justin
 * @创建时间：2019-10-08 11:19
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @desc：返回json格式的字符串
     * @author：justin
     * @date：2018-05-04 11:43
     */
    public String returnJson(AjaxJson json) {
        logger.info("返回的json格式：" + JSON.toJSONString(json));
        return JSON.toJSONString(json);
    }

    /**
     * 描述：返回json格式数据到前端页面
     *
     * @author justin
     * @创建时间 2017年6月14日 下午3:24:20
     */
    public void writeJson(Object object, HttpServletResponse response) {
        try {
            //忽略为空的字段
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            //不忽略为空的字段
            //String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
            response.setContentType("text/html;charset=utf-8");
            // response.setContentType("application/json");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description：验证分页参数pageNum和pageSize
     * @author：justin
     * @date：2019-10-14 10:08
     */
    public String validatePageParam(int pageNum, int pageSize) {
        String msg = "";
        if (pageNum <= 0 || pageSize <= 0) {
            msg = "参数【pageNum】或【pageSize】异常！必须是大于0的整数！";
        }
        return msg;
    }

    protected boolean checkFileSize(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        return fileSize > size;
    }

    /**
     * @description：下载文件
     * @author：justin
     * @date：2019-11-25 17:47
     */
    /*protected void downLoadExcel(String filename, Workbook workbook, boolean isSave, HttpServletResponse response) {
        try {
            FileOutputStream fos =null;
            filename = filename + "_" + DateUtils.formatDate() + ".xlsx";
            if (isSave) { //保存本地文件
                String filePath = "E:/export/";
                File savefile = new File(filePath);
                if (!savefile.exists()) {
                    logger.info("目录不存在，新建目录");
                    savefile.mkdirs();
                }
                 fos = new FileOutputStream(filePath + filename);
                workbook.write(fos);

            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Access-Control-Expose-Headers", "filename");//自定义head的属性返回前台
            response.setHeader("filename", URLEncoder.encode(filename, "utf-8"));
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }*/
}
