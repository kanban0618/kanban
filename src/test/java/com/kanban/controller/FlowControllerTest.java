package com.kanban.controller;

import com.kanban.entity.Flow;


import com.kanban.mapper.FlowMapper;
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
class FlowControllerTest {
    @Autowired
    FlowMapper flowMapper;

    //查询所有
    @Test
    void all(@Autowired MockMvc mvc) throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flow/all"))//模拟通过get方式访问网址 /flow/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString();//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    //查找ID
    @Test
    void findById(@Autowired MockMvc mvc) throws Exception{
        Flow flow = new Flow();
        flow.setWorkname("测试工作流名称");
        flowMapper.insert(flow);
        //注意这里的测试不够敏捷，万一id为1的用户被删了就会测试失败，以后会重构该方法，改为先添加后查询，确保查询的时候id是存在的。
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flow/findById/"+flow.getId()))//模拟通过get方式访问网址 /flow/findById/
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(flow.getId())) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.workname").value("测试工作流名称")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        System.out.println(contentAsString);
        JSONObject object = new JSONObject(contentAsString);
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    //添加
    @Test
    void save(@Autowired MockMvc mvc) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "0");
        jsonObject.put("workname", "测试工作流名称");
        jsonObject.put("category", "测试类别");
        jsonObject.put("capacity", "6");
        System.out.println("jsonObject.toString() = " + jsonObject.toString());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flow/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )//模拟通过post方式访问网址 /flow/save, 添加新工作流
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                //id之前是没有的，添加之后才会出现，由于id不确定，这里只能断定id是数字，不能确定是哪个具体值
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNumber()) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.workname").value("测试工作流名称")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.category").value("测试类别")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.capacity").value("6")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    //修改
    @Test
    void update(@Autowired MockMvc mvc) throws Exception {
        Flow flow = new Flow();
        flow.setWorkname("测试工作流名称");
        flowMapper.insert(flow);
        System.out.println("flow.toString() = " + flow.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", flow.getId()+ "");
        jsonObject.put("workname", "开发");
        System.out.println("jsonObject.toString() = " + jsonObject.toString());
        MvcResult updateMvcResult = mvc.perform(MockMvcRequestBuilders.post("/flow/update")//模拟通过post方式访问网址 /flow/update, 修改工作流
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Flow flows = flowMapper.selectById(flow.getId());
        System.out.println(flows.toString());
        //判断修改前后JSON字符是否有变化
        Assertions.assertTrue(!(flow.getWorkname().equals(flows.getWorkname())));

    }

    //删除
    @Test
    void delete(@Autowired MockMvc mvc) throws Exception{
        Flow flow = new Flow();
        flowMapper.insert(flow);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flow/delete/"+flow.getId()))//模拟通过get方式访问网址 /flow/delete/
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString();//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    //查找字段
    @Test
    void findByName(@Autowired MockMvc mvc) throws Exception{
        Flow flow = new Flow();
        flow.setWorkname("查询工作流");
        flowMapper.insert(flow);
        //注意这里的测试不够敏捷，万一id为1的用户被删了就会测试失败，以后会重构该方法，改为先添加后查询，确保查询的时候id是存在的。
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flow/findByName/查询工作流"))//模拟通过get方式访问网址 /flow/findByName/
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]

    }
}