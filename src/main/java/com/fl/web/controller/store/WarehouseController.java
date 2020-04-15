package com.fl.web.controller.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.store.TWarehouse;
import com.fl.web.entity.store.TWarehousePos;
import com.fl.web.model.store.Warehouse;
import com.fl.web.service.store.IWarehousePosService;
import com.fl.web.service.store.IWarehouseService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：WarehouseController
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:33
 */
@RestController
@RequestMapping("/store/warehouse")
public class WarehouseController extends BaseController {
    @Autowired
    private IWarehouseService warehouseService;
    @Autowired
    private IWarehousePosService warehousePosService;

    /**
     * @desc：分页查询仓位信息
     * @author：justin
     * @date：2020/2/5 11:44
     */
    @PostMapping(value = "/queryWarehouseList")
    public void queryWarehouseList(@RequestBody Warehouse info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TWarehouse> list = warehouseService.queryWarehouseList(info);
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
    @GetMapping(value = "/getWarehouseByCode")
    public void getWarehouseByCode(@RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            TWarehouse m = warehouseService.getWarehouseByCode(code);
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
    @GetMapping(value = "/getWarehouseById")
    public void getWarehouseById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TWarehouse warehouse = warehouseService.getWarehouseById(id);
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
    @PostMapping(value = "/saveWarehouse")
    public void saveWarehouse(@RequestBody TWarehouse warehouse, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String code = warehouse.getCode().trim();
            TWarehouse t = warehouseService.getWarehouseByCode(code);
            if (t != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + code + "已经存在！");
            } else {
                warehouseService.saveWarehouse(warehouse);
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
    @PostMapping(value = "/updateWarehouse")
    public void updateWarehouse(@RequestBody TWarehouse warehouse, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(warehouse.getId())) {
                warehouseService.updateWarehouse(warehouse);
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
    @PostMapping(value = "/deleteWarehouseById")
    public void deleteWarehouseById(@RequestParam(name = "id") String id, @RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TWarehousePos> wpList = warehousePosService.getWarehousePosByWareCode(code);
            if (CollectionUtils.isNotEmpty(wpList)) {
                json.setSuccess(false);
                json.setMsg("此仓库下维护有仓位，禁止删除");
            } else {
                warehouseService.deleteWarehouseById(id);
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
     * @desc：获取所有仓位信息
     * @author：justin
     * @date：2020/2/5 11:54
     */
    @GetMapping(value = "/getWarehouseByCompany")
    public void getWarehouseByCompany(@RequestParam(name = "companyCode") String companyCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TWarehouse> list = warehouseService.getWarehouseByCompany(companyCode);
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
