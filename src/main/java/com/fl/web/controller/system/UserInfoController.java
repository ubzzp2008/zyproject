package com.fl.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.system.TUserInfo;
import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.system.UserInfo;
import com.fl.web.model.system.UserPosition;
import com.fl.web.service.system.IUserInfoService;
import com.fl.web.service.system.IUserPositionService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：UserInfoController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 09:18
 */
@Controller
@RequestMapping("/system/user")
public class UserInfoController extends BaseController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserPositionService userPositionService;

    /**
     * @description：分页查询用户信息
     * @author：justin
     * @date：2019-10-10 09:31
     */
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
    public void queryUserList(@RequestBody UserInfo user, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(user.getPageNum(), user.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TUserInfo> list = userInfoService.queryUserList(user);
                json.setSuccess(true);
                json.setObj(list);
            } else {
                json.setMsg(msg);
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取用户信息异常", e);
            json.setMsg("获取用户信息异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据用户名获取用户信息
     * @author：justin
     * @date：2019-10-10 13:40
     */
    @RequestMapping(value = "/getUserByUserName", method = RequestMethod.GET)
    public void getUserByUserName(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String param = request.getParameter("userName");
            String userName = StringUtil.replaceBlank(param).toLowerCase();
            TUserInfo userInfo = userInfoService.getUserByUserName(userName);
            if (userInfo != null) {
                json.setSuccess(false);
                json.setMsg("此账号已被使用，请更换账号");
            } else {
                json.setSuccess(true);
                json.setMsg("账号检查通过");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("检测用户异常", e);
            json.setSuccess(false);
            json.setMsg("检测用户异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：保存用户
     * @author：justin
     * @date：2019-10-10 09:19
     */
    @RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
    public void saveUserInfo(@RequestBody TUserInfo userInfo, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            userInfo.setUserName(StringUtil.replaceBlank(userInfo.getUserName()).toLowerCase());// 用户名进行去空操作,且做转小写操作
            userInfo.setRealName(StringUtil.replaceBlank(userInfo.getRealName()));// 真实名进行去空操作
            // 根据登录名验证是否存在
            TUserInfo user = userInfoService.getUserByUserName(userInfo.getUserName());
            // 用户名已经存在，不允许新增
            if (user != null) {
                json.setSuccess(false);
                json.setMsg("用户名已经存在，请重新输入！");
            } else {
                userInfoService.saveUserInfo(userInfo);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存用户异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据ID获取用户信息
     * @author：justin
     * @date：2019-10-10 13:40
     */
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.GET)
    public void getUserInfoById(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String id = request.getParameter("userId");
            TUserInfo userInfo = userInfoService.getUserInfoById(id);
            json.setSuccess(true);
            json.setObj(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据ID获取用户信息异常", e);
            json.setSuccess(false);
            json.setMsg("获取用户信息异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：修改用户信息
     * @author：justin
     * @date：2019-10-10 13:42
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public void updateUserInfo(@RequestBody TUserInfo user, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            user.setRealName(StringUtil.replaceBlank(user.getRealName()));// 真实名进行去空操作
            userInfoService.updateUserInfo(user);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改用户保存失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id删除用户
     * @author：justin
     * @date：2019-11-21 10:59
     */
    @PostMapping(value = "/deleteUserInfoById")
    public void deleteUserInfoById(@RequestParam(name = "userId") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtils.isEmpty(id)) {
                json.setSuccess(false);
                json.setMsg("参数【userId】为空！");
            } else {
                TUserInfo userInfo = userInfoService.getUserInfoById(id);
                if (userInfo != null && StringUtil.equals("admin", userInfo.getUserName())) {
                    json.setSuccess(false);
                    json.setMsg("【admin】用户禁止删除！");
                } else {
                    userInfoService.deleteUserInfoById(id);
                    json.setSuccess(true);
                    json.setMsg("删除成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：验证旧密码是否正确
     * @author：justin
     * @date：2019-11-21 10:59
     */
    @GetMapping(value = "/checkOldPwd")
    public void checkOldPwd(@RequestParam(name = "userId") String userId, @RequestParam(name = "oldPwd") String oldPwd, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //根据userId和旧密码验证是否正确
            Integer count = userInfoService.checkOldPwd(userId, oldPwd);
            if (count > 0) {
                json.setSuccess(true);
                json.setMsg("验证通过");
            } else {
                json.setSuccess(false);
                json.setMsg("错误：旧密码不正确！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("检验旧密码是否正确异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：用户修改密码
     * @author：justin
     * @date：2019-10-10 13:46
     */
    @RequestMapping(value = "/updatePasswordSelf", method = RequestMethod.POST)
    public void updatePasswordSelf(@RequestBody Map<String, String> userMap, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String userId = userMap.get("userId");
            String userName = userMap.get("userName");
            String oldPwd = userMap.get("oldPwd");
            String newPwd = userMap.get("newPwd");
            if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(oldPwd) && StringUtil.isNotEmpty(newPwd)) {
                //根据userId和旧密码验证是否正确
                Integer count = userInfoService.checkOldPwd(userId, oldPwd);
                if (count > 0) {
                    userInfoService.updatePasswordSelf(userId, userName, newPwd);
                    json.setSuccess(true);
                    json.setMsg("密码修改成功");
                } else {
                    json.setSuccess(false);
                    json.setMsg("密码修改失败！原因：旧密码不正确！");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！参数【userId、userName、oldPwd、newPwd】存在空值！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("密码修改异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：用户修改个人信息
     * @author：justin
     * @date：2019-11-21 09:47
     */
    @RequestMapping(value = "/updateUserInfoSelf", method = RequestMethod.POST)
    public void updateUserInfoSelf(@RequestBody TUserInfo user, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(user.getId())) {
                userInfoService.updateUserInfoSelf(user);
                json.setSuccess(true);
                json.setMsg("保存成功");
            } else {
                json.setSuccess(false);
                json.setMsg("操作失败！原因：参数【user.id】为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("个人信息修改异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    @RequestMapping(value = "/lockOrUnlockUser", method = RequestMethod.POST)
    public void lockOrUnlockUser(@RequestBody TUserInfo user, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String id = user.getId();
            TUserInfo userInfo = userInfoService.getUserInfoById(id);
            if (userInfo != null && StringUtil.equals("admin", userInfo.getUserName())) {
                json.setSuccess(false);
                json.setMsg("【admin】用户禁止操作！");
            } else {
                userInfoService.lockOrUnlockUser(user);
                json.setSuccess(true);
                json.setMsg("用户修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户修改异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：初始化密码
     * @author：justin
     * @date：2019-10-10 15:36
     */
    @RequestMapping(value = "/initPassword", method = RequestMethod.POST)
    public void initPassword(@RequestParam(name = "userId") String userId, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TUserInfo user = new TUserInfo();
            user.setId(userId);
            user.setPassword("123456");
            userInfoService.initPassword(user);
            json.setSuccess(true);
            json.setMsg("成功初始化密码为：123456");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：分页查询人员岗位信息
     * @author：justin
     * @date：2019-11-19 15:46
     */
    @PostMapping(value = "/queryUserPositionList")
    public void queryUserPositionList(@RequestBody UserPosition info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TUserPosition> list = userPositionService.queryUserPositionList(info);
                json.setSuccess(true);
                json.setObj(list);
            } else {
                json.setMsg(msg);
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取用户岗位信息异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据岗位id获取可用用户
     * @author：justin
     * @date：2019-12-13 16:03
     */
    @PostMapping(value = "/queryUserForPosition")
    public void queryUserForPosition(@RequestBody UserInfo info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                if (StringUtil.isNotEmpty(info.getPosId())) {
                    PageInfo<TUserInfo> list = userInfoService.queryUserForPosition(info);
                    json.setSuccess(true);
                    json.setObj(list);
                } else {
                    json.setSuccess(false);
                    json.setMsg("查询失败！参数【posId】为空！");
                }
            } else {
                json.setMsg(msg);
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取用户信息异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }


    /**
     * @description：获取未配置的维护员
     * @author：justin
     * @date：2019-10-15 10:27
     */
    @GetMapping(value = "/getRepairUser")
    public void getRepairUser(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TUserInfo> list = userInfoService.getRepairUser();
            json.setObj(list);
            json.setSuccess(true);
            json.setMsg("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取未配置的维护员失败", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

}
