package com.fl.web.controller.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.sd.TOrderDelivery;
import com.fl.web.model.sd.OrderDelivery;
import com.fl.web.service.sd.IOrderDeliveryService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderDeliveryController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 14:03
 */
@RestController
@RequestMapping("/sd/orderDelivery")
public class OrderDeliveryController extends BaseController {
    @Autowired
    private IOrderDeliveryService orderDeliveryService;

    /**
     * @description：分页查询已收货信息
     * @author：justin
     * @date：2020-01-09 14:09
     */
    @PostMapping(value = "/queryOrderDeliveryList")
    public void queryOrderDeliveryList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
     * @description：送检确认查询
     * @author：justin
     * @date：2020-01-21 14:29
     */
    @PostMapping(value = "/waitToQMOrderDeliveryList")
    public void waitToQMOrderDeliveryList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.DELIV_0);
                info.setCensorFlag("1");
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
     * @description：确认将数据推送给质检
     * @author：justin
     * @date：2020-01-21 14:35
     */
    @PostMapping(value = "/comfirmDeliveryToQM")
    public void comfirmDeliveryToQM(@RequestBody List<TOrderDelivery> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = new ArrayList<>();
            for (TOrderDelivery delivery : infoList) {
                idList.add(delivery.getId());
            }
            //检查数据状态是否正确
            Integer count = orderDeliveryService.checkDeliveryStatusBatch(idList, StaticParam.DELIV_0);
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                orderDeliveryService.comfirmDeliveryToQM(infoList);
                json.setSuccess(true);
                json.setMsg("操作成功");
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
     * @description：送检确认的作废按钮--删除收货数据
     * @author：justin
     * @date：2020-01-21 14:34
     */
    @PostMapping(value = "/deleteOrderDelivery")
    public void deleteOrderDelivery(@RequestBody TOrderDelivery delivery, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(delivery.getId()) || StringUtil.isEmpty(delivery.getItemId())
                    || StringUtil.isEmpty(delivery.getOrderNo()) || delivery.getNum() == null) {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【id、orderNo、itemId、num】存在空值！");
            } else {
                //检查数据状态是否正确
                Integer count = orderDeliveryService.checkDeliveryStatus(delivery.getId(), StaticParam.DELIV_0);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderDeliveryService.deleteOrderDelivery(delivery);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
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
     * @description：分页查询质检退回数据
     * @author：justin
     * @date：2020-01-21 14:56
     */
    @PostMapping(value = "/qmBackOrderDeliveryList")
    public void qmBackOrderDeliveryList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.DELIV_11);
                info.setCensorFlag("1");
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
     * @desc：分页查询不送检待创建入库单的数据
     * @author：justin
     * @date：2020/2/5 10:24
     */
    @PostMapping(value = "/waitPDToStoreList")
    public void waitPDToStoreList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.DELIV_0);
                info.setCensorFlag("0");
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
     * @description：确认送入仓库
     * @author：justin
     * @date：2020-02-13 09:45
     */
    @PostMapping(value = "/comfirmPDToStore")
    public void comfirmPDToStore(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查数据状态是否正确
            Integer count = orderDeliveryService.checkDeliveryStatusBatch(idList, StaticParam.DELIV_0);
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("数据状态不正确！请刷新页面重试！");
            } else {
                orderDeliveryService.comfirmPDToStore(idList, StaticParam.DELIV_20);
                json.setSuccess(true);
                json.setMsg("操作成功");
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
     * @description：分页查询仓库退回数据
     * @author：justin
     * @date：2020-01-21 14:56
     */
    @PostMapping(value = "/storeBackOrderDeliveryList")
    public void storeBackOrderDeliveryList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                //todo 待实现
//                info.setState(StaticParam.DELIV_21);
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
     * @description：免检待入库查询
     * @author：justin
     * @date：2020-02-13 10:55
     */
    @PostMapping(value = "/queryNoInspectionList")
    public void queryNoInspectionList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.DELIV_20);
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
     * @description：质检待入库查询
     * @author：justin
     * @date：2020-02-13 10:46
     */
    @PostMapping(value = "/queryInspectionList")
    public void queryInspectionList(@RequestBody OrderDelivery info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.DELIV_30);
                PageInfo<TOrderDelivery> list = orderDeliveryService.queryOrderDeliveryList(info);
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
