package com.fl.web.controller.shop;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.shop.TGoods;
import com.fl.web.model.shop.Goods;
import com.fl.web.service.shop.IGoodsService;
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
@RequestMapping("/shop/goods")
public class GoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 09:31
     */
    @PostMapping(value = "/queryGoodsList")
    public void queryGoodsList(@RequestBody Goods info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TGoods> list = goodsService.queryGoodsList(info);
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
    @GetMapping(value = "/checkCodeExist")
    public void checkCodeExist(@RequestParam(name = "code") String code, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            code = code.trim();
            TGoods t = goodsService.checkCodeExist(code);
            if (t != null) {
                json.setSuccess(false);
                json.setMsg("此编码已经存在，请更换商品编码！");
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
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:43
     */
    @PostMapping(value = "/saveGoods")
    public void saveGoods(@RequestBody TGoods info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getGoodsCode()) && StringUtil.isNotEmpty(info.getGoodsName())) {
                String code = info.getGoodsCode().trim();
                TGoods t = goodsService.checkCodeExist(code);
                if (t != null) {
                    json.setSuccess(false);
                    json.setMsg("保存失败！商品编码：" + code + "已经存在！");
                } else {
                    goodsService.saveGoods(info);
                    json.setSuccess(true);
                    json.setMsg("保存成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！商品编码和名称不能为空！");
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
    @GetMapping(value = "/getGoodsById")
    public void getGoodsById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TGoods p = goodsService.getGoodsById(id);
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
    @PostMapping(value = "/updateGoods")
    public void updateGoods(@RequestBody TGoods info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getId())) {
                goodsService.updateGoods(info);
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
    @PostMapping(value = "/deleteGoods")
    public void deleteGoods(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            goodsService.deleteGoods(id);
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
    @GetMapping(value = "/getAllGoodsList")
    public void getAllGoodsList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TGoods> list = goodsService.getAllGoodsList();
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
