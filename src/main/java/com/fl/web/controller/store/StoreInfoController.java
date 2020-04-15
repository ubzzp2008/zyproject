package com.fl.web.controller.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.store.TStoreInfo;
import com.fl.web.model.store.StoreInfo;
import com.fl.web.service.store.IStoreInfoService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：StoreInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:30
 */
@RestController
@RequestMapping("/store/storeInfo")
public class StoreInfoController extends BaseController {
    @Autowired
    private IStoreInfoService storeInfoService;

    /**
     * @description：分页查询库存信息
     * @author：justin
     * @date：2020-02-17 09:31
     */
    @PostMapping(value = "/queryStoreInfoList")
    public void queryStoreInfoList(@RequestBody StoreInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TStoreInfo> list = storeInfoService.queryStoreInfoList(info);
                json.setObj(list);
                json.setSuccess(true);
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

    /**
     * @description：根据公司和项目类别获取库存信息
     * @author：justin
     * @date：2020-02-20 10:31
     */
    @PostMapping(value = "/getStoreInfoList")
    public void getStoreInfoList(@RequestBody StoreInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                if (StringUtil.isEmpty(info.getCompanyCode()) || StringUtil.isEmpty(info.getProjectCode())) {
                    json.setSuccess(false);
                    json.setMsg("关键参数【companyCode、projectCode】存在空值！");
                } else {
                    PageInfo<TStoreInfo> list = storeInfoService.queryStoreInfoList(info);
                    json.setObj(list);
                    json.setSuccess(true);
                }
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
