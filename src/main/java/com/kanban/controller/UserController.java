package com.kanban.controller;

import com.kanban.bean.Msg;
import com.kanban.entity.User;
import com.kanban.mapper.UserMapper;
import com.kanban.service.LoginService;
import com.kanban.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/user")
@Api("用户控制器")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/all")
    @ApiOperation("查询所有用户")
    public Msg all(){
        return userService.queryAll();
    }

    @GetMapping("/findById/{id}")
    @ApiOperation("通过ID查询用户")
    public Msg findById(@PathVariable int id){
        return userService.findById(id);
    }

    @PostMapping("/save")
    @ApiOperation("保存用户")
    public Msg save(@RequestBody User user){
//        User user = User.builder().name("测试姓名").username("123").password("123").jobno("1").position("测试职位").build();
        return userService.insert(user);
    }

    @PostMapping("/update")
    @ApiOperation("更新用户")
    public Msg update( @RequestBody User user){
        return userService.update(user);
    }

    @PostMapping("/update/{id}")
    @ApiOperation("根据id修改")
    public Msg findupdate(@RequestBody User user){
        Msg msg = new Msg();
        msg.setMessage("newName");
        msg.setData(userService.update(user));
        msg.setMessage("成功");
        return msg;
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("根据id删除")
    public Msg delete(@PathVariable int id){
        return userService.delete(id);
//        Msg msg = userService.findById(id);
//        msg.setData(userService.delete(id));
//        return msg;
    }
    @GetMapping("/delete2")
    @ApiOperation("删除固定姓名")
    public Msg delete2(){
        Msg msg = userService.findByName();
        msg.setData(userService.delete2());
        msg.setMessage("成功");
        return msg;
    }
    @GetMapping("/findByName")
    @ApiOperation("查询固定姓名")
    public Msg findByName(){
        return userService.findByName();
    }
    @GetMapping("/findByUserName/{name}")
    @ApiOperation("根据姓名查询用户")
    public Msg findByUserName(@PathVariable String name){
        return userService.findByUserName(name);
    }

    @GetMapping("/findUserName/{username}")
    @ApiOperation("根据用户名查询用户")
    public Msg findUserName(@PathVariable String username){
        return userService.selectfindusername(username);
    }

//    /existUsername
    
    @GetMapping("/findPswByName/{password}")
    @ApiOperation("根据用户查询密码")
    public Msg findPawByName(@PathVariable String password){
        return userService.selectfindpassword(password);
    }


    //    登录功能
    @PostMapping("/login")
    @ApiOperation("登录功能")
        public Msg login(@RequestBody User user){
            return userService.login(user);
        }

    //获取当前登录用户信息
    @GetMapping("/currentUser")
    @ApiOperation("获取当前登录用户信息")
    public Msg currentUser(HttpServletRequest request){
        Msg msg = new Msg();
        HttpSession session = request.getSession();
        msg.setData(session.getAttribute("LOGIN_USER"));
        System.out.println("读取的用户为:"+session.getAttribute("LOGIN_USER"));
        if (session.getAttribute("LOGIN_USER")==null){
            msg.setMessage("失败");
            msg.setResult(false);
            return msg;
        }else {
            msg.setMessage("成功");
            msg.setResult(true);
            return msg;
        }
    }

    /**
     * 1.文件保存在服务器，url地址保存在数据库
     * 上传成功之后返回成功保存的url地址
     */
    @PostMapping("/upload")
    @ApiOperation("上传头像")
    public @ResponseBody String upload(@RequestParam MultipartFile file, HttpServletRequest request){
        if(!file.isEmpty()){
            String uploadPath = "D:"+File.separator+"uploadFile"; //   /  \\
            // File.separator ==/ \

            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            String filename = UUID.randomUUID().toString() +suffixName;
            File localFile = new File(uploadPath+"\\"+filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                Msg msg = new Msg();
                HttpSession session = request.getSession();
                msg.setData(session.getAttribute("LOGIN_USER"));
                User user = (User) msg.getData();
                User user1 = userMapper.selectfindusername(user.getUsername());
                user1.setHeadimg("/uploadFile/"+filename);
                System.out.println(update(user1));
                session.setAttribute("LOGIN_USER",user1);
                /**
                 * 这里应该把filename保存到数据库,供前端访问时使用
                 */
                System.out.println("提交成功!");
                return "提交成功!";//上传成功，返回保存的文件地址
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("上传失败");
                return "上传失败!";
            }
        }else{
            System.out.println("文件为空");
            return "文件为空!";
        }
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Msg findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "") String search) {
        return userService.findPage(pageNum, pageSize, search);
    }
}
