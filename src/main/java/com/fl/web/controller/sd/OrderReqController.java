package com.fl.web.controller.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.sd.TOrderReq;
import com.fl.web.entity.sd.TOrderReqExcel;
import com.fl.web.entity.sd.TOrderReqModel;
import com.fl.web.model.sd.OrderReq;
import com.fl.web.service.sd.IOrderReqService;
import com.fl.web.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderReqController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:03
 */
@RestController
@RequestMapping("/sd/orderReq")
public class OrderReqController extends BaseController {
    @Autowired
    private IOrderReqService orderReqService;
    @Value("${file.exportPath}")
    private String exportPath;

    /**
     * @description：下载模板
     * @author：justin
     * @date：2019-12-26 13:13
     */
    @GetMapping(value = "getReqExcelTemplate")
    public void getReqExcelTemplate(HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            String templateName = "orderReqTemplate.xlsx";
            ClassPathResource cpr = new ClassPathResource("/templates/xls/" + templateName);
            in = new BufferedInputStream(cpr.getInputStream());//得到文档的路径
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode("采购需求申请模板", "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @description：上传解析excel
     * @author：justin
     * @date：2019-12-26 13:04
     */
    @PostMapping(value = "analysisReqExcel")
    public void analysisReqExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //解析excel
            List<TOrderReqExcel> reqList = EasyPoiUtil.importExcel(file, 1, 1, TOrderReqExcel.class);
            String msg = "";
            if (CollectionUtils.isNotEmpty(reqList)) {
                msg = checkExcelData(reqList);
            }
            if (StringUtil.isEmpty(msg)) {
                json.setSuccess(true);
                json.setMsg("解析excel成功");
                json.setObj(reqList);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("采购申请单解析excel异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    private String checkExcelData(List<TOrderReqExcel> reqList) {
        String msg = "";
        for (int i = 0; i < reqList.size(); i++) {
            if (StringUtil.isEmpty(reqList.get(i).getMaktx())) {
                msg += String.format("第%d行物料名称为空;", i + 1);
            }
            if (StringUtil.isEmpty(reqList.get(i).getNorms())) {
                msg += String.format("第%d行规格型号为空;", i + 1);
            }
            if (StringUtil.isEmpty(reqList.get(i).getUnit())) {
                msg += String.format("第%d行单位为空;", i + 1);
            }
            if (StringUtil.isEmpty(reqList.get(i).getMaktl())) {
                msg += String.format("第%d行大类编码为空;", i + 1);
            }
        }
        return msg;
    }

    /**
     * @description：保存采购申请--现有物料
     * @author：justin
     * @date：2019-10-16 17:47
     */
    @PostMapping(value = "/saveOldOrderReq")
    public void saveOldOrderReq(@RequestBody TOrderReqModel info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getCompanyCode()) || StringUtil.isEmpty(info.getCompany())) {
                json.setSuccess(false);
                json.setMsg("请选择公司！");
            } else if (StringUtil.isEmpty(info.getProjectCode())) {
                json.setSuccess(false);
                json.setMsg("请选择单据类型！");
            } else if (StringUtil.isEmpty(info.getReqDept()) || StringUtil.isEmpty(info.getReqName())) {
                json.setSuccess(false);
                json.setMsg("关键参数【reqDept或reqName】为空！");
            } else if (CollectionUtils.isEmpty(info.getItemList())) {
                json.setSuccess(false);
                json.setMsg("您还未添加任何采购物料信息！");
            } else {
                info.setReqStatus(StaticParam.REQ_5);
                info.setReqFlag(StaticParam.REQ_OLD);
                orderReqService.saveOrderReq(info);
                json.setMsg("保存成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：创建并提交采购申请--现有物料
     * @author：justin
     * @date：2019-12-20 17:12
     */
    @PostMapping(value = "/saveSubmitOldOrderReq")
    public void saveSubmitOldOrderReq(@RequestBody TOrderReqModel info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getCompanyCode()) || StringUtil.isEmpty(info.getCompany())) {
                json.setSuccess(false);
                json.setMsg("请选择公司！");
            } else if (StringUtil.isEmpty(info.getProjectCode())) {
                json.setSuccess(false);
                json.setMsg("请选择单据类型！");
            } else if (StringUtil.isEmpty(info.getReqDept()) || StringUtil.isEmpty(info.getReqName())) {
                json.setSuccess(false);
                json.setMsg("关键参数【reqDept或reqName】为空！");
            } else if (CollectionUtils.isEmpty(info.getItemList())) {
                json.setSuccess(false);
                json.setMsg("您还未添加任何采购物料信息！");
            } else {
                info.setReqStatus(StaticParam.REQ_10);
                info.setReqFlag(StaticParam.REQ_OLD);
                orderReqService.saveOrderReq(info);
                json.setMsg("提交成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：保存采购申请--新物料
     * @author：justin
     * @date：2019-10-16 17:47
     */
    @PostMapping(value = "/saveNewOrderReq")
    public void saveNewOrderReq(@RequestBody TOrderReqModel info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getCompanyCode()) || StringUtil.isEmpty(info.getCompany())) {
                json.setSuccess(false);
                json.setMsg("请选择公司！");
            } else if (StringUtil.isEmpty(info.getProjectCode())) {
                json.setSuccess(false);
                json.setMsg("请选择单据类型！");
            } else if (StringUtil.isEmpty(info.getReqDept()) || StringUtil.isEmpty(info.getReqName())) {
                json.setSuccess(false);
                json.setMsg("关键参数【reqDept或reqName】为空！");
            } else if (CollectionUtils.isEmpty(info.getItemList())) {
                json.setSuccess(false);
                json.setMsg("您还未添加任何采购物料信息！");
            } else {
                info.setReqStatus(StaticParam.REQ_0);//保存新物料询价申请
                info.setReqFlag(StaticParam.REQ_NEW);
                orderReqService.saveOrderReq(info);
                json.setMsg("保存成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：创建并提交采购申请单--新物料
     * @author：justin
     * @date：2019-12-20 17:12
     */
    @PostMapping(value = "/saveSubmitNewOrderReq")
    public void saveSubmitNewOrderReq(@RequestBody TOrderReqModel info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getCompanyCode()) || StringUtil.isEmpty(info.getCompany())) {
                json.setSuccess(false);
                json.setMsg("请选择公司！");
            } else if (StringUtil.isEmpty(info.getProjectCode())) {
                json.setSuccess(false);
                json.setMsg("请选择单据类型！");
            } else if (StringUtil.isEmpty(info.getReqDept()) || StringUtil.isEmpty(info.getReqName())) {
                json.setSuccess(false);
                json.setMsg("关键参数【reqDept或reqName】为空！");
            } else if (CollectionUtils.isEmpty(info.getItemList())) {
                json.setSuccess(false);
                json.setMsg("您还未添加任何采购物料信息！");
            } else {
                info.setReqStatus(StaticParam.REQ_1);//待上级领导确认
                info.setReqFlag(StaticParam.REQ_NEW);
                orderReqService.saveOrderReq(info);
                json.setMsg("提交成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询待提交询价需求
     * @author：justin
     * @date：2019-10-16 17:17
     */
    @PostMapping(value = "/waitSubmitNewMara")
    public void waitSubmitNewMara(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_0);
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：提交
     * @author：justin
     * @date：2019-12-20 17:59
     */
    @PostMapping(value = "/submitNewMaraReq")
    public void submitNewMaraReq(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_0);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_1, "提交询价申请", "提交询价申请");//待审批
                json.setSuccess(true);
                json.setMsg("提交成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交成功异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：作废删除数据
     * @author：justin
     * @date：2019-12-20 18:01
     */
    @PostMapping(value = "/deleteNewMaraReq")
    public void deleteNewMaraReq(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_0);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量作废删除数据--由于是暂存状态，所以直接物理删除
                orderReqService.deleteOrderReqBatch(idList);
                json.setSuccess(true);
                json.setMsg("作废成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("作废删除采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询待确认的新物料
     * @author：justin
     * @date：2020-01-02 09:43
     */
    @PostMapping(value = "/waitConfirmNewOrderReq")
    public void waitConfirmNewOrderReq(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_1);
                info.setReqFlag(StaticParam.REQ_NEW);
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：上级领导通过审批
     * @author：justin
     * @date：2020-01-02 10:13
     */
    @PostMapping(value = "/confirmYesToAskPrice")
    public void confirmYesToAskPrice(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isNotEmpty(idList)) {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_1);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    //批量更新数据状态
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_2, "一级负责人通过审批", reason);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【idList】为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：不通过
     * @author：justin
     * @date：2020-01-02 10:23
     */
    @PostMapping(value = "/confirmNoToAskPrice")
    public void confirmNoToAskPrice(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isNotEmpty(idList)) {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_1);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    //批量更新数据状态
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_0, "一级负责人退回", reason);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【idList】为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：待质管部确认质量类别
     * @author：justin
     * @date：2020-01-09 13:43
     */
    @PostMapping(value = "/waitQmConfirmQuality")
    public void waitQmConfirmQuality(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_2);
                info.setReqFlag(StaticParam.REQ_NEW);
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：质管部确认质量类别
     * @author：justin
     * @date：2020-01-09 13:48
     */
    @PostMapping(value = "/qmConfirmQuality")
    public void qmConfirmQuality(@RequestBody List<TOrderReq> reqList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(reqList)) {
                List<String> idList = new ArrayList<>();
                String msg = "";
                //循环参数列表，检验必填值是否存在空值
                for (TOrderReq req : reqList) {
                    if (StringUtil.isEmpty(req.getId()) || StringUtil.isEmpty(req.getQualityType())) {
                        msg = "操作失败！关键参数【id或qualityType】存在空值！";
                    }
                    idList.add(req.getId());
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setMsg(msg);
                    json.setSuccess(false);
                } else {
                    //检查数据状态是否正确
                    Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_2);
                    if (count > 0) {
                        json.setSuccess(false);
                        json.setMsg("数据状态不正确！请刷新页面重试！");
                    } else {
                        //批量更新数据状态
                        orderReqService.updateOrderReqBatch(reqList, StaticParam.REQ_3, "质管部确认质量类别", "质管部确认质量类别");
                        json.setSuccess(true);
                        json.setMsg("操作成功");
                    }
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：待领取进行询价
     * @author：justin
     * @date：2020-01-02 09:56
     */
    @PostMapping(value = "/waitReceiveToAskPrice")
    public void waitReceiveToAskPrice(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_3);
                info.setReqFlag(StaticParam.REQ_NEW);
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：领取需要询价的数据
     * @author：justin
     * @date：2020-01-02 10:25
     */
    @PostMapping(value = "/receiveToAskPrice")
    public void receiveToAskPrice(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            if (CollectionUtils.isNotEmpty(idList)) {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_3);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    //批量更新数据状态
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_4, "采购员领取询价申请", "采购员领取询价申请");
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【idList】为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：待询价列表
     * @author：justin
     * @date：2020-01-02 09:55
     */
    @PostMapping(value = "/waitToAskPrice")
    public void waitToAskPrice(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_4);
                info.setReqFlag(StaticParam.REQ_NEW);
                info.setEnquiryBy(SessionUser.getUserName());
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购申请异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：反馈询价结果
     * @author：justin
     * @date：2020-01-02 10:27
     */
    @PostMapping(value = "/feedBackPriceNewMara")
    public void feedBackPriceNewMara(@RequestBody List<TOrderReq> list, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                List<String> idList = new ArrayList<>();
                for (TOrderReq req : list) {
                    idList.add(req.getId());
                }
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_4);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    //批量更新数据状态
                    orderReqService.updateOrderReqBatch(list, StaticParam.REQ_5, "采购员已询价并反馈", "采购员已询价并反馈");
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：退回至待领取
     * @author：justin
     * @date：2020-01-02 10:39
     */
    @PostMapping(value = "/backToRecevieAskPrice")
    public void backToRecevieAskPrice(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isNotEmpty(idList)) {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_4);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    //批量更新数据状态
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_3, "采购员退回至待领取", reason);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【idList】为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询待提交数据
     * @author：justin
     * @date：2019-10-16 17:17
     */
    @PostMapping(value = "/waitSubmitOrderReqList")
    public void waitSubmitOrderReqList(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_5);
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-27 10:00
     */
    @GetMapping(value = "/getOrderReqById")
    public void getOrderReqById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TOrderReq req = orderReqService.getOrderReqById(id);
            if (req != null) {
                json.setObj(req);
                json.setMsg("获取成功");
                json.setSuccess(true);
            } else {
                json.setMsg("此数据已被删除！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取采购申请单详情异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-27 10:00
     */
    @GetMapping(value = "/getOrderReqDetailById")
    public void getOrderReqDetailById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TOrderReq req = orderReqService.getOrderReqDetailById(id);
            if (req != null) {
                json.setObj(req);
                json.setMsg("获取成功");
                json.setSuccess(true);
            } else {
                json.setMsg("此数据已被删除！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取采购申请单详情异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：提交
     * @author：justin
     * @date：2019-12-20 17:59
     */
    @PostMapping(value = "/submitOrderReq")
    public void submitOrderReq(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_5);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_10, "提交采购申请", "提交采购申请");//待审批
                json.setSuccess(true);
                json.setMsg("提交成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交成功异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：修改并保存
     * @author：justin
     * @date：2019-12-20 17:45
     */
    @PostMapping(value = "/updateSaveOrderReq")
    public void updateSaveOrderReq(@RequestBody TOrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getId()) || StringUtil.isEmpty(info.getProjectCode())) {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【id或projectCode】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatus(info.getId(), StaticParam.REQ_5);//待提交
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    info.setReqStatus(StaticParam.REQ_5);
                    orderReqService.updateSaveOrderReq(info);
                    json.setMsg("保存成功！");
                    json.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：修改并提交
     * @author：justin
     * @date：2019-12-20 17:46
     */
    @PostMapping(value = "/updateSubmitOrderReq")
    public void updateSubmitOrderReq(@RequestBody TOrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getId()) || StringUtil.isEmpty(info.getProjectCode())) {
                json.setSuccess(false);
                json.setMsg("提交失败！关键参数【id或projectCode】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatus(info.getId(), StaticParam.REQ_5);//待提交
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    info.setReqStatus(StaticParam.REQ_10);
                    orderReqService.updateSaveOrderReq(info);
                    json.setMsg("提交成功！");
                    json.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交成功异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：作废删除数据
     * @author：justin
     * @date：2019-12-20 18:01
     */
    @PostMapping(value = "/deleteOrderReq")
    public void deleteOrderReq(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_5);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量作废删除数据--由于是暂存状态，所以直接物理删除
                orderReqService.deleteOrderReqBatch(idList);
                json.setSuccess(true);
                json.setMsg("作废成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("作废删除采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：分页查询待部门领导审批数据
     * @author：justin
     * @date：2019-12-20 17:16
     */
    @PostMapping(value = "/waitDeptApplyOrderReqList")
    public void waitDeptApplyOrderReqList(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_10);
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：部门领导通过采购申请单
     * @author：justin
     * @date：2019-12-24 10:18
     */
    @PostMapping(value = "/deptPassOrderReq")
    public void deptPassOrderReq(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(idList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【idList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_10);//待部门领导审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_20, "部门领导通过采购申请", reason);
                    json.setSuccess(true);
                    json.setMsg("审批成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导通过采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：部门领导否决采购申请单
     * @author：justin
     * @date：2019-12-24 10:18
     */
    @PostMapping(value = "/deptRejectOrderReq")
    public void deptRejectOrderReq(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(idList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【idList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_10);//待部门领导审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_5, "部门领导否决采购申请", reason);
                    json.setSuccess(true);
                    json.setMsg("审批成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导否决采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：获取待采购部审批的单子
     * @author：justin
     * @date：2019-12-24 14:35
     */
    @PostMapping(value = "/waitPDApplyOrderReqList")
    public void waitPDApplyOrderReqList(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_20);//待采购部确认
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：采购部通过采购申请单
     * @author：justin
     * @date：2019-12-24 10:18
     */
    @PostMapping(value = "/purchaseDeptPassOrderReq")
    public void purchaseDeptPassOrderReq(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(idList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【idList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_20);//待采购部审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_30, "采购部领导通过采购申请", reason);
                    json.setSuccess(true);
                    json.setMsg("审批成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导通过采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：采购部否决采购申请单
     * @author：justin
     * @date：2019-12-24 10:18
     */
    @PostMapping(value = "/purchaseDeptRejectOrderReq")
    public void purchaseDeptRejectOrderReq(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(idList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【idList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_20);//待采购部审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_5, "采购部领导否决采购申请", reason);
                    json.setSuccess(true);
                    json.setMsg("审批成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导否决采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：获取待采购下单的数据
     * @author：justin
     * @date：2019-12-24 14:36
     */
    @PostMapping(value = "/waitToPurchaseOrderReqList")
    public void waitToPurchaseOrderReqList(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setReqStatus(StaticParam.REQ_30);//待下单
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：采购下单人员退回
     * @author：justin
     * @date：2019-12-25 09:06
     */
    @PostMapping(value = "/purchaserRejectOrderReq")
    public void purchaserRejectOrderReq(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(idList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【idList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(idList, StaticParam.REQ_30);//待采购部审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderReqService.updateOrderReqStatusBatch(idList, StaticParam.REQ_20, "采购人员退回采购申请", reason);
                    json.setSuccess(true);
                    json.setMsg("退回成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("采购人员否决采购申请单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：采购需求跟踪报表
     * @author：justin
     * @date：2019-12-30 10:22
     */
    @PostMapping(value = "/orderReqReportList")
    public void orderReqReportList(@RequestBody OrderReq info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TOrderReq> list = orderReqService.queryOrderReqList(info);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }


}
