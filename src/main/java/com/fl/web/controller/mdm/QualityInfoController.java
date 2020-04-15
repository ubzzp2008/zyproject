package com.fl.web.controller.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TQualityInfo;
import com.fl.web.model.mdm.QualityInfo;
import com.fl.web.service.mdm.IQualityInfoService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：QualityInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 10:17
 */
@RestController
@RequestMapping("/mdm/qualityInfo")
public class QualityInfoController extends BaseController {
    @Autowired
    private IQualityInfoService qualityInfoService;

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-01-09 10:35
     */
    @PostMapping(value = "/queryQualityInfoList")
    public void queryQualityInfoList(@RequestBody QualityInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TQualityInfo> list = qualityInfoService.queryQualityInfoList(info);
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
     * @description：检查编码是否存在
     * @author：justin
     * @date：2020-01-09 10:43
     */
    @GetMapping(value = "/getQualityInfoByCode")
    public void getQualityInfoByCode(@RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            TQualityInfo m = qualityInfoService.getQualityInfoByCode(code);
            if (m != null) {
                json.setSuccess(false);
                json.setMsg("此编码已经被使用，请更换编码！");
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
     * @description：保存
     * @author：justin
     * @date：2020-01-09 10:38
     */
    @PostMapping(value = "/saveQualityInfo")
    public void saveQualityInfo(@RequestBody TQualityInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String code = info.getCode().trim();
            TQualityInfo m = qualityInfoService.getQualityInfoByCode(code);
            if (m != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + code + "已经存在！");
            } else {
                qualityInfoService.saveQualityInfo(info);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2020-01-09 10:44
     */
    @GetMapping(value = "/getQualityInfoById")
    public void getQualityInfoById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TQualityInfo info = qualityInfoService.getQualityInfoById(id);
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
     * @description：修改
     * @author：justin
     * @date：2020-01-09 10:38
     */
    @PostMapping(value = "/updateQualityInfo")
    public void updateQualityInfo(@RequestBody TQualityInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getId())) {
                qualityInfoService.updateQualityInfo(info);
                json.setSuccess(true);
                json.setMsg("修改成功");
            } else {
                json.setSuccess(false);
                json.setMsg("修改失败！关键参数【id】为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常：", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除
     * @author：justin
     * @date：2020-01-09 10:40
     */
    @PostMapping(value = "/deleteQualityInfoById")
    public void deleteQualityInfoById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            qualityInfoService.deleteQualityInfoById(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常！", e);
            json.setSuccess(false);
            json.setMsg("系统异常！" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取所有物料大类
     * @author：justin
     * @date：2019-10-10 20:25
     */
    @GetMapping(value = "/getAllQualityInfo")
    public void getAllQualityInfo(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TQualityInfo> list = qualityInfoService.getAllQualityInfo();
            json.setSuccess(true);
            json.setObj(list);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

}
