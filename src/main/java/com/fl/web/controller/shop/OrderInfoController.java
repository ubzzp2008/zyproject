package com.fl.web.controller.shop;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.shop.OrderEntity;
import com.fl.web.entity.shop.TOrderInfo;
import com.fl.web.service.shop.IOrderInfoService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：OrderInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:38
 */
@Controller
@RequestMapping("/shop/order")
public class OrderInfoController extends BaseController {
    @Autowired
    private IOrderInfoService orderInfoService;

    @PostMapping(value = "/saveOrder")
    public void saveOrder(@RequestBody OrderEntity info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getDeskCode())) {
                if (CollectionUtils.isNotEmpty(info.getItemList())) {
                    orderInfoService.saveOrder(info);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                } else {
                    json.setSuccess(false);
                    json.setMsg("操作失败！请选择商品！");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！请选择桌号！");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }


    @GetMapping(value = "/getOrderDeskList")
    public void getOrderDeskList(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TOrderInfo> p = orderInfoService.getOrderDeskList();
            json.setSuccess(true);
            json.setObj(p);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }
}
