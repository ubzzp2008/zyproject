package com.fl.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.system.TRole;
import com.fl.web.entity.system.TRoleMenu;
import com.fl.web.model.system.Role;
import com.fl.web.service.system.IRoleService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RoleController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 14:35
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;


    /**
     * @description：分页查询物料数据
     * @author：justin
     * @date：2019-10-08 11:34
     */
    @RequestMapping(value = "/queryRoleList", method = RequestMethod.POST)
    public void queryRoleList(@RequestBody Role role, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(role.getPageNum(), role.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TRole> list = roleService.queryRoleList(role);
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
     * @description：检查编码是否已经存在
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getRoleByCode")
    public void getRoleByCode(@RequestParam(name = "roleCode") String roleCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            roleCode = roleCode.trim();
            TRole m = roleService.getRoleByCode(roleCode);
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
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getRoleById")
    public void getRoleById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TRole role = roleService.getRoleById(id);
            json.setSuccess(true);
            json.setObj(role);
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
    @PostMapping(value = "/saveRole")
    public void saveRole(@RequestBody TRole role, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String code = role.getRoleCode().trim();
            TRole t = roleService.getRoleByCode(code);
            if (t != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + code + "已经存在！");
            } else {
                roleService.saveRole(role);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
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
    @PostMapping(value = "/updateRole")
    public void updateRole(@RequestBody TRole role, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            roleService.updateRole(role);
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
    @PostMapping(value = "/deleteRoleById")
    public void deleteRoleById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            roleService.deleteRoleById(id);
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
     * @description：保存角色与菜单关系
     * @author：justin
     * @date：2019-10-12 17:07
     */
    @PostMapping(value = "/saveRoleMenus")
    public void saveRoleMenus(@RequestBody List<TRoleMenu> list, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                roleService.saveRoleMenus(list);
                json.setSuccess(true);
                json.setMsg("保存成功");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！参数接收异常！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存角色与菜单关系失败", e);
            json.setSuccess(false);
            json.setMsg("保存失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据岗位id获取角色信息
     * @author：justin
     * @date：2019-10-14 15:48
     */
    @GetMapping(value = "/getRolesByPosId")
    public void getRolesByPosId(@RequestParam(name = "posId") String posId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(posId)) {
                json.setSuccess(false);
                json.setMsg("获取角色信息异常！参数【userId】为空！");
            } else {
                List<TRole> list = roleService.getRolesByPosId(posId);
                json.setSuccess(true);
                json.setObj(list);
                json.setMsg("获取成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据用户获取角色失败", e);
            json.setSuccess(false);
            json.setMsg("获取角色信息异常" + e.getMessage());
        }
        writeJson(json, response);
    }

}
