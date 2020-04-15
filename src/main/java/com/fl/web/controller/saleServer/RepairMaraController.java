package com.fl.web.controller.saleServer;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.saleServer.TRepairMara;
import com.fl.web.model.saleServer.RepairMara;
import com.fl.web.service.saleServer.IRepairMaraService;
import com.fl.web.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RepairMaraController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-31 13:46
 */
@RestController
@RequestMapping("/saleServer/repairMara")
public class RepairMaraController extends BaseController {
    @Autowired
    private IRepairMaraService repairMaraService;

    /**
     * @description：查询坏件待邮寄物料信息
     * @author：justin
     * @date：2019-10-31 13:49
     */
    @PostMapping(value = "/waitExpressOldMaraList")
    public void waitExpressOldMaraList(@RequestBody RepairMara info, @RequestParam("posCode") String posCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                logger.info("岗位编码{}，权限值为{}", posCode, AuthorEnum.getVal(posCode));
                if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHONLY)) {
                    info.setRepairBy(SessionUser.getUserName());
                }
                info.setState(StaticParam.BJS05);//坏件待邮寄
                info.setMaraFlag(StaticParam.MF_OLD);//退坏流程
                PageInfo<TRepairMara> list = repairMaraService.queryRepairMaraList(info);
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
     * @description：保存邮寄信息
     * @author：justin
     * @date：2019-11-01 10:16
     */
    @PostMapping(value = "/saveExpressMara")
    public void saveExpressMara(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = checkExpressInfo(param);
            if (StringUtil.isEmpty(msg)) {
                String ids = param.get("ids").toString();
                List<String> idList = JSONArray.parseArray(ids, String.class);
                param.put("idList", idList);
                param.put("state", StaticParam.BJS30);
                param.put("receiveMsgUser", SessionUser.getUser().getEmpCode());
                repairMaraService.updateRepairMara(param);
                json.setSuccess(true);
                json.setMsg("保存成功！");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("保存邮寄信息异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：检查字段是否为空
     * @author：justin
     * @date：2019-11-01 10:39
     */
    private String checkExpressInfo(Map<String, Object> param) {
        if (param.get("ids") == null || StringUtil.isEmpty(param.get("ids").toString())) {
            return "参数【ids】为空！";
        }
        if (param.get("expressWay") == null || StringUtil.isEmpty(param.get("expressWay").toString())) {
            return "参数【expressWay】为空！";
        }
        if (param.get("expressNum") == null || StringUtil.isEmpty(param.get("expressNum").toString())) {
            return "参数【expressNum】为空！";
        }
        if (param.get("receiver") == null || StringUtil.isEmpty(param.get("receiver").toString())) {
            return "参数【receiver】为空！";
        }
        if (param.get("address") == null || StringUtil.isEmpty(param.get("address").toString())) {
            return "参数【address】为空！";
        }
        if (param.get("sender") == null || StringUtil.isEmpty(param.get("sender").toString())) {
            return "参数【sender】为空！";
        }
        return null;
    }

    /**
     * @description：分页查询坏件待收货列表
     * @author：justin
     * @date：2019-11-01 11:10
     */
    @PostMapping(value = "/waitReceiveOldMaraList")
    public void waitReceiveOldMaraList(@RequestBody RepairMara info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.BJS30);//待收货
                info.setMaraFlag(StaticParam.MF_OLD);//退坏流程
                PageInfo<TRepairMara> list = repairMaraService.queryRepairMaraList(info);
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
     * @date：2019-11-01 11:16
     */
    @PostMapping(value = "/saveReceiveMara")
    public void saveReceiveMara(@RequestBody List<TRepairMara> maraList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(maraList)) {
                repairMaraService.saveReceiveMara(maraList);
                json.setSuccess(true);
                json.setMsg("保存成功！");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！参数对象为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("确认收货异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询待部门领导审批列表
     * @author：justin
     * @date：2019-11-01 11:26
     */
    @PostMapping(value = "/waitDeptApplyMaraList")
    public void waitDeptApplyMaraList(@RequestBody RepairMara info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.BJS10);//待部门领导审批
                info.setMaraFlag(StaticParam.MF_NEW);//换新流程
                PageInfo<TRepairMara> list = repairMaraService.queryRepairMaraList(info);
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
     * @description：部门领导审批
     * @author：justin
     * @date：2019-11-01 11:32
     */
    @PostMapping(value = "/deptApplyMara")
    public void deptApplyMara(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Map<String, Object> param = new HashMap<>();
                param.put("idList", idList);
                param.put("state", StaticParam.BJS20);//待商务部审批
                repairMaraService.updateRepairMara(param);
                json.setSuccess(true);
                json.setMsg("保存成功！");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！参数【idList】为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("部门领导审批异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询待商务部审批列表
     * @author：justin
     * @date：2019-11-01 11:28
     */
    @PostMapping(value = "/waitCommApplyMaraList")
    public void waitCommApplyMaraList(@RequestBody RepairMara info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.BJS20);//待商务部领导审批
                info.setMaraFlag(StaticParam.MF_NEW);//换新流程
                PageInfo<TRepairMara> list = repairMaraService.queryRepairMaraList(info);
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
     * @description：商务部审批
     * @author：justin
     * @date：2019-11-01 11:34
     */
    @PostMapping(value = "/commApplyMara")
    public void commApplyMara(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Map<String, Object> param = new HashMap<>();
                param.put("idList", idList);
                param.put("state", StaticParam.BJS05);//待邮寄
                repairMaraService.updateRepairMara(param);
                json.setSuccess(true);
                json.setMsg("保存成功！");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！参数【idList】为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("商务部领导审批异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询新件待邮寄列表
     * @author：justin
     * @date：2019-10-31 13:57
     */
    @PostMapping(value = "/waitExpressNewMaraList")
    public void waitExpressNewMaraList(@RequestBody RepairMara info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.BJS05);//待邮寄
                info.setMaraFlag(StaticParam.MF_NEW);//换新流程
                PageInfo<TRepairMara> list = repairMaraService.queryRepairMaraList(info);
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
     * @description：分页查询新件待收货列表
     * @author：justin
     * @date：2019-11-01 11:11
     */
    @PostMapping(value = "/waitReceiveNewMaraList")
    public void waitReceiveNewMaraList(@RequestBody RepairMara info, @RequestParam("posCode") String posCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                logger.info("岗位编码{}，权限值为{}", posCode, AuthorEnum.getVal(posCode));
                if (StringUtil.equals(AuthorEnum.getVal(posCode), StaticParam.AUTHONLY)) {
                    info.setRepairBy(SessionUser.getUserName());
                }
                info.setState(StaticParam.BJS30);//待收货
                info.setMaraFlag(StaticParam.MF_NEW);//换新流程
                PageInfo<TRepairMara> list = repairMaraService.queryRepairMaraList(info);
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
