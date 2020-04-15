package com.fl.web.controller.system;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.system.TMenu;
import com.fl.web.model.base.ModuleTree;
import com.fl.web.service.system.IMenuService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MenuController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 10:57
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController {
    @Autowired
    private IMenuService menuService;

    /**
     * @description：根据用户ID获取菜单
     * @author：justin
     * @date：2019-10-09 10:59
     */
    @RequestMapping(value = "/findMenuByUserId", method = RequestMethod.GET)
    public void findMenuByUserId(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String userId = request.getParameter("userId");
            String posId = request.getParameter("posId");
            if (StringUtils.isNotEmpty(userId) && StringUtil.isNotEmpty(posId)) {
                List<ModuleTree> menuList = menuService.findMenuByUserId(userId, posId);
                json.setObj(menuList);
                json.setSuccess(true);
            } else {
                json.setMsg("获取菜单失败！参数【userId或posId】为空！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取菜单异常", e);
            json.setSuccess(false);
            json.setMsg("获取菜单异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：查询所有菜单
     * @author：justin
     * @date：2019-10-12 15:47
     */
    @RequestMapping(value = "/queryMenuList", method = RequestMethod.POST)
    public void queryMenuList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TMenu> list = menuService.queryMenuList();
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取菜单信息异常", e);
            json.setMsg("获取菜单信息异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getMenuById")
    public void getMenuById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TMenu menu = menuService.getMenuById(id);
            json.setSuccess(true);
            json.setObj(menu);
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
    @PostMapping(value = "/saveMenu")
    public void saveMenu(@RequestBody TMenu menu, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            menuService.saveMenu(menu);
            json.setSuccess(true);
            json.setMsg("保存成功");
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存角色异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/updateMenu")
    public void updateMenu(@RequestBody TMenu menu, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            menuService.updateMenu(menu);
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
     * @description：删除信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/deleteMenuById")
    public void deleteMenuById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //检查是否存在下级，若存在下级，则不允许删除
            List<TMenu> list = menuService.getMenuByPid(id);
            if (CollectionUtils.isNotEmpty(list)) {
                json.setSuccess(false);
                json.setMsg("此菜单包含下级菜单，禁止删除！");
            } else {
                menuService.deleteMenuById(id);
                json.setSuccess(true);
                json.setMsg("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除失败", e);
            json.setSuccess(false);
            json.setMsg("删除失败" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：启用禁用菜单
     * @author：justin
     * @date：2019-10-12 16:38
     */
    @RequestMapping(value = "/lockOrUnlockMenu", method = RequestMethod.POST)
    public void lockOrUnlockMenu(@RequestBody TMenu menu, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            menuService.lockOrUnlockMenu(menu);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改异常", e);
            json.setSuccess(false);
            json.setMsg("修改异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据角色id获取菜单
     * @author：justin
     * @date：2019-10-14 15:09
     */
    @GetMapping(value = "/getMenuByRoleId")
    public void getMenuByRoleId(@RequestParam(name = "roleId") String roleId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(roleId)) {
                json.setSuccess(false);
                json.setMsg("获取角色信息异常！参数【roleId】为空！");
            } else {
                List<TMenu> list = menuService.getMenuByRoleId(roleId);
                json.setSuccess(true);
                json.setObj(list);
                json.setMsg("获取成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据角色获取菜单失败", e);
            json.setSuccess(false);
            json.setMsg("获取菜单异常" + e.getMessage());
        }
        writeJson(json, response);
    }


}
