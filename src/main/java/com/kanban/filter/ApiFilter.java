package com.kanban.filter;

import com.alibaba.fastjson.JSONObject;
import com.kanban.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @version 1.0
 * @time 2022/7/12 11:32
 * @Author SmallWatermelon
 * @since 1.8
 */
//过滤controller api
@WebFilter(filterName = "ApiFilter", urlPatterns = {"/flow/*", "/rule/*", "/story/*", "/task/*", "/user/*"})
public class ApiFilter implements Filter {
    public void destroy() {
        System.out.println("运行结束时销毁");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        OutputStream out = response.getOutputStream();//如果应用中包含的有fastJson, siteMesh, urlWriter等插件，都要更改response.getWriter()为response.getOutputStream()

        HttpSession session = request.getSession();//获取session
        User user = (User) session.getAttribute("LOGIN_USER");//获取token, 类似与通行证

        if (user == null) {
            System.out.println("未授权登录 " + request.getRequestURI());
            response.setContentType("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", false);
            jsonObject.put("message", "非法访问！");
            jsonObject.put("data", null);


            out.write(Integer.parseInt(jsonObject.toString()));
            out.flush();
            out.close();
        }
        System.out.println("已登录，放行:" + request.getRequestURI());
        chain.doFilter(req, resp);//转接下一步
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("运行开始时初始化");
    }

}

/**
 * 参考：1.https://www.jianshu.com/p/4dea12ffbf8e (getOutputStream)
 *      2.
 */
