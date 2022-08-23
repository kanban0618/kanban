package com.kanban.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kanban.bean.Msg;
import com.kanban.entity.Story;
import com.kanban.entity.User;
import com.kanban.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    //查询全部
    public Msg queryAll(){
        Msg msg = new Msg();
        msg.setData(userMapper.selectList(null));
        msg.setResult(true);
        msg.setMessage("成功");
        return msg;
    }
    //id查询
    public Msg findById(int id){
        Msg msg = new Msg();
        msg.setData(userMapper.selectUserById(id));
        if (msg.getData() ==null){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
        msg.setResult(true);
        msg.setMessage("成功");
        }
        return msg;
    }
    //添加
    public Msg insert(User user){
        Msg msg = new Msg();
        int in = userMapper.insert(user);
        msg.setData(user);
        if (in==0){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
            msg.setResult(true);
            msg.setMessage("成功");
        }

//        System.out.println(msg.getData());
        return msg;
    }
    //修改
    public Msg update(User user){
        Msg msg = new Msg();
        msg.setData(userMapper.updateById(user));
        msg.setResult(true);
        msg.setMessage("成功");
        return msg;
    }
    //删除
    public Msg delete(int id){
        Msg msg = new Msg();
        msg.setData(userMapper.deleteById(id));
        if ((int)msg.getData() ==0){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
            msg.setResult(true);
            msg.setMessage("成功");
        }
        return msg;
    }
    //删除指定姓名
    public Msg delete2(){
        Msg msg = new Msg();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "测试姓名");
        msg.setData(userMapper.deleteByMap(map));
        msg.setResult(true);
        msg.setMessage("成功");
        return msg;
    }
    //查询指定姓名
    public Msg findByName(){
        Msg msg = new Msg();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "测试姓名");
        List list =userMapper.selectByMap(map);
        msg.setData(list);
        if (list.size()<1){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
            msg.setResult(true);
            msg.setMessage("成功");
        }
        return msg;
    }
    //根据姓名查询
    public Msg findByUserName(@PathVariable String name){
        Msg msg = new Msg();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List list = userMapper.selectByMap(map);
        msg.setData(list);
        if (list.size()<1){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
            msg.setResult(true);
            msg.setMessage("成功");
        }
        return msg;
    }

    //    根据账号查找
    public Msg selectfindusername(@PathVariable String username){
        Msg msg = new Msg();
        msg.setData(userMapper.selectfindusername(username));
        if (msg.getData() == null){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
            msg.setResult(true);
            msg.setMessage("成功");
        }
        return msg;
    }

    //    根据密码查找
    public Msg selectfindpassword(@PathVariable String password){
        Msg msg = new Msg();
        msg.setData(userMapper.selectfindpassword(password));
        if (msg.getData() == null){
            msg.setResult(false);
            msg.setMessage("失败");
        }else {
            msg.setResult(true);
            msg.setMessage("成功");
        }
        return msg;
    }

    //    根据账号和密码查找
//    public User selcetfindBy(String username , String password){
//        return userMapper.selectfindBy(username,password);
//    }
    public User findUserName(@PathVariable String username){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        List<User> users = userMapper.selectByMap(map);
        if (users.size()==0){
            return  null;
        }else {
            return users.get(0);
        }
    }


    //登录功能
    public Msg login(User user){//参数user是前端登录表单
        Msg msg = new Msg();
        User userExist = findUserName(user.getUsername());
        if (userExist==null){
            msg.setResult(false);
            msg.setMessage("用户名不存在");
            msg.setData(null);
        }else {
            if (userExist.getPassword().equals(user.getPassword())){
                msg.setResult(true);//成功
                msg.setMessage("登录成功");
                userExist.setPassword("******");//脱敏
                msg.setData(userExist);
            }else {
                msg.setResult(false);//失败
                msg.setMessage("登录失败，密码错误！");
                msg.setData(null);
            }
        }
        return msg;
    }

    /**
     * 分页查询
     */
    public Msg findPage(Integer pageNum, Integer pageSize, String search) {
        Msg msg = new Msg();
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        msg.setData(userPage);
        return msg;
    }

}

