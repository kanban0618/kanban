package com.kanban.controller;

import com.kanban.entity.User;
import com.kanban.mapper.UserMapper;
import com.kanban.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    UserController userController;
    @Autowired
    UserMapper userMapper;


    @Test
    void all(@Autowired MockMvc mvc) throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/all"))//模拟通过get方式访问网址 /user/all
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString();//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void findById(@Autowired MockMvc mvc) throws Exception{
        User user = new User();
        user.setUsername("TT");
        JSONObject object = new JSONObject();
        userMapper.insert(user);
//        object.put("id",user.getId());
//        object.put("name","TT");
        //注意这里的测试不够敏捷，万一id为1的用户被删了就会测试失败，以后会重构该方法，改为先添加后查询，确保查询的时候id是存在的。
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/findById/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString())
        )//模拟通过get方式访问网址 /user/findById/1,查询id为1的用户
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(user.getId())) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
//                .andExpect(MockMvcResultMatchers.jsonPath("$name").value("测试王五")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void save(@Autowired MockMvc mvc) throws Exception{
        User user = new User();
        JSONObject object = new JSONObject();
//        object.put("id",user.getId()+"");
        object.put("name","测试姓名");
        object.put("username","测试账号");
        object.put("password","测试密码");
        object.put("jobno","测试工号");
        object.put("position","测试职位");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("测试姓名"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("测试账号"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("测试密码"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.jobno").value("测试工号"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.position").value("测试职位"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("测试账号")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void update(@Autowired MockMvc mvc) throws Exception {
        User user = new User();
        user.setName("测试姓名前");
        user.setUsername("测试账号前");
        userMapper.insert(user);
        JSONObject object = new JSONObject();
        object.put("id",user.getId()+"");
        object.put("name","测试姓名后");
        object.put("username","测试账号后");

        MvcResult updatemvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("测试账号后"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andReturn();
        String updatecontentAsString = updatemvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
//        JSONObject object2 = new JSONObject(updatecontentAsString);
//        String username = (String)object2.get("username");
//        Assertions.assertTrue(!(username.equals(user.getUsername())));
//        userMapper.deleteById(user.getId());
        Assertions.assertTrue(updatecontentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
        }

    @Test
    void testUpdate(@Autowired MockMvc mvc) throws Exception{
        User user = new User();
        user.setName("测试修改");
        userMapper.insert(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",user.getId());
        jsonObject.put("name","测试修改");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/update/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
//        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
//        JSONObject object = new JSONObject(contentAsString);
//        int id = (int) object.get("id");
//        userMapper.deleteById(id);
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }

    @Test
    void delete(@Autowired MockMvc mvc) throws Exception{
        User user = new User();
        user.setName("删除测试");
        userMapper.insert(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/delete/"+user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("1")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
//                .andExpect(MockMvcResultMatchers.jsonPath("$name").value("测试姓名")) //例子参考：http://cn.voidcc.com/question/p-atjxxegq-bmb.html
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]

    }

//    @Test
//    void delete2() {
//    }
//
//    @Test
//    void findByName() {
//    }

    @Test
    void findByUserName(@Autowired MockMvc mvc) throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/findByUserName/测试"))
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andReturn();//将返回保存到mvcResult对象中
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]

    }

    @Test
    void loginShouldSuccess(@Autowired MockMvc mvc) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "cs");
        jsonObject.put("password", "123");
        System.out.println("jsonObject.toString() = " + jsonObject.toString());
        MvcResult savemvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/login")
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
    void loginShouldFail(@Autowired MockMvc mvc) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "cs");
        jsonObject.put("password", "1234");
        System.out.println("jsonObject.toString() = " + jsonObject.toString());
        MvcResult savemvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(false)) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("登录失败，密码错误！")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andReturn();
        String contentAsString = savemvcResult.getResponse().getContentAsString(Charset.forName("utf-8"));//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]
    }


    private static MockHttpSession sessionPub;
    @BeforeAll
    public static void setupMockMvc() {
        sessionPub = new MockHttpSession();
        sessionPub.setAttribute("LOGIN_USER","GG");
    }
    @Test
    void currentUser(@Autowired MockMvc mvc) throws  Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/currentUser")
                .session(sessionPub)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )

                .andExpect(MockMvcResultMatchers.status().isOk()) //期望这次访问返回的状态是200（ok）的
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//期望这次访问返回的数据是json格式的
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true)) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("获取成功！")) //jsonPath语法参考：https://blog.csdn.net/weixin_37794119/article/details/81484885
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();//获得返回的json字符串
        Assertions.assertTrue(contentAsString.length()>0);//期望返回的字符串不是空，至少有个[]

    }
}