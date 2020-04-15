package com.fl.web.controller.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TDeviceInfo;
import com.fl.web.model.mdm.DeviceInfo;
import com.fl.web.service.mdm.IDeviceInfoService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DeviceInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 16:13
 */
@RestController
@RequestMapping("/mdm/device")
public class DeviceInfoController extends BaseController {
    @Autowired
    private IDeviceInfoService deviceInfoService;

    /**
     * @description：分页查询客户信息
     * @author：justin
     * @date：2019-10-11 12:55
     */
    @PostMapping(value = "/queryDeviceInfoList")
    public void queryDeviceInfoList(@RequestBody DeviceInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TDeviceInfo> list = deviceInfoService.queryDeviceInfoList(info);
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
    @GetMapping(value = "/getDeviceInfoBySN")
    public void getDeviceInfoBySN(@RequestParam(name = "deviceSN") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            TDeviceInfo info = deviceInfoService.getDeviceInfoBySN(code);
            if (info != null) {
                json.setSuccess(false);
                json.setMsg("此SN号已经被使用，请更换编码！");
            } else {
                json.setSuccess(true);
                json.setMsg("此SN号验证通过，可使用");
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
    @GetMapping(value = "/getDeviceInfoById")
    public void getDeviceInfoById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TDeviceInfo info = deviceInfoService.getDeviceInfoById(id);
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
    @PostMapping(value = "/saveDeviceInfo")
    public void saveDeviceInfo(@RequestBody TDeviceInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String code = info.getDeviceSN().trim();
            TDeviceInfo k = deviceInfoService.getDeviceInfoBySN(code);
            if (k != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！SN号：" + code + "已经存在！");
            } else {
                deviceInfoService.saveDeviceInfo(info);
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
     * @description：修改信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/updateDeviceInfo")
    public void updateDeviceInfo(@RequestBody TDeviceInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            deviceInfoService.updateDeviceInfo(info);
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
     * @description：删除
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/deleteDeviceInfoById")
    public void deleteDeviceInfoById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            deviceInfoService.deleteDeviceInfoById(id);
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
     * @description：获取所有设备列表
     * @author：justin
     * @date：2019-12-02 14:20
     */
    @GetMapping(value = "/findAllDeviceInfoList")
    public void findAllDeviceInfoList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDeviceInfo> list = deviceInfoService.findAllDeviceInfoList();
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

    /**
     * @description：模糊匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:55
     */
    @GetMapping(value = "/searchDeviceInfoList")
    public void searchDeviceInfoList(@RequestParam(name = "info", required = false) String info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDeviceInfo> list = deviceInfoService.searchDeviceInfoList("%" + (StringUtil.isEmpty(info) ? "" : info) + "%");
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
