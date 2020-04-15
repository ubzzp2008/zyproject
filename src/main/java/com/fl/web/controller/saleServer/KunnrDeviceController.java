package com.fl.web.controller.saleServer;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.saleServer.TKunnrDevice;
import com.fl.web.model.saleServer.KunnrDevice;
import com.fl.web.service.saleServer.IKunnrDeviceService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：KunnrDeviceController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 08:49
 */
@RestController
@RequestMapping("/saleServer/kunnrDevice")
public class KunnrDeviceController extends BaseController {
    @Autowired
    private IKunnrDeviceService kunnrDeviceService;

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-14 09:35
     */
    @PostMapping(value = "/queryKunnrDeviceList")
    public void queryKunnrDeviceList(@RequestBody KunnrDevice info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TKunnrDevice> list = kunnrDeviceService.queryKunnrDeviceList(info);
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
     * @description：根据客户编码和设备编号验证数据是否存在
     * @author：justin
     * @date：2019-10-14 10:56
     */
//    @GetMapping(value = "/checkDataExist")
//    public void checkDataExist(@RequestParam(name = "kunnr") String kunnr,
//                               @RequestParam(name = "deviceCode") String code, HttpServletResponse response) {
//        AjaxJson json = new AjaxJson();
//        try {
//            if (StringUtil.isEmpty(kunnr) || StringUtil.isEmpty(code)) {
//                json.setMsg("操作失败！参数【kunnr】或【deviceCode】为空！");
//                json.setSuccess(false);
//            } else {
//                TKunnrDevice kd = kunnrDeviceService.checkDataExist(kunnr, code);
//                if (kd != null) {
//                    json.setSuccess(false);
//                    json.setMsg("此客户与设备已经绑定，无需再次添加！");
//                } else {
//                    json.setMsg("验证成功！");
//                    json.setSuccess(true);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            json.setSuccess(false);
//            logger.error("获取数据异常", e);
//            json.setMsg("获取数据异常" + e.getMessage());
//        }
//        writeJson(json, response);
//    }

    /**
     * @description：保存
     * @author：justin
     * @date：2019-10-14 10:34
     */
    @PostMapping(value = "/saveKunnrDevice")
    public void saveKunnrDevice(@RequestBody TKunnrDevice info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //根据客户编码和设备编码验证数据是否存在
            TKunnrDevice kd = kunnrDeviceService.checkDataExist(info.getKunnr(), info.getDeviceSN());
            if (kd != null) {
                json.setSuccess(false);
                json.setMsg("此客户与设备已经绑定，无需添加！");
            } else {
                kunnrDeviceService.saveKunnrDevice(info);
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
    @PostMapping(value = "/deleteKunnrDeviceById")
    public void deleteKunnrDeviceById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            kunnrDeviceService.deleteKunnrDeviceById(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("删除客户设备关系异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：启用/禁用
     * @author：justin
     * @date：2020-01-15 17:27
     */
    @PostMapping(value = "/updateKunnrDeviceFlag")
    public void updateKunnrDeviceFlag(@RequestParam(name = "id") String id, @RequestParam(name = "flag") String flag, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            kunnrDeviceService.updateKunnrDeviceFlag(id, flag);
            json.setSuccess(true);
            json.setMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：模糊匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:55
     */
    @GetMapping(value = "/searchDeviceListByKunnr")
    public void searchDeviceListByKunnr(@RequestParam(name = "info", required = false) String info,
                                        @RequestParam(name = "kunnr") String kunnr, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TKunnrDevice> list = kunnrDeviceService.searchDeviceListByKunnr("%" + (StringUtil.isEmpty(info) ? "" : info) + "%", kunnr);
            json.setObj(list);
            json.setSuccess(true);
            json.setMsg("获取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据客户编码获取设备信息
     * @author：justin
     * @date：2019-12-02 15:19
     */
    @GetMapping(value = "/findDeviceListByKunnr")
    public void findDeviceListByKunnr(@RequestParam(name = "kunnr") String kunnr, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TKunnrDevice> list = kunnrDeviceService.findDeviceListByKunnr(kunnr);
            json.setObj(list);
            json.setSuccess(true);
            json.setMsg("获取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

}
