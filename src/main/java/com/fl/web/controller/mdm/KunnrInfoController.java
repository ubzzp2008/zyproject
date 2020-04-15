package com.fl.web.controller.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TKunnrInfo;
import com.fl.web.model.mdm.KunnrInfo;
import com.fl.web.service.mdm.IKunnrInfoService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：KunnrInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 12:45
 */
@RestController
@RequestMapping("/mdm/kunnr")
public class KunnrInfoController extends BaseController {
    @Autowired
    private IKunnrInfoService kunnrInfoService;

    /**
     * @description：分页查询客户信息
     * @author：justin
     * @date：2019-10-11 12:55
     */
    @PostMapping(value = "/queryKunnrInfoList")
    public void queryKunnrInfoList(@RequestBody KunnrInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TKunnrInfo> list = kunnrInfoService.queryKunnrInfoList(info);
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
     * @description：检查编码是否已经存在
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getKunnrInfoByKunnr")
    public void getKunnrInfoByKunnr(@RequestParam(name = "kunnr") String kunnr, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            kunnr = kunnr.trim();
            TKunnrInfo info = kunnrInfoService.getKunnrInfoByKunnr(kunnr);
            if (info != null) {
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
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getKunnrInfoById")
    public void getKunnrInfoById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TKunnrInfo info = kunnrInfoService.getKunnrInfoById(id);
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
     * @description：保存信息
     * @author：justin
     * @date：2019-10-08 11:31
     */
    @PostMapping(value = "/saveKunnrInfo")
    public void saveKunnrInfo(@RequestBody TKunnrInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String kunnr = info.getKunnr().trim();
            TKunnrInfo k = kunnrInfoService.getKunnrInfoByKunnr(kunnr);
            if (k != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + kunnr + "已经存在！");
            } else {
                kunnrInfoService.saveKunnrInfo(info);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存客户信息异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/updateKunnrInfo")
    public void updateKunnrInfo(@RequestBody TKunnrInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            kunnrInfoService.updateKunnrInfo(info);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改失败", e);
            json.setSuccess(false);
            json.setMsg("修改失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除物料信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/deleteKunnrInfoById")
    public void deleteKunnrInfoById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            kunnrInfoService.deleteKunnrInfoById(id);
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
     * @description：模糊匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:55
     */
    @GetMapping(value = "/searchKunnrInfoList")
    public void searchKunnrInfoList(@RequestParam(name = "info", required = false) String info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TKunnrInfo> list = kunnrInfoService.searchKunnrInfoList("%" + (StringUtil.isEmpty(info) ? "" : info) + "%");
            json.setObj(list);
            json.setSuccess(true);
            json.setMsg("获取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据失败", e);
            json.setSuccess(false);
            json.setMsg("获取数据失败" + e.getMessage());
        }
        writeJson(json, response);
    }
}
