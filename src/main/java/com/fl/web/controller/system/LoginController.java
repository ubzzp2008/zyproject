package com.fl.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.system.TUserInfo;
import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.system.User;
import com.fl.web.service.system.IUserInfoService;
import com.fl.web.service.system.IUserPositionService;
import com.fl.web.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserPositionService userPositionService;

    @Value("${spring.profiles.active}")
    private String profiles;
//    @Value("${ldap.url}")
//    private String ldapUrl;
//    @Value("${ldap.username}")
//    private String ldapRootName;
//    @Value("${ldap.password}")
//    private String ldapPwd;

    /**
     * @description：登录前检验用户信息
     * @author：justin
     * @date：2019-10-09 09:12
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public void userLogin(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            HttpSession session = ContextHolderUtils.getSession();
            String username = map.get("username");
            String password = map.get("password");
            String randCode = map.get("randCode");
            String loginType = map.get("loginType");//登录方式
            logger.info("randCode===" + randCode);
            logger.info("active===" + profiles);
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                json.setSuccess(false);
                json.setMsg("用户名或密码为空！");
//            } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
//                json.setSuccess(false);
//                json.setMsg("验证码错误！");
            } else {
//                logger.info("开发模式："+profiles+"==="+StringUtil.equals("dev", profiles));
                TUserInfo user = userInfoService.checkUserExits(username, password);
                if (user != null) {
                    if (StringUtils.equals("0", user.getLockFlag())) {
                        User obj = new User();
                        BeanUtils.copyProperties(user, obj);
                        //根据用户id获取岗位信息
                        List<TUserPosition> posList = userPositionService.getPositionByUserId(user.getId());
                        obj.setPosList(posList);
                        Client client = new Client();
                        client.setIp(IpUtil.getIpAddr(request));
                        client.setLoginDate(new Date());
                        client.setUser(obj);
                        ClientManager.getInstance().addClient(session.getId(), client);
                        json.setSuccess(true);
                        json.setMsg("验证成功");
                        json.setObj(obj);
                    } else {
                        json.setSuccess(false);
                        json.setMsg("此账号已停用");
                    }
                } else {
                    json.setSuccess(false);
                    json.setMsg("用户名或密码不正确！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg("系统异常，请联系系统管理员！" + e.getMessage());
        }
        writeJson(json, response);
    }

    /*@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public void userLogin(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            HttpSession session = ContextHolderUtils.getSession();
            String username = map.get("username");
            String password = map.get("password");
            String randCode = map.get("randCode");
            logger.info("randCode===" + randCode);
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                json.setSuccess(false);
                json.setMsg("用户名或密码为空！");
//            } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
//                json.setSuccess(false);
//                json.setMsg("验证码错误！");
            } else {
                TUserInfo user = userInfoService.checkUserExits(username, password);
                if (user != null) {
                    if (StringUtils.equals("1", user.getLockFlag())) {
                        json.setSuccess(false);
                        json.setMsg("此用户已被禁用");
                    } else {
                        User obj = new User();
                        BeanUtils.copyProperties(user, obj);
                        json.setSuccess(true);
                        json.setMsg("验证成功");
                        json.setObj(obj);
                        Client client = new Client();
                        client.setIp(IpUtil.getIpAddr(request));
                        client.setLoginDate(new Date());
                        client.setUser(obj);
                        ClientManager.getInstance().addClient(session.getId(), client);
                    }
                } else {
                    json.setSuccess(false);
                    json.setMsg("用户名或密码不正确！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            json.setSuccess(false);
            json.setMsg("系统异常，请联系系统管理员！" + e.getMessage());
        }
        writeJson(json, response);
    }*/

    /**
     * @description：登出系统
     * @author：justin
     * @date：2019-10-09 13:38
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletResponse response) {
        HttpSession session = ContextHolderUtils.getSession();
        ClientManager.getInstance().removeClient(session.getId());
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("msg", "成功退出系统！");
        writeJson(res, response);
    }
}
