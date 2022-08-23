package com.kanban.controller;

import com.kanban.entity.Story;
import com.kanban.mapper.StoryMapper;
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

/**
 * @version 1.0.0
 * @time 2022/6/28 17:27
 * @Author SmallWatermelon
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StoryControllerTest {

    @Autowired
    StoryController storyController;

    @Autowired
    StoryMapper storyMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void all() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/story/all"))  //模拟get方式访问网址
                .andExpect(MockMvcResultMatchers.status().isOk())   //期望访问返回状态是200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) //期望返回数据是JSON格式的
                .andReturn();   //返回对象保存进mvcResult
        String contentAsString = mvcResult.getResponse().getContentAsString();  //获得返回的JSON字符串
        Assertions.assertTrue(contentAsString.length() > 0);    //期望返回字符串不是空， 至少为[]
    }

    @Test
    void findById() throws Exception {
        Story story = new Story();
        story.setTitle("测试标题");
        story.setState("测试状态");
        storyMapper.insert(story);

        //注意这里的测试不够敏捷，万一id为1的用户被删了就会测试失败，以后会重构该方法，改为先添加后查询，确保查询的时候id是存在的。
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/story/findById/" + story.getId()))//模拟通过get方式访问网址 /story/findById/1,查询id为1的用户
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("测试标题")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.state").value("测试状态"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
//                .andExpect(MockMvcResultMatchers.jsonPath("$name").value("测试王五")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void save() throws Exception {
        JSONObject object = new JSONObject();
        object.put("title", "测试标题");
        object.put("originator", "测试创始人");
        object.put("personInCharge", "测试负责人");
        object.put("estimationPoint", 1);
        object.put("state", "测试状态");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/story/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885

                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }


    @Test
    void update() throws Exception {
        Story story = new Story();
        story.setOriginator("测试发起人");
        story.setTitle("测试标题");
        story.setState("测试状态");
        storyMapper.insert(story);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", story.getId() + "");
        jsonObject.put("originator", "测试发起人");
        jsonObject.put("state", "状态测试");

        MvcResult updateMvcResult = mvc.perform(MockMvcRequestBuilders.post("/story/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Story story1 = storyMapper.selectById(story.getId());
        Assertions.assertTrue(!(story.getState().equals(story1.getContent())));
    }

    @Test
    void delete() throws Exception {
        Story story = new Story();
        story.setTitle("测试标题");
        story.setState("测试状态");
        storyMapper.insert(story);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/story/delete/" + story.getId()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("删除成功！"))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回字符串不是空，至少有[]
    }

    @Test
    void delete2() {
    }

    @Test
    void findByName() throws Exception {
        Story story = new Story();
        story.setTitle("测试标题");
        story.setState("测试状态");
        storyMapper.insert(story);

        //注意这里的测试不够敏捷，万一id为1的用户被删了就会测试失败，以后会重构该方法，改为先添加后查询，确保查询的时候id是存在的。
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/story/findByName/测试状态"))//模拟通过get方式访问网址 /story/findById/1,查询id为1的用户
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void page() {
    }

    @Test
    void init() {
    }
}