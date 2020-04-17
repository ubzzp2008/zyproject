package com.fl.web.controller.shop;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.shop.TDesk;
import com.fl.web.model.shop.Desk;
import com.fl.web.service.shop.IDeskService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：GoodsController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-16 10:23
 */
@Controller
@RequestMapping("/shop/desk")
public class DeskController extends BaseController {
    @Autowired
    private IDeskService deskService;

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 09:31
     */
    @PostMapping(value = "/queryDeskList")
    public void queryDeskList(@RequestBody Desk info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TDesk> list = deskService.queryDeskList(info);
                json.setSuccess(true);
                json.setObj(list);
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
     * @description：检查编码是否存在
     * @author：justin
     * @date：2019-12-13 10:04
     */
    @GetMapping(value = "/checkDeskCodeExist")
    public void checkDeskCodeExist(@RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            TDesk t = deskService.checkCodeExist(code);
            if (t != null) {
                json.setSuccess(false);
                json.setMsg("此编号已经存在，请更换其他编号！");
            } else {
                json.setSuccess(true);
                json.setMsg("此编号验证通过，可使用");
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
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:43
     */
    @PostMapping(value = "/saveDesk")
    public void saveDesk(@RequestBody TDesk info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getDeskCode()) && StringUtil.isNotEmpty(info.getDeskName())) {
                String code = info.getDeskCode().trim();
                TDesk t = deskService.checkCodeExist(code);
                if (t != null) {
                    json.setSuccess(false);
                    json.setMsg("保存失败！编号：" + code + "已经存在！");
                } else {
                    deskService.saveDesk(info);
                    json.setSuccess(true);
                    json.setMsg("保存成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！编号和名称不能为空！");
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
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:06
     */
    @GetMapping(value = "/getDeskById")
    public void getDeskById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TDesk p = deskService.getDeskById(id);
            json.setSuccess(true);
            json.setObj(p);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改组织信息
     * @author：justin
     * @date：2019-12-13 09:58
     */
    @PostMapping(value = "/updateDesk")
    public void updateDesk(@RequestBody TDesk info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getId())) {
                deskService.updateDesk(info);
                json.setSuccess(true);
                json.setMsg("修改成功");
            } else {
                json.setMsg("操作失败！关键参数【id】为空！");
                json.setSuccess(false);
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
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-13 10:14
     */
    @PostMapping(value = "/deleteDesk")
    public void deleteDesk(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            deskService.deleteDesk(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取所有数据
     * @author：justin
     * @date：2020-04-16 15:08
     */
    @GetMapping(value = "/getUseableDeskList")
    public void getUseableDeskList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TDesk> list = deskService.getUseableDeskList();
            json.setSuccess(true);
            json.setObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }
}
