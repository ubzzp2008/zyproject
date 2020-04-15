package com.fl.web.controller.mdm;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TAttachInfo;
import com.fl.web.service.mdm.IAttachInfoService;
import com.fl.web.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：AttachInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-05 13:09
 */
@RestController
@RequestMapping("/mdm/attachInfo")
public class AttachInfoController extends BaseController {
    @Autowired
    private IAttachInfoService attachInfoService;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.virtualPath}")
    private String virtualPath;

    /**
     * @description：维修单待处理文件上传，一次上传一个文件
     * @author：justin
     * @date：2019-12-05 16:11
     */
    @PostMapping(value = "/uploadOneFile")
    @ResponseBody
    public void uploadOneFile(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String tableId = request.getParameter("tableId");
            String orderNo = request.getParameter("orderNo");
            String tableName = request.getParameter("tableName");
            //判断文件是否为空
            if (file == null || file.isEmpty()) {
                json.setMsg("上传失败！文件为空！");
                json.setSuccess(false);
            }
            if (StringUtil.isEmpty(tableId) || StringUtil.isEmpty(orderNo) || StringUtil.isEmpty(tableName)) {
                json.setMsg("上传失败！参数【tableId、orderNo、tableName】存在空值！");
                json.setSuccess(false);
            } else {
                //验证文件大小，不能大于10M
                if (checkFileSize(file.getSize(), 10, "M")) {
                    json.setMsg("文件过大，不能超过10M！");
                    json.setSuccess(false);
                } else {
                    // 获取文件名
                    String fileName = file.getOriginalFilename();
                    logger.info("上传的文件名为: " + fileName);
                    //获取后缀名
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                    String currDate = DateUtils.formatDate("yyyyMMdd");
                    //保存文件的物理绝对路径
                    String localPath = uploadFolder + currDate;
                    //创建文件路径
                    File dir = new File(localPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String newName = DateUtils.formatDate("yyyyMMddHHmmss") + "_" + fileName;
                    //上传文件
                    file.transferTo(new File(localPath + File.separator + newName)); //保存文件
                    String vPath = virtualPath + currDate + File.separator + newName;//虚拟路径
                    //操作数据库,保存数据
                    TAttachInfo info = new TAttachInfo();
                    info.setId(UUIDUtil.get32UUID());
                    info.setTableId(tableId);
                    info.setTableName(tableName);
                    info.setOrderNo(orderNo);
                    info.setOldName(file.getOriginalFilename());
                    info.setFileName(newName);
                    info.setFileType(suffix);
                    info.setFileSize((int) file.getSize());
                    info.setFilePath(vPath);
                    info.setFlag("1");
                    info.setCreateBy(SessionUser.getUserName());
                    info.setCreateDate(new Date());
                    attachInfoService.saveAttachInfo(info);
                    json.setMsg("文件【" + fileName + "】上传成功！");
                    json.setSuccess(true);
                    json.setObj(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg("系统异常！" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：一次上传多个文件
     * @author：justin
     * @date：2019-12-05 16:10
     */
    /*@PostMapping(value = "/uploadMultiFile")
    @ResponseBody
    public void uploadMultiFile(MultipartHttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
//            List<MultipartFile> files = request.getFiles("files");
            List<MultipartFile> files = request.getFiles("file");
            String id = request.getParameter("id");
            String orderNo = request.getParameter("orderNo");
            String tableName = request.getParameter("tableName");

            if (CollectionUtils.isNotEmpty(files)) {
                List<TAttachInfo> attachInfoList = new ArrayList<>();
                String filePath = "F:/fileUpload/";
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    logger.info("上传文件【" + file.getOriginalFilename() + "】");
                    logger.info("文件大小：" + file.getSize());
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                    logger.info("文件类型：" + suffix);
                    String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + file.getOriginalFilename();
                    File dest = new File(filePath + fileName);
                    file.transferTo(dest);
                    //构建数据对象
                    TAttachInfo info = new TAttachInfo();
                    info.setId(UUIDUtil.get32UUID());
                    info.setTableId(id);
                    info.setTableName(tableName);
                    info.setOrderNo(orderNo);
                    info.setOldName(file.getOriginalFilename());
                    info.setFileName(fileName);
                    info.setFileType(suffix);
                    info.setFileSize((int) file.getSize());
                    info.setFilePath(filePath + fileName);
                    info.setCreateBy(SessionUser.getUserName());
                    info.setCreateDate(new Date());
                    attachInfoList.add(info);
                }

                //操作数据库,保存数据
                attachInfoService.saveAttachInfoBatch(attachInfoList);
                json.setSuccess(true);
                json.setMsg("文件上传成功");
            } else {
                json.setMsg("上传失败！上传文件为空！");
                json.setSuccess(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg("系统异常！" + e.getMessage());
        }
        writeJson(json, response);
    }*/


    /**
     * @description：删除文件
     * @author：justin
     * @date：2019-12-06 15:31
     */
    @PostMapping(value = "/deleteOneFile")
    @ResponseBody
    public void deleteOneFile(@RequestParam(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(id)) {
                json.setSuccess(false);
                json.setMsg("删除失败！参数【id】为空！");
            } else {
                //根据id获取附件信息
                TAttachInfo info = attachInfoService.getAttachInfoById(id);
                if (info != null && StringUtil.isNotEmpty(info.getFilePath())) {
                    String filePath = info.getFilePath().replace(virtualPath, "");//去掉虚拟路劲
                    File file = new File(uploadFolder + filePath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                //根据id删除数据
                attachInfoService.deleteAttachInfoById(id);
                json.setMsg("文件删除成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg("系统异常！" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据单号获取附件信息
     * @author：justin
     * @date：2019-12-09 11:51
     */
    @GetMapping(value = "/getAttachInfoByOrderNo")
    public void getAttachInfoByOrderNo(@RequestParam(name = "orderNo") String orderNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TAttachInfo> infoList = attachInfoService.getAttachInfoByOrderNo(orderNo);
            json.setSuccess(true);
            json.setObj(infoList);
            json.setMsg("获取附件列表成功");
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }


}
