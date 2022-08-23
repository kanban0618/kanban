package com.kanban.controller;

import com.alibaba.fastjson.JSON;
import com.kanban.entity.Rule;
import com.kanban.entity.User;
import com.kanban.mapper.RuleMapper;
import com.kanban.mapper.UserMapper;
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
class RuleControllerTest {
    @Autowired
    RuleMapper ruleMapper;
    @Autowired
    UserMapper userMapper;


    /*  null <上-下> 查找测试开始 */
    @Test
    void all(@Autowired MockMvc mvc) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/rule/all"))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString();//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]

    }

    @Test
    void findById(@Autowired MockMvc mvc) throws Exception {
        //添加测试
        Rule rule = new Rule();
        rule.setUrl("com.qq");
        ruleMapper.insert(rule);

        //模拟浏览器请求
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/rule/findById/" + rule.getId()))//模拟通过get方式访问网址 /user/findById/1,查询id为1的用户
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(rule.getId())) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.url").value("com.qq")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void findByUserId(@Autowired MockMvc mvc) throws Exception {
        //添加测试
        User user = new User();
        user.setName("测试用");
        userMapper.insert(user);
        Rule rule = new Rule();
        rule.setUserid(user.getId());
        rule.setUrl("com.1");
        Rule rule1 = new Rule();
        rule1.setUserid(user.getId());
        rule1.setUrl("com.2");

        ruleMapper.insert(rule);
        ruleMapper.insert(rule1);


        //模拟浏览器请求
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/rule/findByUserId/" + user.getId()))//模拟通过get方式访问网址 /user/findById/1,查询id为1的用户
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(rule.getId())) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("true"))
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }

    /*暂定
    @Test
    void fuzzyQuery(@Autowired MockMvc mvc) throws Exception {
        Rule rule = new Rule();
        rule.setUserid(1);
        rule.setUrl("测试");
        rule.setToken(true);
        ruleMapper.insert(rule);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Object","测试");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/rule/fuzzyQuery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.url").value("测试"))
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]

    }
    */

    /* 查找测试结束 <上-下> 添加测试开始 */
    @Test
    void save(@Autowired MockMvc mvc) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "0");
        jsonObject.put("userid", "12345");
        jsonObject.put("url", "测试添加");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/rule/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userid").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.url").value("测试添加"))
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }

    /* 添加测试结束 <上-下> 修改测试开始 */
    @Test
    void update(@Autowired MockMvc mockMvc) throws Exception {
        //添加测试
        Rule rule = new Rule();
        rule.setUserid(1111);
        rule.setUrl("测试修改前");
        ruleMapper.insert(rule);
        System.out.println("修改前 = " + rule.toString());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", rule.getId() + "");
        jsonObject.put("userid", "1111");
        jsonObject.put("url", "测试修改后");
        //修改任务
        MvcResult upmvc = mockMvc.perform(MockMvcRequestBuilders.post("/rule/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();
        Rule rule1 = ruleMapper.selectById(rule.getId());
        System.out.println("修改后 = " + rule1.toString());
        //判断修改前后JSON字符是否有变化
        Assertions.assertTrue(!(rule.getUrl().equals(rule1.getUrl())));
    }

    /*  修改测试结束 <上-下> 删除测试开始 */
    @Test
    void delete(@Autowired MockMvc mvc) throws Exception {
        //添加测试
        Rule rule = new Rule();
        rule.setUrl("删除单元测试");
        ruleMapper.insert(rule);
        System.out.println("添加的: " + rule);

        MvcResult delmvcResult = mvc.perform(MockMvcRequestBuilders.get("/rule/delete/" + rule.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("true"))
                .andReturn();//将返回保存到mvcResult对象中
        String delcontentAsString = delmvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        System.out.println("删除的: " + delcontentAsString);
        Assertions.assertTrue(delcontentAsString.length() > 0);

    }


}