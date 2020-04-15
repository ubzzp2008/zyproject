package com.fl.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyIntercept extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return  super.preHandle(request,response,handler);
        //字符集拦截
        /*request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = ContextHolderUtils.getSession();
        crossDomain(request, response);
        String requestPath = SessionUser.getRequestPath(request);// 用户访问的资源地址
        logger.info("请求路劲：" + requestPath + "====sessionId：" + session.getId());
        Client client = ClientManager.getInstance().getClient(session.getId());
        if (client == null) {
            client = ClientManager.getInstance().getClient(request.getParameter("sessionId"));
        }
        if (client != null && client.getUser() != null) {
            logger.info("当前用户已登录，登录的用户名为： " + client.getUser().getUserName());
            return true;
        } else {
            //返回json格式的登录超时
            JSONObject res = new JSONObject();
            res.put("success", false);
            res.put("timeOut", true);
            res.put("msg", "登录超时，请重新登录！");
            String jsonObjectStr = JSONObject.toJSONString(res);
            returnJson(response, jsonObjectStr);
            return false;
        }*/
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            logger.info(json);
            writer.print(json);
        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    private void crossDomain(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));//支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
//        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //Origin, X-Requested-With, Content-Type, Accept,Access-Token
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
//        logger.info("MyIntercept正在拦截");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
//        logger.info("MyIntercept拦截完成");
    }
}
