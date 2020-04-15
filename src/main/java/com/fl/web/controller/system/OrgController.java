package com.fl.web.controller.system;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.system.TOrg;
import com.fl.web.entity.system.TPosition;
import com.fl.web.model.base.BaseTree;
import com.fl.web.service.system.IOrgService;
import com.fl.web.service.system.IPositionService;
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
 * @类名称：OrgController
 * @类描述：组织机构
 * @创建人：justin
 * @创建时间：2019-12-13 09:31
 */
@Controller
@RequestMapping("/system/org")
public class OrgController extends BaseController {
    @Autowired
    private IOrgService orgService;
    @Autowired
    private IPositionService positionService;


    /**
     * @description：检查编码是否存在
     * @author：justin
     * @date：2019-12-13 10:04
     */
    @GetMapping(value = "/getOrgByCode")
    public void getOrgByCode(@RequestParam(name = "orgCode") String orgCode, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            orgCode = orgCode.trim();
            TOrg t = orgService.getOrgByCode(orgCode);
            if (t != null) {
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
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:43
     */
    @PostMapping(value = "/saveOrg")
    public void saveOrg(@RequestBody TOrg org, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(org.getPid())) {
                String code = org.getOrgCode().trim();
                TOrg t = orgService.getOrgByCode(code);
                if (t != null) {
                    json.setSuccess(false);
                    json.setMsg("保存失败！编码：" + code + "已经存在！");
                } else {
                    orgService.saveOrg(org);
                    json.setSuccess(true);
                    json.setMsg("保存成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！关键参数【pid】为空！");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存组织信息异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:06
     */
    @GetMapping(value = "/getOrgById")
    public void getOrgById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TOrg p = orgService.getOrgById(id);
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
     * @description：根据pid获取数据
     * @author：justin
     * @date：2019-12-13 10:22
     */
    @GetMapping(value = "/getOrgByPid")
    public void getOrgByPid(@RequestParam(name = "pid") String pid, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TOrg> list = orgService.getOrgByPid(pid);
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

    /**
     * @description：修改组织信息
     * @author：justin
     * @date：2019-12-13 09:58
     */
    @PostMapping(value = "/updateOrg")
    public void updateOrg(@RequestBody TOrg org, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(org.getId()) && StringUtil.isNotEmpty(org.getPid())) {
                orgService.updateOrg(org);
                json.setSuccess(true);
                json.setMsg("修改成功");
            } else {
                json.setMsg("操作失败！关键参数【id或pid】为空！");
                json.setSuccess(false);
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
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-13 10:14
     */
    @PostMapping(value = "/deleteOrgById")
    public void deleteOrgById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.equals(StaticParam.ROOT_ORG, id)) {
                json.setSuccess(false);
                json.setMsg("此组织为根节点，禁止删除！");
            } else {
                //验证组织是否包含下级，若包含下级，禁止删除
                List<TOrg> subOrg = orgService.getOrgByPid(id);
                if (CollectionUtils.isNotEmpty(subOrg)) {
                    json.setSuccess(false);
                    json.setMsg("此组织包含下级组织，禁止删除！");
                } else {
                    //需要检查此组织是否已经绑定岗位，若已绑定，禁止删除
                    List<TPosition> positionList = positionService.getPositionByOrgId(id);
                    if (CollectionUtils.isNotEmpty(positionList)) {
                        json.setSuccess(false);
                        json.setMsg("删除失败！原因：此组织已绑定岗位，请先解绑岗位再做删除操作！");
                    } else {
                        orgService.deleteOrgById(id);
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
     * @description：获取所有组织信息，树形结构
     * @author：justin
     * @date：2019-12-13 10:46
     */
    @GetMapping(value = "/findAllOrgList")
    public void findAllOrgList(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<BaseTree> list = orgService.findAllOrgList();
            json.setSuccess(true);
            json.setObj(list);
            json.setMsg("获取组织信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取组织信息失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }


}
