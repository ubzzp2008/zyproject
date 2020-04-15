package com.fl.web.controller.qm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.qm.TMaraCensor;
import com.fl.web.model.qm.MaraCensor;
import com.fl.web.service.qm.IMaraCensorService;
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
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MaraCensorController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:55
 */
@RestController
@RequestMapping("/qm/maraCensor")
public class MaraCensorController extends BaseController {
    @Autowired
    private IMaraCensorService maraCensorService;

    /**
     * @description：分页查询待质检收货
     * @author：justin
     * @date：2020-01-09 17:57
     */
    @PostMapping(value = "/waitQmReceiveList")
    public void waitQmReceiveList(@RequestBody MaraCensor info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.SJ_0);
                PageInfo<TMaraCensor> list = maraCensorService.queryMaraCensorList(info);
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
     * @description：质检确认收货
     * @author：justin
     * @date：2020-01-10 13:26
     */
    @PostMapping(value = "/qmConfirmReceiveCensor")
    public void qmConfirmReceiveCensor(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                //检查数据状态是否正确
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_0);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.qmConfirmReceiveCensor(idList, StaticParam.SJ_10);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【idList】存在空值！");
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
     * @description：质检部拒绝收货
     * @author：justin
     * @date：2020-01-10 13:44
     */
    @PostMapping(value = "/qmRejectReceiveCensor")
    public void qmRejectReceiveCensor(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<String> idList = (List<String>) param.get("idList");
            String reason = (String) param.get("reason");
            if (CollectionUtils.isNotEmpty(idList) && StringUtil.isNotEmpty(reason)) {
                //检查数据状态是否正确
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_0);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.qmRejectReceiveCensor(idList, reason);
                    json.setSuccess(true);
                    json.setMsg("退回成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【idList或reason】存在空值！");
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
     * @description：分页查询待录入质检结果数据
     * @author：justin
     * @date：2020-01-10 11:16
     */
    @PostMapping(value = "/waitQmInspectList")
    public void waitQmInspectList(@RequestBody MaraCensor info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.SJ_10);
                PageInfo<TMaraCensor> list = maraCensorService.queryMaraCensorList(info);
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
     * @description：保存质检结果
     * @author：justin
     * @date：2020-01-10 14:00
     */
    @PostMapping(value = "/saveInspectResult")
    public void saveInspectResult(@RequestBody List<TMaraCensor> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                //检查数据状态是否正确
                List<String> idList = new ArrayList<>();
                String msg = "";
                for (TMaraCensor censor : infoList) {
                    if (censor.getNum().compareTo(censor.getQualifiedNum().add(censor.getUnqualifiedNum())) != 0) { //-1小于，0等于，1大于
                        msg += "单号" + censor.getOrderNo() + "的物料" + censor.getMatnr() + "送检数量 != 合格数量 + 不合格数量；";
                    }
                    idList.add(censor.getId());
                }
                if (StringUtil.isNotEmpty(msg)) {
                    json.setSuccess(false);
                    json.setMsg(msg);
                } else {
                    Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_10);
                    if (count > 0) {
                        json.setSuccess(false);
                        json.setMsg("数据状态不正确！请刷新页面重试！");
                    } else {
                        maraCensorService.saveInspectResult(infoList);
                        json.setSuccess(true);
                        json.setMsg("操作成功");
                    }
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
     * @description：待质检时退回操作
     * @author：justin
     * @date：2020-01-10 14:25
     */
    @PostMapping(value = "/backToReceiveCensor")
    public void backToReceiveCensor(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_10);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.updateMaraCensor(idList, StaticParam.SJ_0);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
     * @description：分页查询不合格数据
     * @author：justin
     * @date：2020-01-21 10:35
     */
    @PostMapping(value = "/queryUnQualifiedList")
    public void queryUnQualifiedList(@RequestBody MaraCensor info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.SJ_15);
                PageInfo<TMaraCensor> list = maraCensorService.queryMaraCensorList(info);
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
     * @description：质检不合格确认通过
     * @author：justin
     * @date：2020-01-21 10:38
     */
    @PostMapping(value = "/comfirmUnQualified")
    public void comfirmUnQualified(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_15);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.updateMaraCensor(idList, StaticParam.SJ_20);//待创建质检单
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
     * @description：质检不合格确认退回
     * @author：justin
     * @date：2020-01-21 10:38
     */
    @PostMapping(value = "/backUnQualified")
    public void backUnQualified(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_15);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.updateMaraCensor(idList, StaticParam.SJ_10);//退回至检测结果录入
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
     * @description：分页查询待创建质检单
     * @author：justin
     * @date：2020-01-10 11:16
     */
    @PostMapping(value = "/waitQmAddCensorShipList")
    public void waitQmAddCensorShipList(@RequestBody MaraCensor info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.SJ_20);
                PageInfo<TMaraCensor> list = maraCensorService.queryMaraCensorList(info);
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
     * @description：创建质检单
     * @author：justin
     * @date：2020-01-10 14:35
     */
    @PostMapping(value = "/saveCensorShip")
    public void saveCensorShip(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_20);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.saveCensorShip(idList);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
     * @description：创建质检单时退回至结果录入
     * @author：justin
     * @date：2020-01-10 14:32
     */
    @PostMapping(value = "/backToInspectCensor")
    public void backToInspectCensor(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_20);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.updateMaraCensor(idList, StaticParam.SJ_10);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
     * @description：送检跟踪报表
     * @author：justin
     * @date：2020-01-10 11:24
     */
    @PostMapping(value = "/queryMaraCensorList")
    public void queryMaraCensorList(@RequestBody MaraCensor info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TMaraCensor> list = maraCensorService.queryMaraCensorList(info);
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
     * @description：分页查询质检完成待转入库的数据
     * @author：justin
     * @date：2020-01-09 17:57
     */
    @PostMapping(value = "/waitQMToStoreList")
    public void waitQMToStoreList(@RequestBody MaraCensor info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                info.setState(StaticParam.SJ_30);
                PageInfo<TMaraCensor> list = maraCensorService.queryMaraCensorList(info);
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
     * @desc：确认转给仓库
     * @author：justin
     * @date：2020/2/5 16:15
     */
    @PostMapping(value = "/comfirmToStore")
    public void comfirmToStore(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                Integer count = maraCensorService.checkCensorStateBatch(idList, StaticParam.SJ_30);
                if (count > 0) {
                    json.setSuccess(false);
                    json.setMsg("数据状态不正确！请刷新页面重试！");
                } else {
                    maraCensorService.comfirmToStore(idList, StaticParam.SJ_50);
                    json.setSuccess(true);
                    json.setMsg("操作成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数为空！");
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
