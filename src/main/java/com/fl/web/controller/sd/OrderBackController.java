package com.fl.web.controller.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.sd.TOrderBack;
import com.fl.web.model.sd.OrderBack;
import com.fl.web.service.sd.IOrderBackService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
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
 * @类名称：OrderBackController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 16:18
 */
@RestController
@RequestMapping("/sd/orderBack")
public class OrderBackController extends BaseController {
    @Autowired
    private IOrderBackService orderBackService;

    /**
     * @description：采购查询待退换货数据
     * @author：justin
     * @date：2020-01-14 17:40
     */
    @PostMapping(value = "/waitPDComfirmBackList")
    public void waitPDComfirmBackList(@RequestBody OrderBack info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.MB_5);
                PageInfo<TOrderBack> list = orderBackService.queryOrderBackList(info);
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
     * @description：分页查询质检退回数据
     * @author：justin
     * @date：2020-01-15 11:24
     */
    @PostMapping(value = "/waitPDDealQMBackList")
    public void waitPDDealQMBackList(@RequestBody OrderBack info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.MB_0);
                PageInfo<TOrderBack> list = orderBackService.queryOrderBackList(info);
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
     * @description：退货处理
     * @author：justin
     * @date：2020-01-15 09:12
     */
    @PostMapping(value = "/backOrderMara")
    public void backOrderMara(@RequestBody List<TOrderBack> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                List<String> idList = new ArrayList<>();
                for (TOrderBack back : infoList) {
                    idList.add(back.getId());
                }
                //检查数据状态是否正确
                Integer count = orderBackService.checkOrderBackStateBatch(idList, StaticParam.MB_5);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderBackService.backOrderMara(infoList);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【infoList】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：换货处理
     * @author：justin
     * @date：2020-01-15 09:12
     */
    @PostMapping(value = "/exchangeOrderMara")
    public void exchangeOrderMara(@RequestBody List<TOrderBack> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                List<String> idList = new ArrayList<>();
                for (TOrderBack back : infoList) {
                    idList.add(back.getId());
                }
                //检查数据状态是否正确
                Integer count = orderBackService.checkOrderBackStateBatch(idList, StaticParam.MB_5);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderBackService.exchangeOrderMara(infoList);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【infoList】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：报废处理
     * @author：justin
     * @date：2020-01-15 11:21
     */
    @PostMapping(value = "/scrapOrderMara")
    public void scrapOrderMara(@RequestBody List<TOrderBack> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                List<String> idList = new ArrayList<>();
                for (TOrderBack back : infoList) {
                    idList.add(back.getId());
                }
                //检查数据状态是否正确
                Integer count = orderBackService.checkOrderBackStateBatch(idList, StaticParam.MB_5);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    orderBackService.scrapOrderMara(infoList);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【infoList】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }
}
