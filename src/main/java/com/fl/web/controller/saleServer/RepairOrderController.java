package com.fl.web.controller.saleServer;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TAttachInfo;
import com.fl.web.entity.saleServer.TRepairOrderHead;
import com.fl.web.entity.saleServer.TRepairOrderItem;
import com.fl.web.model.saleServer.RepairOrderHead;
import com.fl.web.model.saleServer.RepairOrderItem;
import com.fl.web.service.mdm.IAttachInfoService;
import com.fl.web.service.saleServer.IRepairOrderService;
import com.fl.web.service.system.IUserPositionService;
import com.fl.web.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DeviceRepairController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:22
 */
@RestController
@RequestMapping("/saleServer/repairOrder")
public class RepairOrderController extends BaseController {
    @Autowired
    private IRepairOrderService repairOrderService;
    @Autowired
    private IAttachInfoService attachInfoService;
    @Autowired
    private IUserPositionService userPositionService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.virtualPath}")
    private String virtualPath;

    /**
     * @description：保存维修单，上报时报修单不存在行项目
     * @author：justin
     * @date：2019-10-15 08:42
     */
    @PostMapping(value = "/saveRepairOrder")
    public void saveRepairOrder(@RequestPart("formData") TRepairOrderHead info, @RequestParam("files") MultipartFile[] files, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TAttachInfo> attachInfoList = new ArrayList<>();
            String msg = "";
            if (files.length > 0) {
                msg = checkFiles(files, 10, "M");//检查文件大小
                if (StringUtil.isEmpty(msg)) {
                    for (MultipartFile file : files) {
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
                        TAttachInfo ta = new TAttachInfo();
                        ta.setId(UUIDUtil.get32UUID());
                        ta.setOldName(file.getOriginalFilename());
                        ta.setFileName(newName);
                        ta.setFileType(suffix);
                        ta.setFileSize((int) file.getSize());
                        ta.setFilePath(vPath);
                        ta.setFlag("0");
                        attachInfoList.add(ta);
                    }
                }
            }
            if (StringUtil.isNotEmpty(msg)) {
                json.setSuccess(false);
                json.setMsg("文件【" + msg + "】过大，单个文件不能超过10M");
            } else {
                String orderNo = repairOrderService.saveRepairOrder(info, attachInfoList);
                json.setMsg("故障上报成功！单号：" + orderNo);
                json.setSuccess(true);
                json.setObj(orderNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常！" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：检查文件大小
     * @author：justin
     * @date：2019-12-10 15:26
     */
    private String checkFiles(MultipartFile[] files, int size, String unit) {
        String msg = "";
        for (MultipartFile file : files) {
            boolean b = checkFileSize(file.getSize(), size, unit);
            if (b) {
                msg += file.getOriginalFilename() + "；";
            }
        }
        return msg;
    }

    /**
     * @description：分页查询待确认的维修单--无数据权限
     * @author：justin
     * @date：2019-10-15 14:35
     */
    @PostMapping(value = "/waitConfirmRepairOrderList")
    public void waitConfirmRepairOrderList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ROS_0);//待确认
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：指派维修单到指定维修员
     * @author：justin
     * @date：2019-11-13 14:42
     */
    @PostMapping(value = "/assignRepairOrder")
    public void assignRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {

            List<String> idList = (List<String>) map.get("idList");
            String userName = (String) map.get("userName");
            String realName = (String) map.get("realName");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isEmpty(idList)) {
                json.setSuccess(false);
                json.setMsg("参数【idList】为空！");
            } else if (StringUtil.isEmpty(userName)) {
                json.setSuccess(false);
                json.setMsg("审批失败！被指派人不能为空！");
            } else if (StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("审批失败！审批意见不能为空！");
            } else {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_0);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.assignRepairOrder(idList, userName, realName, reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：作废报修单
     * @author：justin
     * @date：2019-12-23 11:13
     */
    @PostMapping(value = "/cancelRepairOrder")
    public void cancelRepairOrder(@RequestBody Map<String, String> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {

            String id = map.get("id");
            String reason = map.get("reason");
            if (StringUtil.isEmpty(id)) {
                json.setSuccess(false);
                json.setMsg("参数【id】为空！");
            } else if (StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("作废失败！作废原因不能为空！");
            } else {
                //检查数据状态是否正确
                List<String> idList = new ArrayList<>();
                idList.add(id);
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_0);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.cancelRepairOrder(id, reason);
                    json.setMsg("作废成功");
                    json.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：维修单确认通过
     * @author：justin
     * @date：2019-10-15 14:35
     */
    @PostMapping(value = "/confirmYesRepairOrder")
    public void confirmYesRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isEmpty(idList)) {
                json.setSuccess(false);
                json.setMsg("参数【idList】为空！");
            } else if (StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("审批失败！审批意见不能为空！");
            } else {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_0);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    map.put("orderStatus", StaticParam.ROS_5);//待领取
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_5, "部门领导审批通过", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：维修单确认不通过
     * @author：justin
     * @date：2019-10-15 17:46
     */
    @PostMapping(value = "/confirmNoRepairOrder")
    public void confirmNoRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isEmpty(idList)) {
                json.setSuccess(false);
                json.setMsg("参数【idList】为空！");
            } else if (StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("操作失败！审批意见不能为空！");
            } else {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_0);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    //审批不通过--到质管部归档
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_30, "部门领导审批不通过", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：分页查询待领取的报修单--无数据权限
     * @author：justin
     * @date：2019-10-16 20:14
     */
    @PostMapping(value = "/waitReceiveRepairOrderList")
    public void waitReceiveRepairOrderList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ROS_5);//待领取
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：领取报修单
     * @author：justin
     * @date：2019-10-16 20:04
     */
    @PostMapping(value = "/receiveRepairOrder")
    public void receiveRepairOrder(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_5);
            if (CollectionUtils.isNotEmpty(headList)) {
                json.setMsg("数据状态不正确，请刷新页面重试！");
                json.setSuccess(false);
            } else {
                repairOrderService.receiveRepairOrder(idList);
                json.setMsg("保存成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：维修员退回报修单
     * @author：justin
     * @date：2019-10-16 20:15
     */
    @PostMapping(value = "/toBackRepairOrder")
    public void toBackRepairOrder(@RequestBody List<String> idList, @RequestParam(name = "orderNo") String orderNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList) && idList.size() == 1) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_10);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.toBackRepairOrder(idList.get(0), orderNo);
                    json.setMsg("保存成功");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取待处理的维修单--管理员查询所有，维修员查询自己领取的
     * @author：justin
     * @date：2019-10-25 15:34
     */
    @PostMapping(value = "/waitDealRepairOrderList")
    public void waitDealRepairOrderList(@RequestBody RepairOrderHead info, @RequestParam("posCode") String posCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                // 维修员岗位，查询自己领取的
                logger.info("岗位编码{}，权限值为{}", posCode, AuthorEnum.getVal(posCode));
                if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHONLY)) {
                    info.setReceiveBy(SessionUser.getUserName());
                }
                info.setOrderStatus(StaticParam.ROS_10);//已领取待处理
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：根据单据号获取单据详情
     * @author：justin
     * @date：2019-10-25 15:38
     */
    @GetMapping(value = "/getRepairOrderByOrderNo")
    public void getRepairOrderByOrderNo(@RequestParam(name = "orderNo") String orderNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TRepairOrderHead head = repairOrderService.getRepairOrderByOrderNo(orderNo);
            if (head != null) {
                json.setObj(head);
                json.setMsg("成功获取单据信息");
                json.setSuccess(true);
            } else {
                json.setMsg("未找到单号为【" + orderNo + "】的单据信息");
                json.setSuccess(false);
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
     * @description：保存单据行项目信息
     * @author：justin
     * @date：2019-10-25 16:19
     */
    @PostMapping(value = "/saveRepairOrderItem")
    public void saveRepairOrderItem(@RequestBody Map<String, String> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String orderId = map.get("orderId");
            String orderNo = map.get("orderNo");
            String param = map.get("itemList");
            List<TRepairOrderItem> itemList = JSONArray.parseArray(param, TRepairOrderItem.class);
            if (StringUtil.isEmpty(orderId)) {
                json.setSuccess(false);
                json.setMsg("保存失败！参数【orderId】为空！");
            } else if (StringUtil.isEmpty(orderNo)) {
                json.setSuccess(false);
                json.setMsg("保存失败！参数【orderNo】为空！");
            } else if (CollectionUtils.isEmpty(itemList)) {
                json.setMsg("保存失败！行项目信息为空！");
                json.setSuccess(false);
            } else {
                logger.info("参数json：" + JSONArray.toJSONString(itemList));
                repairOrderService.saveRepairOrderItem(orderId, orderNo, itemList);
                json.setMsg("保存成功");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("添加维修部件异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取维修方案待审批的单子
     * @author：justin
     * @date：2019-11-19 14:05
     */
    @PostMapping(value = "/waitApplyRepairPlanList")
    public void waitApplyRepairPlanList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ROS_15);//维修方案待审批
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：通过维修方案
     * @author：justin
     * @date：2019-11-19 14:11
     */
    @PostMapping(value = "/saveApplyRepairPlan")
    public void saveApplyRepairPlan(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_15);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.saveApplyRepairPlan(idList, reason);
                    json.setMsg("审批成功！");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("审批失败！参数【idList、reason】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：维修方案退回
     * @author：justin
     * @date：2019-11-25 11:27
     */
    @PostMapping(value = "/toBackApplyRepairPlan")
    public void toBackApplyRepairPlan(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_15);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_10, "维修方案审批不通过", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("审批失败！参数【idList、reason】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询待提交的单据--管理员查询所有，维修员查询自己领取的
     * @author：justin
     * @date：2019-11-27 16:18
     */
    @PostMapping(value = "/waitRepairOrderFinishList")
    public void waitRepairOrderFinishList(@RequestBody RepairOrderHead info, @RequestParam("posCode") String posCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                // 维修员岗位，查询自己领取的
                logger.info("岗位编码{}，权限值为{}", posCode, AuthorEnum.getVal(posCode));
                if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHONLY)) {
                    info.setReceiveBy(SessionUser.getUserName());
                }
                info.setOrderStatus(StaticParam.ROS_20);//维修中（待提交）
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：维修人确认维修单处理完毕
     * @author：justin
     * @date：2019-11-14 10:00
     */
    @PostMapping(value = "/updateRepairOrderFinish")
    public void updateRepairOrderFinish(@RequestParam(name = "orderId") String orderId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(orderId)) {
                json.setSuccess(false);
                json.setMsg("操作失败！参数【orderId】为空！");
            } else {
                //检查数据状态是否正确
                List<String> idList = new ArrayList<>();
                idList.add(orderId);
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_20);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    //验证该订单是否所有行项目状态都已完成
                    Integer count = repairOrderService.checkItemStatusByOrderId(orderId);
                    if (count > 0) {
                        json.setMsg("保存失败！此维修单还存在流程未结束的行项！");
                        json.setSuccess(false);
                    } else {
                        Integer tac = attachInfoService.checkAttachInfoByTableId(orderId, "1");
                        if (tac > 0) {
                            repairOrderService.updateRepairOrderFinish(orderId);
                            json.setMsg("保存成功");
                            json.setSuccess(true);
                        } else {
                            json.setMsg("提交失败！您还未上传《维修报告》的附件！");
                            json.setSuccess(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("维修员确认维修单处理完毕异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询待部门领导确认的单据
     * @author：justin
     * @date：2019-11-01 14:09
     */
    @PostMapping(value = "/waitDeptApplyOrderList")
    public void waitDeptApplyOrderList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ROS_25);//待部门领导确认
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：部门领导复审维修单
     * @author：justin
     * @date：2019-10-15 18:43
     */
    @PostMapping(value = "/deptApplyYesRepairOrder")
    public void deptApplyYesRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_25);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_30, "复审通过", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("审批失败！参数【idList、reason】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：部门领导复审不通过
     * @author：justin
     * @date：2019-12-11 16:21
     */
    @PostMapping(value = "/deptApplyNoRepairOrder")
    public void deptApplyNoRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_25);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_20, "复审不通过", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("审批失败！参数【idList、reason】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询待质管部确认的单据
     * @author：justin
     * @date：2019-11-01 14:15
     */
    @PostMapping(value = "/waitQmApplyOrderList")
    public void waitQmApplyOrderList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ROS_30);//待质管部确认
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：质管部确认维修单
     * @author：justin
     * @date：2019-10-15 18:43
     */
    @PostMapping(value = "/qmApplyYesRepairOrder")
    public void qmApplyYesRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_30);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_50, "质管部已归档", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("审批失败！参数【idList、reason】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：质管部退回维修单
     * @author：justin
     * @date：2019-12-11 16:18
     */
    @PostMapping(value = "/qmApplyNoRepairOrder")
    public void qmApplyNoRepairOrder(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) map.get("idList");
            String reason = (String) map.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                List<TRepairOrderHead> headList = repairOrderService.findDataByIdAndStatus(idList, StaticParam.ROS_30);
                if (CollectionUtils.isNotEmpty(headList)) {
                    json.setMsg("数据状态不正确，请刷新页面重试！");
                    json.setSuccess(false);
                } else {
                    repairOrderService.updateRepairOrderStatusBatch(idList, StaticParam.ROS_25, "质管部退回", reason);
                    json.setMsg("审批成功");
                    json.setSuccess(true);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("审批失败！参数【idList、reason】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("更改维修单状态异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据客户编码和设备编码获取单据列表（维修记录单据报表）
     * @author：justin
     * @date：2019-12-01 13:18
     */
    @PostMapping(value = "/repairOrderByKunnrList")
    public void repairOrderByKunnrList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                if (StringUtil.isNotEmpty(info.getKdId())) {
                    PageInfo<TRepairOrderHead> list = repairOrderService.repairOrderByKunnrList(info);
                    json.setObj(list);
                    json.setSuccess(true);
                } else {
                    json.setSuccess(false);
                    json.setMsg("操作失败！参数【kdId】！");
                }
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
     * @description：获取维修记录（物料明细）
     * @author：justin
     * @date：2019-12-05 09:31
     */
    @PostMapping(value = "/repairOrderDetailList")
    public void repairOrderDetailList(@RequestBody RepairOrderItem info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                if (StringUtil.isNotEmpty(info.getKdId())) {
                    PageInfo<TRepairOrderItem> list = repairOrderService.repairOrderDetailList(info);
                    json.setObj(list);
                    json.setSuccess(true);
                } else {
                    json.setSuccess(false);
                    json.setMsg("操作失败！参数【kdId】为空！");
                }
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
     * @description：获取设备所有维修单
     * @author：justin
     * @date：2020-01-16 09:19
     */
    @PostMapping(value = "/repairOrderByDeviceList")
    public void repairOrderByDeviceList(@RequestBody RepairOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                if (StringUtil.isNotEmpty(info.getDeviceSN())) {
                    PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
                    json.setObj(list);
                    json.setSuccess(true);
                } else {
                    json.setSuccess(false);
                    json.setMsg("操作失败！参数【deviceSN】为空！");
                }
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
     * @description：获取设备所有维修物料
     * @author：justin
     * @date：2020-01-16 09:29
     */
    @PostMapping(value = "/repairMaraByDeviceList")
    public void repairMaraByDeviceList(@RequestBody RepairOrderItem info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                if (StringUtil.isNotEmpty(info.getDeviceSN())) {
                    PageInfo<TRepairOrderItem> list = repairOrderService.queryRepairItemList(info);
                    json.setObj(list);
                    json.setSuccess(true);
                } else {
                    json.setSuccess(false);
                    json.setMsg("操作失败！参数【deviceSN】为空！");
                }
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
     * @description：维修单跟踪报表查询--管理员查询所有，维修员查询自己处理的单据
     * @author：justin
     * @date：2019-12-01 09:16
     */
    @PostMapping(value = "/repairOrderReportList")
    public void repairOrderReportList(@RequestBody RepairOrderHead info, @RequestParam("posCode") String posCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                //自己查自己的，部门领导查询整个部门的
                logger.info("岗位编码{}，权限值为{}", posCode, AuthorEnum.getVal(posCode));
                if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHONLY)) {
                    info.setReceiveBy(SessionUser.getUserName());
                }
                // 部门领导获取该部门所有数据
                if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHDEPT)) {
                    String posId = SessionUser.getPosIdByPosCode(posCode);
                    List<String> users = userPositionService.getUserNameByPid(posId);
                    info.setUserNameList(users);
                }
                PageInfo<TRepairOrderHead> list = repairOrderService.queryRepairOrderList(info);
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
     * @description：维修单跟踪报表导出
     * @author：justin
     * @date：2019-12-01 13:28
     */
    @RequestMapping("exportRepairOrder")
    public void exportRepairOrder(@RequestBody RepairOrderHead info, @RequestParam("posCode") String posCode, HttpServletResponse response) {
        Long t1 = System.currentTimeMillis();
        //自己查自己的，部门领导查询整个部门的
        logger.info("岗位编码{}，权限值为{}", posCode, AuthorEnum.getVal(posCode));
        if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHONLY)) {
            info.setReceiveBy(SessionUser.getUserName());
        }
        // 部门领导获取该部门所有数据
        if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHDEPT)) {
            String posId = SessionUser.getPosIdByPosCode(posCode);
            List<String> users = userPositionService.getUserNameByPid(posId);
            info.setUserNameList(users);
        }
        List<TRepairOrderHead> orderList = repairOrderService.exportRepairOrder(info);
        Long t2 = System.currentTimeMillis();
        logger.info("查询耗时{}秒", (t2 - t1) / 1000);
        Long t3 = System.currentTimeMillis();
        EasyPoiUtil.exportExcelXlsx(orderList, null, null, TRepairOrderHead.class, "维修单跟踪报表", response);
        Long t4 = System.currentTimeMillis();
        logger.info("写入excel耗时{}秒", (t4 - t3) / 1000);
        logger.info("导出总耗时{}秒", (t4 - t1) / 1000);
    }

}
