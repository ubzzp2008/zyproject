package com.fl.web.controller.qm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.qm.TCensorShipHead;
import com.fl.web.model.qm.CensorShipHead;
import com.fl.web.service.qm.ICensorShipService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.DateUtils;
import com.fl.web.utils.FileUtils;
import com.fl.web.utils.StringUtil;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：CensorShipController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-13 13:33
 */
@RestController
@RequestMapping("/qm/censorShip")
public class CensorShipController extends BaseController {
    @Autowired
    private ICensorShipService censorShipService;
    @Value("${file.exportPath}")
    private String exportPath;

    @PostMapping(value = "/queryCensorShipList")
    public void queryCensorShipList(@RequestBody CensorShipHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TCensorShipHead> list = censorShipService.queryCensorShipList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据质检单号获取详情
     * @author：justin
     * @date：2020-01-13 14:10
     */
    @GetMapping(value = "/getCensorShipDetail")
    public void getCensorShipDetail(@RequestParam(name = "censorNo") String censorNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TCensorShipHead obj = censorShipService.getCensorShipDetail(censorNo);
            json.setObj(obj);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：质检单生成excel并下载
     * @author：justin
     * @date：2020-01-14 09:13
     */
    @GetMapping(value = "printCensorShip")
    public void printCensorShip(@RequestParam(name = "censorNo") String censorNo, HttpServletResponse response) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            TCensorShipHead obj = censorShipService.getCensorShipDetail(censorNo);
            String templateName = "censorTemplate.xlsx";
            ClassPathResource cpr = new ClassPathResource("/templates/xls/" + templateName);
            in = new BufferedInputStream(cpr.getInputStream());//得到文档的路径
            //Excel导出配置 获取resources下面的模板
//            String url = ResourceUtils.getURL("classpath:").getPath();
//            String config = url + "templates/xls/censorTemplate.xlsx";
//            in = new FileInputStream(config);//得到文档的路径
            String fileName = exportPath + "质检单_" + censorNo + ".xlsx";
            //列表数据将存储到指定的excel文件路径，这个路径是在项目编译之后的target目录下
            out = new FileOutputStream(fileName);
            //这里的context是jxls框架上的context内容
            org.jxls.common.Context context = new org.jxls.common.Context();
            //将列表参数放入context中
            context.putVar("vo", obj);
            context.putVar("itemList", obj.getItemList());
            //将List<Exam>列表数据按照模板文件中的格式生成到scoreOutput.xls文件中
            JxlsHelper.getInstance().processTemplate(in, out, context);

            //下面步骤为浏览器下载部分
            //指定数据生成后的文件输入流（将上述out的路径作为文件的输入流）
            FileInputStream fileInputStream = new FileInputStream(fileName);
            //导出excel文件，设置文件名
            String filename = URLEncoder.encode("质检单_" + censorNo + ".xlsx", "UTF-8");
            //设置下载头
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            ServletOutputStream outputStream = response.getOutputStream();
            //将文件写入浏览器
            byte[] bys = new byte[fileInputStream.available()];
            fileInputStream.read(bys);
            fileInputStream.close();
            outputStream.write(bys);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
                //先关闭输入输出流，在删除文件才能成功
                File f = new File(exportPath + "质检单_" + censorNo + ".xlsx");
                logger.info(String.valueOf(f.isFile()));
                if (f.exists()) {
                    f.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description：批量导出质检单并下载（多个文件，压缩成zip后下载）
     * @author：justin
     * @date：2020-01-14 11:32
     */
    @GetMapping(value = "printCensorShipList")
    public void printCensorShipList(@RequestParam(name = "censorNoList") List<String> censorNoList, HttpServletResponse response) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            String templateName = "censorTemplate.xlsx";
            ClassPathResource cpr = new ClassPathResource("/templates/xls/" + templateName);
            //Excel导出配置 获取resources下面的模板
//            String url = ResourceUtils.getURL("classpath:").getPath();
//            String config = url + "templates/xls/censorTemplate.xlsx";
            String fileName = "质检单";
            List<File> files = new ArrayList<>();
            for (String censorNo : censorNoList) {
                TCensorShipHead obj = censorShipService.getCensorShipDetail(censorNo);
                in = new BufferedInputStream(cpr.getInputStream());//得到文档的路径
                //列表数据将存储到指定的excel文件路径，这个路径是在项目编译之后的target目录下
                out = new FileOutputStream(exportPath + fileName + "_" + censorNo + ".xlsx");
                //这里的context是jxls框架上的context内容
                org.jxls.common.Context context = new org.jxls.common.Context();
                //将列表参数放入context中
                context.putVar("vo", obj);
                context.putVar("itemList", obj.getItemList());
                //将List<Exam>列表数据按照模板文件中的格式生成到scoreOutput.xls文件中
                JxlsHelper.getInstance().processTemplate(in, out, context);
                files.add(new File(exportPath + fileName + "_" + censorNo + ".xlsx"));
            }
            FileUtils.downLoadFiles(files, exportPath + fileName + DateUtils.formatDate("yyyyMMdd"), response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
