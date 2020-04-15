package com.fl.web.controller.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TDataDicType;
import com.fl.web.entity.mdm.TDataDictionary;
import com.fl.web.model.mdm.DataDictionary;
import com.fl.web.service.mdm.IDataDicTypeService;
import com.fl.web.service.mdm.IDataDictionaryService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DataDictionaryController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 11:41
 */
@RestController
@RequestMapping("/mdm/dataDic")
public class DataDictionaryController extends BaseController {
    @Autowired
    private IDataDictionaryService dataDictionaryService;
    @Autowired
    private IDataDicTypeService dataDicTypeService;

    /**
     * @description：获取所有数据字典类型
     * @author：justin
     * @date：2019-11-21 15:09
     */
    @GetMapping(value = "/getAllDicType")
    public void getAllDicType(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDicType> list = dataDicTypeService.getAllDicType();
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询所有数据字典数据
     * @author：justin
     * @date：2019-11-21 15:12
     */
    @PostMapping(value = "/queryDataDicList")
    public void queryDataDicList(@RequestBody DataDictionary info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TDataDictionary> list = dataDictionaryService.queryDataDicList(info);
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
     * @description：根据组编码和编码验证数据是否存在
     * @author：justin
     * @date：2019-11-21 15:19
     */
    @GetMapping(value = "/getDataDictionaryByCode")
    public void getDataDictionaryByCode(@RequestParam(name = "groupCode") String groupCode, @RequestParam(name = "code") String code,
                                        HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            Integer count = dataDictionaryService.getDataDictionaryByCode(groupCode, code);
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("此编码已经存在，请更换编码！");
            } else {
                json.setSuccess(true);
                json.setMsg("此编码验证通过，可使用");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取数据字典信息
     * @author：justin
     * @date：2019-11-21 15:24
     */
    @GetMapping(value = "/getDataDictionaryById")
    public void getDataDictionaryById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TDataDictionary info = dataDictionaryService.getDataDictionaryById(id);
            json.setSuccess(true);
            json.setObj(info);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-11-21 15:25
     */
    @PostMapping(value = "/saveDataDictionary")
    public void saveDataDictionary(@RequestBody TDataDictionary info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String code = info.getCode().trim();
            Integer count = dataDictionaryService.getDataDictionaryByCode(info.getGroupCode(), code);
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + code + "已经存在！");
            } else {
                dataDictionaryService.saveDataDictionary(info);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-11-21 15:27
     */
    @PostMapping(value = "/updateDataDictionary")
    public void updateDataDictionary(@RequestBody TDataDictionary info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(info.getId())) {
                json.setSuccess(false);
                json.setMsg("操作失败！参数【id】为空！");
            } else {
                dataDictionaryService.updateDataDictionary(info);
                json.setSuccess(true);
                json.setMsg("修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改失败", e);
            json.setSuccess(false);
            json.setMsg("修改失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-11-21 15:29
     */
    @PostMapping(value = "/deleteDataDictionaryById")
    public void deleteDataDictionaryById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            dataDictionaryService.deleteDataDictionaryById(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除失败", e);
            json.setSuccess(false);
            json.setMsg("删除失败" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：获取装机方式字典
     * @author：justin
     * @date：2019-10-14 11:47
     */
    @GetMapping(value = "/queryDicZJFS")
    public void queryDicZJFS(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCZJFS);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取维修单状态字典
     * @author：justin
     * @date：2019-10-15 15:14
     */
    @GetMapping(value = "/queryDicROS")
    public void queryDicROS(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCROS);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取维修方式数据字典
     * @author：justin
     * @date：2019-10-28 10:00
     */
    @GetMapping(value = "/queryDicDealType")
    public void queryDicDealType(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCDT);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取维修部件状态数据字典
     * @author：justin
     * @date：2019-11-01 10:23
     */
    @GetMapping(value = "/queryDicBJS")
    public void queryDicBJS(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCBJS);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取快递方式数据字典
     * @author：justin
     * @date：2019-11-01 10:19
     */
    @GetMapping(value = "/queryDicExpress")
    public void queryDicExpress(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCEXP);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取单位数据字典
     * @author：justin
     * @date：2019-11-15 16:54
     */
    @GetMapping(value = "/queryDicUnit")
    public void queryDicUnit(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCUNIT);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取图标数据字典
     * @author：justin
     * @date：2019-11-21 14:55
     */
    @GetMapping(value = "/queryDicIcon")
    public void queryDicIcon(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCICON);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取采购订单单据类型数据字典
     * @author：justin
     * @date：2019-12-18 14:57
     */
    @GetMapping(value = "/queryDicOrdType")
    public void queryDicOrdType(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCORDTYPE);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取采购申请单单据状态
     * @author：justin
     * @date：2019-12-23 14:52
     */
    @GetMapping(value = "/queryDicHcReq")
    public void queryDicHcReq(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCREQ);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取采购订单单据状态
     * @author：justin
     * @date：2019-12-23 14:53
     */
    @GetMapping(value = "/queryDicHcOrd")
    public void queryDicHcOrd(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDataDictionary> list = dataDictionaryService.findDataDictionaryByGroup(StaticParam.HCORD);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取数据异常", e);
            json.setMsg("获取数据异常" + e.getMessage());
        }
        writeJson(json, response);
    }
}
