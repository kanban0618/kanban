package com.kanban.service;

import com.kanban.bean.Msg;
import com.kanban.entity.User;
import com.kanban.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class LoginService  {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    //登录检查
     public Msg doGet(User user){
         Msg msg = new Msg();
         User userExist = userService.findUserName(user.getUsername());
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

    //    判断用户是否存在
    public Msg findusername(@PathVariable String username){
        Msg msg = new Msg();
        msg.setResult(userMapper.selectfindusername(username) !=null );
        return msg;
    }

}




