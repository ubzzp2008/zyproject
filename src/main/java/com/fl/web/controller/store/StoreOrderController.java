package com.fl.web.controller.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.store.TStoreItem;
import com.fl.web.entity.store.TStoreOrder;
import com.fl.web.model.store.StoreOrder;
import com.fl.web.service.qm.IMaraCensorService;
import com.fl.web.service.sd.IOrderDeliveryService;
import com.fl.web.service.store.IStoreInfoService;
import com.fl.web.service.store.IStoreOrderService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：StoreOrderController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:36
 */
@RestController
@RequestMapping("/store/storeOrder")
public class StoreOrderController extends BaseController {
    @Autowired
    private IStoreOrderService storeOrderService;
    @Autowired
    private IOrderDeliveryService orderDeliveryService;
    @Autowired
    private IMaraCensorService maraCensorService;
    @Autowired
    private IStoreInfoService storeInfoService;

    /**
     * @description：分页查询出入库单据
     * @author：justin
     * @date：2020-02-21 10:36
     */
    @PostMapping(value = "/queryStoreOrderList")
    public void queryStoreOrderList(@RequestBody StoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.ST_50);
                PageInfo<TStoreOrder> list = storeOrderService.queryStoreOrderList(info);
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
     * @description：采购免检入库
     * @author：justin
     * @date：2020-02-13 14:06
     */
    @PostMapping(value = "/addPDStoreOrder")
    public void addPDStoreOrder(@RequestBody TStoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(info.getItemList())) {
                //检查所选行项数据状态是否正确
                String msg = "";
                List<String> deliveryIdList = new ArrayList<>();
                for (TStoreItem item : info.getItemList()) {
                    if (StringUtil.isEmpty(item.getDeliveryId())) {
                        msg = "操作失败！行项目关键参数【deliveryId】存在空值！";
                        break;
                    }
                    deliveryIdList.add(item.getDeliveryId());
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setSuccess(false);
                    json.setMsg(msg);
                } else {
                    Integer count = orderDeliveryService.checkDeliveryStatusBatch(deliveryIdList, StaticParam.DELIV_20);
                    if (count > 0) {
                        json.setMsg("操作失败！数据状态不正确！请刷新页面重试！");
                        json.setSuccess(false);
                    } else {
                        info.setState(StaticParam.ST_50);
                        String storeNo = storeOrderService.addStoreOrder(info, "0");//免检入库
                        json.setMsg("保存成功！单号：" + storeNo);
                        json.setSuccess(true);
                        json.setObj(storeNo);
                    }
                }
            } else {
                json.setMsg("保存失败！未添加任何物料信息！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：质检入库
     * @author：justin
     * @date：2020-02-13 14:06
     */
    @PostMapping(value = "/addQMStoreOrder")
    public void addQMStoreOrder(@RequestBody TStoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(info.getItemList())) {
                //检查所选行项数据状态是否正确
                String msg = "";
                List<String> maraCensorIdList = new ArrayList<>();
                for (TStoreItem item : info.getItemList()) {
                    if (StringUtil.isEmpty(item.getCensorId())) {
                        msg = "操作失败！行项目关键参数【censorId】存在空值！";
                        break;
                    }
                    maraCensorIdList.add(item.getCensorId());
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setSuccess(false);
                    json.setMsg(msg);
                } else {
                    Integer count = maraCensorService.checkCensorStateBatch(maraCensorIdList, StaticParam.SJ_50);
                    if (count > 0) {
                        json.setMsg("操作失败！数据状态不正确！请刷新页面重试！");
                        json.setSuccess(false);
                    } else {
                        info.setState(StaticParam.ST_50);
                        String orderNo = storeOrderService.addStoreOrder(info, "1");//质检入库
                        json.setMsg("保存成功！单号：" + orderNo);
                        json.setSuccess(true);
                        json.setObj(orderNo);
                    }
                }
            } else {
                json.setMsg("保存失败！未添加任何物料信息！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：保存物料领用申请单
     * @author：justin
     * @date：2020-02-20 11:40
     */
    @PostMapping(value = "/saveOutOrder")
    public void saveOutOrder(@RequestBody TStoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(info.getItemList())) {
                //检查物料库存是否足够
                String msg = "";
                for (TStoreItem item : info.getItemList()) {
                    if (StringUtil.isEmpty(item.getMatnr()) || StringUtil.isEmpty(item.getWareCode()) || StringUtil.isEmpty(item.getWarePosCode())) {
                        msg = "保存失败！关键参数【matnr、wareCode、warePosCode】缺失或存在空值！";
                        break;
                    } else {
                        Integer count = storeInfoService.checkStoreInfoNum(item.getMatnr(), item.getWareCode(), item.getWarePosCode(), item.getOptNum());
                        if (count == 0) {
                            msg = "操作失败！物料【" + item.getMatnr() + "】库存数不足，请刷新页面重试！";
                            break;
                        }
                    }
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setSuccess(false);
                    json.setMsg(msg);
                } else {
                    info.setState(StaticParam.ST_0);
                    String storeNo = storeOrderService.saveOutStoreOrder(info);
                    json.setMsg("保存成功！单号：" + storeNo);
                    json.setSuccess(true);
                    json.setObj(storeNo);
                }
            } else {
                json.setMsg("保存失败！未添加任何物料信息！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：保存并提交出库单
     * @author：justin
     * @date：2020-02-20 18:02
     */
    @PostMapping(value = "/saveSubmitOutOrder")
    public void saveSubmitOutOrder(@RequestBody TStoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(info.getItemList())) {
                //检查物料库存是否足够
                String msg = "";
                for (TStoreItem item : info.getItemList()) {
                    if (StringUtil.isEmpty(item.getMatnr()) || StringUtil.isEmpty(item.getWareCode()) || StringUtil.isEmpty(item.getWarePosCode())) {
                        msg = "保存失败！关键参数【matnr、wareCode、warePosCode】缺失或存在空值！";
                        break;
                    } else {
                        Integer count = storeInfoService.checkStoreInfoNum(item.getMatnr(), item.getWareCode(), item.getWarePosCode(), item.getOptNum());
                        if (count == 0) {
                            msg = "操作失败！物料【" + item.getMatnr() + "】库存数不足，请刷新页面重试！";
                            break;
                        }
                    }
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setSuccess(false);
                    json.setMsg(msg);
                } else {
                    info.setState(StaticParam.ST_0);
                    String storeNo = storeOrderService.saveOutStoreOrder(info);
                    json.setMsg("保存成功！单号：" + storeNo);
                    json.setSuccess(true);
                    json.setObj(storeNo);
                }
            } else {
                json.setMsg("保存失败！未添加任何物料信息！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：分页查询待提交数据
     * @author：justin
     * @date：2020-02-20 13:11
     */
    @PostMapping(value = "/waitSubmitOutOrderList")
    public void waitSubmitOutOrderList(@RequestBody StoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.ST_0);
                info.setOptType(StaticParam.OUT);
                PageInfo<TStoreOrder> list = storeOrderService.queryStoreOrderList(info);
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
     * @description：提交申请单
     * @author：justin
     * @date：2020-02-20 12:41
     */
    @PostMapping(value = "/submitOutOrder")
    public void submitOutOrder(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = storeOrderService.checkStoreOrderStatusBatch(idList, StaticParam.ST_0);//待提交
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                storeOrderService.updateStoreOrderStatusBatch(idList, StaticParam.ST_10);//待审批
                json.setSuccess(true);
                json.setMsg("提交成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询待部门审批数据
     * @author：justin
     * @date：2020-02-20 13:11
     */
    @PostMapping(value = "/waitDeptApplyOutOrderList")
    public void waitDeptApplyOutOrderList(@RequestBody StoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.ST_10);
                info.setOptType(StaticParam.OUT);
                PageInfo<TStoreOrder> list = storeOrderService.queryStoreOrderList(info);
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
     * @description：部门审批领料申请
     * @author：justin
     * @date：2020-02-21 09:23
     */
    @PostMapping(value = "/deptApplyOutOrder")
    public void deptApplyOutOrder(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = storeOrderService.checkStoreOrderStatusBatch(idList, StaticParam.ST_10);//待审批
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                storeOrderService.updateStoreOrderStatusBatch(idList, StaticParam.ST_20);//待仓库确认
                json.setSuccess(true);
                json.setMsg("操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询待仓库确认数据
     * @author：justin
     * @date：2020-02-20 13:11
     */
    @PostMapping(value = "/waitWareConfirmOutOrderList")
    public void waitWareConfirmOutOrderList(@RequestBody StoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.ST_20);
                info.setOptType(StaticParam.OUT);
                PageInfo<TStoreOrder> list = storeOrderService.queryStoreOrderList(info);
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
     * @description：仓库确认领料申请
     * @author：justin
     * @date：2020-02-21 09:23
     */
    @PostMapping(value = "/wareConfirmOutOrder")
    public void wareConfirmOutOrder(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = storeOrderService.checkStoreOrderStatusBatch(idList, StaticParam.ST_20);
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                //批量更新数据状态
                storeOrderService.updateStoreOrderStatusBatch(idList, StaticParam.ST_50);
                json.setSuccess(true);
                json.setMsg("提交成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据单号获取详情
     * @author：justin
     * @date：2020-02-21 10:31
     */
    @GetMapping(value = "/getStoreOrderByStoreNo")
    public void getStoreOrderByStoreNo(@RequestParam("storeNo") String StoreNo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TStoreOrder order = storeOrderService.getStoreOrderByStoreNo(StoreNo);
            json.setSuccess(true);
            json.setObj(order);
            json.setMsg("获取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常：", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：物料领用跟踪报表
     * @author：justin
     * @date：2020-02-21 10:40
     */
    @PostMapping(value = "/queryReqOutOrderList")
    public void queryReqOutOrderList(@RequestBody StoreOrder info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setOptType(StaticParam.OUT);
                info.setStoreType("0");//只查询正常的，不查询冲销的
                PageInfo<TStoreOrder> list = storeOrderService.queryStoreOrderList(info);
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
}
