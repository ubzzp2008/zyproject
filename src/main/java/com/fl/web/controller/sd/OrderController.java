package com.fl.web.controller.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.sd.TOrderHead;
import com.fl.web.entity.sd.TOrderItem;
import com.fl.web.entity.sd.TOrderReq;
import com.fl.web.model.sd.OrderHead;
import com.fl.web.model.sd.OrderItem;
import com.fl.web.service.sd.IOrderReqService;
import com.fl.web.service.sd.IOrderService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:02
 */
@RestController
@RequestMapping("/sd/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderReqService orderReqService;


    /**
     * @description：保存采购订单
     * @author：justin
     * @date：2019-10-16 19:01
     */
    @PostMapping(value = "/saveOrder")
    public void saveOrder(@RequestBody TOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(info.getItemList())) {
                info.setOrderStatus(StaticParam.ORD_5);
                String orderNo = orderService.saveOrder(info);
                json.setMsg("保存成功！单号：" + orderNo);
                json.setSuccess(true);
                json.setObj(orderNo);
            } else {
                json.setMsg("保存失败！未添加任何采购物料信息！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存采购申请单异常", e);
            json.setMsg("保存失败！系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：保存提交采购订单
     * @author：justin
     * @date：2019-12-25 10:11
     */
    @PostMapping(value = "/saveSubmitOrder")
    public void saveSubmitOrder(@RequestBody TOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(info.getItemList())) {
                info.setOrderStatus(StaticParam.ORD_10);
                String orderNo = orderService.saveOrder(info);
                json.setMsg("保存成功！单号：" + orderNo);
                json.setSuccess(true);
                json.setObj(orderNo);
            } else {
                json.setMsg("保存失败！未添加任何采购物料信息！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存采购申请单异常", e);
            json.setMsg("保存失败！系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据采购需求id构建订单数据
     * @author：justin
     * @date：2019-12-27 15:02
     */
    @PostMapping(value = "/addOrderByReqIdList")
    public void addOrderByReqIdList(@RequestBody List<String> reqIdList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TOrderReq> reqOrderList = orderReqService.queryOrderReqByIdList(reqIdList);
            if (CollectionUtils.isNotEmpty(reqOrderList)) {
                //先验证所选择的数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(reqIdList, StaticParam.REQ_30);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    //验证所选择的数据是否为相同的公司、单据类型、申请部门、申请人
                    String companyCode = reqOrderList.get(0).getCompanyCode();//取第一条数据的公司编码
                    String projectCode = reqOrderList.get(0).getProjectCode();//取第一条数据的单据类型
                    String reqDept = reqOrderList.get(0).getReqDept();//取第一条数据的申请部门
                    String reqName = reqOrderList.get(0).getReqName();//取第一条数据的申请人
                    String msg = "";
                    TOrderHead head = new TOrderHead();
                    List<TOrderItem> itemList = new ArrayList<>();
                    for (TOrderReq req : reqOrderList) {
                        if (!StringUtil.equals(companyCode, req.getCompanyCode())
                                || !StringUtil.equals(projectCode, req.getProjectCode())
                                || !StringUtil.equals(reqDept, req.getReqDept())
                                || !StringUtil.equals(reqName, req.getReqName())) {
                            msg = "错误！只能选择相同公司、相同单据类型、相同申请部门、相同申请人的数据进行操作！";
                            break;
                        } else {
                            //构建单据行项目信息
                            TOrderItem item = new TOrderItem();
                            BeanUtils.copyProperties(req, item);
                            item.setReqId(req.getId());
                            itemList.add(item);
                        }
                    }
                    if (StringUtil.isNotEmpty(msg)) {
                        json.setSuccess(false);
                        json.setMsg(msg);
                    } else {
                        //构建单据头信息
                        head.setCompanyCode(companyCode);
                        head.setCompany(reqOrderList.get(0).getCompany());
                        head.setProjectCode(projectCode);
                        head.setProjectName(reqOrderList.get(0).getProjectName());
                        head.setReqDept(reqDept);
                        head.setReqName(reqName);
                        head.setItemList(itemList);
                        json.setSuccess(true);
                        json.setMsg("数据验证通过");
                        json.setObj(head);
                    }
                }
            } else {
                json.setSuccess(false);
                json.setMsg("数据异常！未找到相应数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取采购需求异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：申请单转采购订单--保存
     * @author：justin
     * @date：2019-12-25 09:50
     */
    @PostMapping(value = "/saveReqToOrder")
    public void saveReqToOrder(@RequestBody TOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isEmpty(info.getItemList())) {
                json.setSuccess(false);
                json.setMsg("保存失败！未添加任何采购物料信息！");
            } else {
                //检查行项目数据状态是否正确
                List<String> reqIdList = new ArrayList<>();
                for (TOrderItem item : info.getItemList()) {
                    reqIdList.add(item.getReqId());
                }
                //先验证所选择的数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(reqIdList, StaticParam.REQ_30);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    if (info.getTotalMoney().compareTo(info.getActualMoney().add(info.getRestMoney())) == 0) {
                        info.setOrderStatus(StaticParam.ORD_5);
                        String orderNo = orderService.saveReqToOrder(info);
                        json.setMsg("保存成功！单号：" + orderNo);
                        json.setSuccess(true);
                        json.setObj(orderNo);
                    } else {
                        json.setSuccess(false);
                        json.setMsg("保存失败！实付金额+待付金额不等于应付金额！");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存采购单异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：申请单转采购订单--保存并提交
     * @author：justin
     * @date：2019-12-25 10:12
     */
    @PostMapping(value = "/saveSubmitReqToOrder")
    public void saveSubmitReqToOrder(@RequestBody TOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isEmpty(info.getItemList())) {
                json.setSuccess(false);
                json.setMsg("提交失败！未添加任何采购物料信息！");
            } else {
                //检查行项目数据状态是否正确
                List<String> reqIdList = new ArrayList<>();
                for (TOrderItem item : info.getItemList()) {
                    reqIdList.add(item.getReqId());
                }
                //先验证所选择的数据状态是否正确
                Integer count = orderReqService.checkReqStatusBatch(reqIdList, StaticParam.REQ_30);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    if (info.getTotalMoney().compareTo(info.getActualMoney().add(info.getRestMoney())) == 0) {
                        info.setOrderStatus(StaticParam.ORD_10);
                        String orderNo = orderService.saveReqToOrder(info);
                        json.setMsg("提交成功！单号：" + orderNo);
                        json.setSuccess(true);
                        json.setObj(orderNo);
                    } else {
                        json.setSuccess(false);
                        json.setMsg("提交失败！实付金额+待付金额不等于应付金额！");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("提交采购单异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询待提交数据
     * @author：justin
     * @date：2019-10-16 17:17
     */
    @PostMapping(value = "/waitSubmitOrderList")
    public void waitSubmitOrderList(@RequestBody OrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ORD_5);
                PageInfo<TOrderHead> list = orderService.queryOrderList(info);
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
     * @description：提交采购订单
     * @author：justin
     * @date：2019-12-25 11:39
     */
    @PostMapping(value = "/submitOrder")
    public void submitOrder(@RequestBody List<String> orderNoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderService.checkOrderStatusBatch(orderNoList, StaticParam.ORD_5);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                orderService.updateOrderStatusBatch(orderNoList, StaticParam.ORD_10, "提交采购订单", "提交采购订单");//待审批
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
     * @description：修改并保存采购订单
     * @author：justin
     * @date：2019-12-30 09:19
     */
    @PostMapping(value = "/updateSaveOrder")
    public void updateSaveOrder(@RequestBody TOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderService.checkOrderStatus(info.getOrderNo(), StaticParam.ORD_5);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //检查应付金额=实付金额+待付金额
                if (info.getTotalMoney().compareTo(info.getActualMoney().add(info.getRestMoney())) == 0) {
                    //批量更新数据状态
                    info.setOrderStatus(StaticParam.ORD_5);
                    orderService.updateSaveOrder(info);//待审批
                    json.setSuccess(true);
                    json.setMsg("保存成功");
                } else {
                    json.setSuccess(false);
                    json.setMsg("保存失败！实付金额+待付金额不等于应付金额！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("修改保存成功异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：修改并提交采购订单
     * @author：justin
     * @date：2019-12-30 09:19
     */
    @PostMapping(value = "/updateSubmitOrder")
    public void updateSubmitOrder(@RequestBody TOrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderService.checkOrderStatus(info.getOrderNo(), StaticParam.ORD_5);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                info.setOrderStatus(StaticParam.ORD_10);
                orderService.updateSaveOrder(info);//待审批
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
     * @description：作废采购订单
     * @author：justin
     * @date：2019-12-30 09:17
     */
    @PostMapping(value = "/deleteOrder")
    public void deleteOrder(@RequestParam(name = "orderNo") String orderNo, @RequestParam(name = "reqId") String reqId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderService.checkOrderStatus(orderNo, StaticParam.ORD_5);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                orderService.deleteOrder(orderNo, reqId);
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
     * @description：分页查询待采购部领导审批的采购订单
     * @author：justin
     * @date：2019-12-25 11:30
     */
    @PostMapping(value = "/waitPDApplyOrderList")
    public void waitPDApplyOrderList(@RequestBody OrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOrderStatus(StaticParam.ORD_10);
                PageInfo<TOrderHead> list = orderService.queryOrderList(info);
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
     * @description：采购部领导通过采购订单
     * @author：justin
     * @date：2019-12-30 10:06
     */
    @PostMapping(value = "/deptPDPassOrder")
    public void deptPDPassOrder(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> orderNoList = (List<String>) param.get("orderNoList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(orderNoList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("审批失败！关键参数【orderNoList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderService.checkOrderStatusBatch(orderNoList, StaticParam.ORD_10);//待部门领导审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderService.updateOrderStatusBatch(orderNoList, StaticParam.ORD_20, "部门领导通过采购订单", reason);
                    json.setSuccess(true);
                    json.setMsg("审批成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导通过采购订单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：采购部领导否决采购订单
     * @author：justin
     * @date：2019-12-30 10:06
     */
    @PostMapping(value = "/deptPDRejectOrder")
    public void deptPDRejectOrder(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> orderNoList = (List<String>) param.get("orderNoList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isEmpty(orderNoList) || StringUtil.isEmpty(reason)) {
                json.setSuccess(false);
                json.setMsg("审批失败！关键参数【orderNoList或reason】为空！");
            } else {
                //检查数据状态是否正确
                Integer count = orderService.checkOrderStatusBatch(orderNoList, StaticParam.ORD_10);//待部门领导审批
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderService.updateOrderStatusBatch(orderNoList, StaticParam.ORD_5, "部门领导否决采购订单", reason);
                    json.setSuccess(true);
                    json.setMsg("审批成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导否决采购订单异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：查询待收货列表
     * @author：justin
     * @date：2019-12-25 11:35
     */
    @PostMapping(value = "/waitDeliveryOrderList")
    public void waitDeliveryOrderList(@RequestBody OrderItem info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TOrderItem> list = orderService.queryWaitDeliveryList(info);
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
     * @description：确认收货
     * @author：justin
     * @date：2020-01-08 09:18
     */
    @PostMapping(value = "/deliveryOrderItem")
    public void deliveryOrderItem(@RequestBody List<TOrderItem> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                String msg = "";
                for (TOrderItem info : infoList) {
                    //检查收货数量是否大于可收货数量
                    TOrderItem item = orderService.checkDeliveryNum(info.getId());
                    if (item.getUsableNum().compareTo(info.getUsableNum()) == -1) {
                        msg += "单号" + info.getOrderNo() + "的物料" + info.getMatnr() + "收货数量大于可收货数量；";
                    }
                    if (item.getUsableNum().compareTo(info.getUsableNum()) == 0) {
                        info.setDeliveryState("0");//完全收货
                    }
                    if (item.getUsableNum().compareTo(info.getUsableNum()) == 1) {
                        info.setDeliveryState("-1");//部分收货
                    }
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setSuccess(false);
                    json.setMsg(msg);
                } else {
                    orderService.deliveryOrderItem(infoList);
                    json.setSuccess(true);
                    json.setMsg("收货成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("收货失败！参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：差异收货
     * @author：justin
     * @date：2020-01-08 14:32
     */
    @PostMapping(value = "/deliveryDiffOrderItem")
    public void deliveryDiffOrderItem(@RequestBody List<TOrderItem> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                orderService.deliveryDiffOrderItem(infoList);
                json.setSuccess(true);
                json.setMsg("差异收货成功");
            } else {
                json.setSuccess(false);
                json.setMsg("差异收货失败！参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：根据单号获取单据详情--带日志信息
     * @author：justin
     * @date：2019-10-16 18:42
     */
    @GetMapping(value = "/getOrderDetailByOrderNo")
    public void getOrderDetailByOrderNo(@RequestParam(name = "orderNo") String orderNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TOrderHead head = orderService.getOrderDetailByOrderNo(orderNo);
            json.setObj(head);
            json.setMsg("获取成功");
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取采购订单详情异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据单号获取单据详情--不带日志信息
     * @author：justin
     * @date：2019-10-16 18:42
     */
    @GetMapping(value = "/getOrderByOrderNo")
    public void getOrderByOrderNo(@RequestParam(name = "orderNo") String orderNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TOrderHead head = orderService.getOrderByOrderNo(orderNo);
            json.setObj(head);
            json.setMsg("获取成功");
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取采购订单详情异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：采购需求跟踪报表
     * @author：justin
     * @date：2019-12-30 10:22
     */
    @PostMapping(value = "/orderReportList")
    public void orderReportList(@RequestBody OrderHead info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TOrderHead> list = orderService.queryOrderList(info);
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
