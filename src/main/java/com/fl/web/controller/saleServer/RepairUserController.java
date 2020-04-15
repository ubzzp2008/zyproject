package com.fl.web.controller.saleServer;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.saleServer.TRepairUser;
import com.fl.web.model.saleServer.RepairUser;
import com.fl.web.service.saleServer.IRepairUserService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RepairUserController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 15:57
 */
@RestController
@RequestMapping("/saleServer/repairUser")
public class RepairUserController extends BaseController {
    @Autowired
    private IRepairUserService repairUserService;

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-14 16:16
     */
    @PostMapping(value = "/queryRepairUserList")
    public void queryRepairUserList(@RequestBody RepairUser info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TRepairUser> list = repairUserService.queryRepairUserList(info);
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
     * @description：保存数据
     * @author：justin
     * @date：2019-10-14 16:17
     */
    @PostMapping(value = "/saveRepairUser")
    public void saveRepairUser(@RequestBody TRepairUser info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String userName = info.getUserName();
            //根据用户名检查数据是否存在
            TRepairUser reUser = repairUserService.findRepairUserByUserName(userName);
            if (reUser != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！此用户配置已经存在！");
            } else {
                repairUserService.saveRepairUser(info);
                json.setMsg("保存成功");
                json.setSuccess(true);
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
     * @description：删除
     * @author：justin
     * @date：2019-10-14 10:35
     */
    @PostMapping(value = "/deleteRepairUserById")
    public void deleteRepairUserById(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                repairUserService.deleteRepairUserById(idList);
                json.setSuccess(true);
                json.setMsg("删除成功");
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("删除客户设备关系异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取所有维修员
     * @author：justin
     * @date：2019-10-24 16:06
     */
    @GetMapping(value = "/findAllRepairUserList")
    public void findAllRepairUserList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TRepairUser> list = repairUserService.findAllRepairUserList();
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }
}
