package com.fl.web.utils;

import com.fl.web.model.system.User;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 项目参数工具类
 */
public class SessionUser {

    //	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("sysConfig");

    /**
     * 获取session定义名称
     *
     * @return
     */
    //		public static final String getSessionattachmenttitle(String sessionName) {
    //			return bundle.getString(sessionName);
    //		}
    public static final User getUser() {
        HttpSession session = ContextHolderUtils.getSession();
        if (ClientManager.getInstance().getClient(session.getId()) != null) {
            return ClientManager.getInstance().getClient(session.getId()).getUser();
        }
        return null;
    }

    /**
     * @desc：获取登录帐号
     * @author：justin
     * @date：2019-01-18 11:12
     */
    public static final String getUserName() {
        User user = getUser();
        if (user != null) {
            return user.getUserName();
        }
        return "";
    }

    /**
     * @desc：获取登录姓名
     * @author：justin
     * @date：2019-01-18 11:12
     */
    public static final String getRealName() {
        User user = getUser();
        if (user != null) {
            return user.getRealName();
        }
        return "";
    }

    /**
     * 获得请求路径
     *
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        //		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
        //		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
        //			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
        //		}
        String requestPath = request.getRequestURI();
        if (requestPath.indexOf("?") > -1) {// 去掉其他参数
            requestPath = requestPath.substring(0, requestPath.indexOf("?"));
        }
        requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
        return requestPath;
    }

    /**
     * 获取配置文件参数
     */
    public static String getParameter(String field) {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        return request.getParameter(field);
    }

    /**
     * @description：根据岗位编码获取岗位id
     * @author：justin
     * @date：2019-12-17 09:23
     */
    public static final String getPosIdByPosCode(String posCode) {
        User user = getUser();
        String posId = "";
        if (user != null && CollectionUtils.isNotEmpty(user.getPosList())) {
            for (int i = 0; i < user.getPosList().size(); i++) {
                if (StringUtil.equals(posCode, user.getPosList().get(i).getPosCode())) {
                    posId = user.getPosList().get(i).getPosId();
                    break;
                }
            }
        }
        return posId;
    }


    /**
     * 获取随机码的长度
     *
     * @return 随机码的长度
     */
    //	public static String getRandCodeLength() {
    //		return bundle.getString("randCodeLength");
    //	}

    /**
     * 获取随机码的类型
     *
     * @return 随机码的类型
     */
    //	public static String getRandCodeType() {
    //		return bundle.getString("randCodeType");
    //	}

}
