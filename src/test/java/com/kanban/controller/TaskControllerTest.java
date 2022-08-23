package com.kanban.controller;

import com.kanban.entity.Flow;
import com.kanban.entity.Task;
import com.kanban.entity.User;
import com.kanban.mapper.FlowMapper;
import com.kanban.mapper.TaskMapper;
import com.kanban.mapper.UserMapper;
import org.json.JSONArray;
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
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerTest {
    @Autowired
    TaskController taskController;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FlowMapper flowMapper;

    @Test
    void save(@Autowired MockMvc mvc) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "0");
        jsonObject.put("title", "添加任务单元测试");
        jsonObject.put("content", "测试任务内容");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/task/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())

        )//模拟通过post方式访问网址 /task/add, 添加新任务
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                //id之前是没有的，添加之后才会出现，由于id不确定，这里只能断定id是数字，不能确定是哪个具体值
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNumber()) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("添加任务单元测试")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("测试任务内容")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]
    }

    /* 添加测试结束 <上-下> 删除测试开始 */
    @Test
    void delete(@Autowired MockMvc mvc) throws Exception {
        //添加测试Task
        Task task = new Task();
        task.setTitle("删除任务单元测试");
        taskMapper.insert(task);
        System.out.println("添加的: " + task);

        MvcResult delmvcResult = mvc.perform(MockMvcRequestBuilders.get("/task/delete/" + task.getId()))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1"))
                .andReturn();//将返回保存到mvcResult对象中
        String delcontentAsString = delmvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);//获得返回的json字符串
        System.out.println("删除的: " + delcontentAsString);
        Assertions.assertTrue(delcontentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]

    }

    /* 删除测试结束 <上-下> 修改测试开始 */
    @Test
    void update(@Autowired MockMvc mockMvc) throws Exception {
        //添加测试Task
        Task task = new Task();
        task.setTitle("修改任务单元测试");
        task.setContent("测试修改任务前");
        task.setSort(1001);
        taskMapper.insert(task);
        System.out.println("修改前 = " + task.toString());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", task.getId() + "");
        jsonObject.put("title", "修改任务单元测试");
        jsonObject.put("content", "测试修改任务后");
        //修改任务
        mockMvc.perform(MockMvcRequestBuilders.post("/task/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();
        Task task2 = taskMapper.selectById(task.getId());
        System.out.println("修改后 = " + task2.toString());
        //判断修改前后JSON字符是否有变化
        Assertions.assertNotEquals(task.getContent(), task2.getContent());
    }

    @Test
    void endTask(@Autowired MockMvc mvc) throws Exception {
        //添加测试对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = new Date(6000);
        String format = dateFormat.format(date);
        Task task = new Task();
        task.setTitle("任务完成时更新数据单元测试对象");
        task.setActime(date);
        taskMapper.insert(task);
        System.out.println("添加的测试对象: " + task);

        mvc.perform(MockMvcRequestBuilders.get("/task/endTask/" + task.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println("修改前: " + format);
        Task task1 = taskMapper.selectById(task.getId());
        Date actime = task1.getActime();
        String format1 = dateFormat.format(actime);
        System.out.println("修改后: " + format1);
        //判断修改前后JSON字符是否有变化
        Assertions.assertNotEquals(format, format1);

        //回收测试对象
        taskMapper.deleteById(task1.getId());
        System.out.println("删除的测试对象: " + task1);
    }

    /* 修改测试结束 <上-下> 查找测试开始 */
    @Test
    void all(@Autowired MockMvc mvc) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/task/all"))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString();//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]

    }

    @Test
    void findById(@Autowired MockMvc mvc) throws Exception {
        //添加测试Task
        Task task = new Task();
        task.setTitle("根据id返回Task单元测试");
        taskMapper.insert(task);

        //模拟浏览器请求
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/task/findById/" + task.getId()))//模拟通过get方式访问网址 /user/findById/1,查询id为1的用户
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(task.getId())) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("根据id返回Task单元测试")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);//获得返回的json字符串
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.length() > 0);//期望返回的字符串不是空，至少有个[]

        //回收测试Task
        taskMapper.deleteById(task.getId());
    }

    @Test
    void findByName(@Autowired MockMvc mvc) throws Exception {
        Task task1 = new Task();
        task1.setTitle("条件查询单元测试对象");
        taskMapper.insert(task1);
        Task task2 = new Task();
        task2.setTitle("条件查询单元测试对象");
        task2.setSort(1002);
        taskMapper.insert(task2);
        //查询
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/task/findByName/条件查询单元测试对象"))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);//获得返回的json字符串
        JSONObject jsonObject = new JSONObject(contentAsString);
        String string = jsonObject.getString("data");
        JSONArray jsonArray = new JSONArray(string);
        System.out.println(jsonArray);
        //由于添加了两个测试对象,所以数组返回值期望应该大于1
        Assertions.assertTrue(jsonArray.length() > 1);

    }

    @Test
    void findByFlow(@Autowired MockMvc mvc) throws Exception {
        //添加一个flow两个task用于测试关联查询
        Flow flow = new Flow();
        flow.setWorkname("测试根据工作流关联查询");
        flow.setCategory("测试根据工作流关联查询");
        flow.setCapacity(100);
        flow.setSort(1000);
        flowMapper.insert(flow);
        Task task = new Task();
        task.setTitle("测试关联查询");
        task.setSort(1001);
        task.setFlowid(flow.getId());
        Task task1 = new Task();
        task1.setSort(1002);
        task1.setTitle("测试关联查询");
        task1.setFlowid(flow.getId());
        taskMapper.insert(task);
        taskMapper.insert(task1);
        taskMapper.insertFlowTask(flow.getId(), task.getId());
        taskMapper.insertFlowTask(flow.getId(), task1.getId());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/task/findByFlow/" + flow.getId()))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("true"))
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);//获得返回的json字符串
        JSONObject jsonObject = new JSONObject(contentAsString);
        String string = jsonObject.getString("data");
        JSONArray jsonArray = new JSONArray(string);
        System.out.println(jsonArray);
        //添加了两个关联任务,返回数组应当大于1;
        Assertions.assertTrue(jsonArray.length() > 1);
    }

    @Test
    void currentUserTasks(@Autowired MockMvc mvc) throws Exception {
        //添加一个user两个task用于测试关联查询
        Task task = new Task();
        task.setTitle("测试根据用户关联查询");
        task.setSort(1001);
        Task task1 = new Task();
        task1.setTitle("测试根据用户关联查询");
        task1.setSort(1002);
        User user = new User();
        user.setName("测试根据用户关联查询");
        userMapper.insert(user);
        taskMapper.insert(task);
        taskMapper.insert(task1);
        taskMapper.insertUserTask(user.getId(), task.getId());
        taskMapper.insertUserTask(user.getId(), task1.getId());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/task/currentUserTasks/" + user.getId()))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("true"))
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);//获得返回的json字符串
        JSONObject jsonObject = new JSONObject(contentAsString);
        String string = jsonObject.getString("data");
        JSONArray jsonArray = new JSONArray(string);
        System.out.println(jsonArray);
        //添加了两个关联任务,返回数组应当大于1;
        Assertions.assertTrue(jsonArray.length() > 1);
    }
}