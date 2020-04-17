package com.fl.web.controller.shop;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.shop.TOrderReport;
import com.fl.web.model.shop.OrderReport;
import com.fl.web.service.shop.IOrderReportService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/shop/report")
public class OrderReportController extends BaseController {
    @Autowired
    private IOrderReportService orderReportService;

    @PostMapping(value = "/saveOrderReportBatch")
    public void saveOrderReportBatch(@RequestBody List<TOrderReport> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                orderReportService.saveOrderReportBatch(infoList);
                json.setSuccess(true);
                json.setMsg("操作成功");
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！请选择商品！");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    @PostMapping(value = "/queryOrderReportList")
    public void queryOrderReportList(@RequestBody OrderReport info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TOrderReport> list = orderReportService.queryOrderReportList(info);
                json.setSuccess(true);
                json.setObj(list);
            } else {
                json.setMsg(msg);
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
}
