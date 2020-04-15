package com.fl.web.controller.system;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.system.TPosition;
import com.fl.web.entity.system.TPositionRole;
import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.base.BaseTree;
import com.fl.web.service.system.IPositionRoleService;
import com.fl.web.service.system.IPositionService;
import com.fl.web.service.system.IUserPositionService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：PositionController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:06
 */
@Controller
@RequestMapping("/system/position")
public class PositionController extends BaseController {
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IPositionRoleService positionRoleService;
    @Autowired
    private IUserPositionService userPositionService;

    /**
     * @description：根据组织机构ID获取岗位信息
     * @author：justin
     * @date：2019-12-13 14:25
     */
    @GetMapping(value = "/getPositionByOrgId")
    public void getPositionByOrgId(@RequestParam(name = "orgId") String orgId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TPosition> list = positionService.getPositionByOrgId(orgId);
            json.setSuccess(true);
            json.setObj(list);
            json.setMsg("获取数据成功");
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }


    /**
     * @description：检查编码是否存在
     * @author：justin
     * @date：2019-11-18 17:39
     */
    @GetMapping(value = "/getPositionByCode")
    public void getPositionByCode(@RequestParam(name = "posCode") String posCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            posCode = posCode.trim();
            TPosition p = positionService.getPositionByCode(posCode);
            if (p != null) {
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
     * @date：2019-11-18 17:40
     */
    @GetMapping(value = "/getPositionById")
    public void getPositionById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TPosition p = positionService.getPositionById(id);
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
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:43
     */
    @PostMapping(value = "/savePosition")
    public void savePosition(@RequestBody TPosition position, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(position.getOrgId())) {
                String code = position.getPosCode().trim();
                TPosition t = positionService.getPositionByCode(code);
                if (t != null) {
                    json.setSuccess(false);
                    json.setMsg("保存失败！编码：" + code + "已经存在！");
                } else {
                    positionService.savePosition(position);
                    json.setSuccess(true);
                    json.setMsg("保存成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【orgId】为空！");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存岗位信息异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：保存岗位与角色关系
     * @author：justin
     * @date：2019-11-19 16:26
     */
    @PostMapping(value = "/savePositionRoles")
    public void savePositionRoles(@RequestBody List<TPositionRole> list, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                positionRoleService.savePositionRoles(list);
                json.setSuccess(true);
                json.setMsg("保存成功");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！参数接收异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存岗位与角色关系失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：修改岗位信息
     * @author：justin
     * @date：2019-11-18 17:45
     */
    @PostMapping(value = "/updatePosition")
    public void updatePosition(@RequestBody TPosition position, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isEmpty(position.getId())) {
                json.setMsg("操作失败！关键参数【id】为空！");
                json.setSuccess(false);
            } else {
                if (StringUtil.equals(StaticParam.ROOT_POS, position.getId())) {
                    position.setPid(null);//若为根节点岗位，置空pid使其不做修改，避免调整到了根节点，导致数据紊乱
                }
                positionService.updatePosition(position);
                json.setSuccess(true);
                json.setMsg("修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改岗位信息失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除
     * @author：justin
     * @date：2019-11-18 17:46
     */
    @PostMapping(value = "/deletePositionById")
    public void deletePositionById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.equals(StaticParam.ROOT_POS, id)) {
                json.setSuccess(false);
                json.setMsg("此岗位为根节点，禁止删除！");
            } else {
                //验证岗位是否包含下级岗位，若包含下级，禁止删除
                List<TPosition> subPos = positionService.getPositionByPid(id);
                if (CollectionUtils.isNotEmpty(subPos)) {
                    json.setSuccess(false);
                    json.setMsg("此岗位存在下级岗位，禁止删除！");
                } else {
                    //需要检查改岗位是否还挂着人员，若是，提示先删除人员，不需检查岗位与角色的关系，删除岗位时，直接删除岗位与角色关系
                    Integer count = userPositionService.checkPositionByPosId(id);
                    if (count > 0) {
                        json.setSuccess(false);
                        json.setMsg("删除失败！原因：此岗位还存在人员，请先解绑此岗位上的人员后再做删除操作！");
                    } else {
                        positionService.deletePositionById(id);
                        json.setSuccess(true);
                        json.setMsg("删除成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除岗位信息失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：获取所有岗位信息（级联选择用）
     * @author：justin
     * @date：2019-11-19 09:34
     */
    @GetMapping(value = "/findAllPositionList")
    public void findAllPositionList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<BaseTree> list = positionService.findAllPositionList();
            json.setSuccess(true);
            json.setObj(list);
            json.setMsg("获取岗位信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取岗位信息失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据岗位id获取用户信息
     * @author：justin
     * @date：2019-12-13 15:37
     */
    @GetMapping(value = "/getUserInfoByPosId")
    public void getUserInfoByPosId(@RequestParam(name = "posId") String posId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TUserPosition> list = userPositionService.getUserInfoByPosId(posId);
            json.setSuccess(true);
            json.setObj(list);
            json.setMsg("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：保存用户与岗位关系数据
     * @author：justin
     * @date：2019-12-13 15:50
     */
    @PostMapping(value = "/saveUserPosition")
    public void saveUserPosition(@RequestBody List<TUserPosition> infoList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(infoList)) {
                userPositionService.saveUserPosition(infoList);
                json.setSuccess(true);
                json.setMsg("保存成功");
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！参数为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存用户与岗位关系异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除用户与岗位关系
     * @author：justin
     * @date：2019-12-13 15:54
     */
    @PostMapping(value = "/deleteUserPosition")
    public void deleteUserPosition(@RequestBody List<String> idList, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                userPositionService.deleteUserPosition(idList);
                json.setSuccess(true);
                json.setMsg("保存成功");
            } else {
                json.setMsg("操作失败！参数为空！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除岗位与人员关系失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


}
