package com.kanban.filter;

import com.alibaba.fastjson.JSONObject;
import com.kanban.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.SendResult;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 1.0
 * @time 2022/7/7 15:22
 * @Author SmallWatermelon
 * @since 1.8
 */
//过滤网页
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/sub/*"})//过滤sub目录下的所有页面
public class LoginFilter implements Filter {
    public void destroy() {
        System.out.println("destroy");//销毁时运行
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();//获取session
        User user = (User) session.getAttribute("LOGIN_USER");//获取token, 类似与通行证

        if (user == null) {//如果session是空则跳转到登录页面
            response.sendRedirect("../../login.html");//页面跳转
            return;
        }

        chain.doFilter(req, resp);//转接下一步
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter init");//初始化时运行
    }
}
