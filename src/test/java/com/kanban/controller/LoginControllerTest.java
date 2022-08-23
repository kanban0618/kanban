package com.kanban.controller;

import com.kanban.entity.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginControllerTest {

    @Test
    void loginShouldSuccess(@Autowired MockMvc mvc) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "cs");
        jsonObject.put("password", "123");
        System.out.println("jsonObject.toString() = " + jsonObject.toString());
        MvcResult savemvcResult = mvc.perform(MockMvcRequestBuilders.post("/login/doLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("cs")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("******")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true)) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("登录成功")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andReturn();
        String contentAsString = savemvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void logout() {

    }

    @Test
    void register(@Autowired MockMvc mvc) throws Exception{
        JSONObject object = new JSONObject();
//        object.put("id",user.getId()+"");
        object.put("name","测试姓名");
        object.put("username","测试账号");
        object.put("password","测试密码");
        object.put("jobno","测试工号");
        object.put("position","测试职位");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("测试账号")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }


}