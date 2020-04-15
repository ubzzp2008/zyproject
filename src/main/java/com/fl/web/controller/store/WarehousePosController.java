package com.fl.web.controller.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.store.TWarehousePos;
import com.fl.web.model.store.WarehousePos;
import com.fl.web.service.store.IStoreOrderService;
import com.fl.web.service.store.IWarehousePosService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：WarehousePosController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 10:50
 */
@RestController
@RequestMapping("/store/warehousePos")
public class WarehousePosController extends BaseController {
    @Autowired
    private IWarehousePosService warehousePosService;
    @Autowired
    private IStoreOrderService storeOrderService;

    /**
     * @desc：分页查询仓位信息
     * @author：justin
     * @date：2020/2/5 11:44
     */
    @PostMapping(value = "/queryWarehousePosList")
    public void queryWarehousePosList(@RequestBody WarehousePos info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TWarehousePos> list = warehousePosService.queryWarehousePosList(info);
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
     * @desc：检查编码是否已经存在
     * @author：justin
     * @date：2020/2/5 11:45
     */
    @GetMapping(value = "/getWarehousePosByCode")
    public void getWarehousePosByCode(@RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            TWarehousePos m = warehousePosService.getWarehousePosByCode(code);
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
     * @desc：根据id获取信息
     * @author：justin
     * @date：2020/2/5 11:47
     */
    @GetMapping(value = "/getWarehousePosById")
    public void getWarehousePosById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TWarehousePos warehouse = warehousePosService.getWarehousePosById(id);
            json.setSuccess(true);
            json.setObj(warehouse);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }


    /**
     * @desc：保存信息
     * @author：justin
     * @date：2020/2/5 11:49
     */
    @PostMapping(value = "/saveWarehousePos")
    public void saveWarehousePos(@RequestBody TWarehousePos warehouse, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String code = warehouse.getCode().trim();
            TWarehousePos t = warehousePosService.getWarehousePosByCode(code);
            if (t != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + code + "已经存在！");
            } else {
                warehousePosService.saveWarehousePos(warehouse);
                json.setSuccess(true);
                json.setMsg("保存成功");
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
     * @desc：
     * @author：justin
     * @date：修改 11:53
     */
    @PostMapping(value = "/updateWarehousePos")
    public void updateWarehousePos(@RequestBody TWarehousePos warehouse, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(warehouse.getId())) {
                warehousePosService.updateWarehousePos(warehouse);
                json.setSuccess(true);
                json.setMsg("修改成功");
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！关键参数【id】为空！");
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
     * @desc：删除
     * @author：justin
     * @date：2020/2/5 11:52
     */
    @PostMapping(value = "/deleteWarehousePosById")
    public void deleteWarehousePosById(@RequestParam(name = "id") String id, @RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //todo 需要验证仓位是否已经被使用，及仓位中是否存在物料
            //检查出入库单据表是否使用仓位
            Integer count = storeOrderService.findStoreOrderByWarePosCode(code);
            if (count > 0) {
                json.setSuccess(false);
                json.setMsg("此仓位已使用，禁止删除！");
            } else {
                warehousePosService.deleteWarehousePosById(id);
                json.setSuccess(true);
                json.setMsg("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @desc：根据仓库编码和项目类别获取仓位信息
     * @author：justin
     * @date：2020/2/5 11:54
     */
    @GetMapping(value = "/getWarehousePosList")
    public void getWarehousePosList(@RequestParam(name = "wareCode") String wareCode, @RequestParam(name = "projectCode") String projectCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TWarehousePos> list = warehousePosService.getWarehousePosList(wareCode, projectCode);
            json.setSuccess(true);
            json.setObj(list);
            json.setMsg("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @desc：获取所有仓位信息
     * @author：justin
     * @date：2020/2/5 11:54
     */
    @GetMapping(value = "/getWarehousePosByWareCode")
    public void getWarehousePosByWareCode(@RequestParam(name = "wareCode") String wareCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TWarehousePos> list = warehousePosService.getWarehousePosByWareCode(wareCode);
            json.setSuccess(true);
            json.setObj(list);
            json.setMsg("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }
}
