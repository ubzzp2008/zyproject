package com.fl.web.controller.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TMaraType;
import com.fl.web.model.mdm.MaraType;
import com.fl.web.service.mdm.IMaraTypeService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MaktlInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 15:08
 */
@RestController
@RequestMapping("/mdm/maraType")
public class MaraTypeController extends BaseController {
    @Autowired
    private IMaraTypeService maraTypeService;

    /**
     * @description：分页查询物料大类数据
     * @author：justin
     * @date：2019-10-08 11:34
     */
    @PostMapping(value = "/queryMaraTypeList")
    public void queryMaraTypeList(@RequestBody MaraType maraType, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(maraType.getPageNum(), maraType.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TMaraType> list = maraTypeService.queryMaraTypeList(maraType);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取物料大类异常", e);
            json.setMsg("获取物料大类异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：检查物料大类编码是否已经存在
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getMaraTypeByMaktl")
    public void getMaraTypeByMaktl(@RequestParam(name = "maktl") String maktl, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            maktl = maktl.trim();
            TMaraType m = maraTypeService.getMaraTypeByMaktl(maktl);
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
     * @description：根据id获取物料信息
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getMaraTypeById")
    public void getMaraTypeById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TMaraType maraType = maraTypeService.getMaraTypeById(id);
            json.setSuccess(true);
            json.setObj(maraType);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }


    /**
     * @description：保存物料信息
     * @author：justin
     * @date：2019-10-08 11:31
     */
    @PostMapping(value = "/saveMaraType")
    public void saveMaraType(@RequestBody TMaraType maraType, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String maktl = maraType.getMaktl().trim();
            TMaraType m = maraTypeService.getMaraTypeByMaktl(maktl);
            if (m != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + maktl + "已经存在！");
            } else {
                maraTypeService.saveMaraType(maraType);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存物料异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改物料信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/updateMaraType")
    public void updateMaraType(@RequestBody TMaraType maraType, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            maraTypeService.updateMaraType(maraType);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改物料大类失败", e);
            json.setSuccess(false);
            json.setMsg("修改物料大类失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除物料信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/deleteMaraTypeById")
    public void deleteMaraTypeById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            maraTypeService.deleteMaraTypeById(id);
            json.setSuccess(true);
            json.setMsg("物料大类删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("物料大类删除失败", e);
            json.setSuccess(false);
            json.setMsg("物料大类删除失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取所有物料大类
     * @author：justin
     * @date：2019-10-10 20:25
     */
    @GetMapping(value = "/getAllMaraType")
    public void getAllMaraType(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TMaraType> list = maraTypeService.getAllMaraType();
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
