package com.kanban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kanban.bean.Msg;
import com.kanban.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper //表明这是Mapper，也可以在启动类上加上包扫描
//Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_user where id=#{id}")
    @Results({
            @Result(column = "id", property = "id",id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "jobno", property = "jobno"),
            @Result(column = "position", property = "position"),
            @Result(property = "storyList", column = "id", many = @Many(select = "com.kanban.mapper.StoryMapper.selectRelationByUserId")),
            @Result(property = "taskList", column = "id", many = @Many(select = "com.kanban.mapper.TaskMapper.selectRelationByUserId")),
    })
    User selectUserById(@Param("id") int id);

    @Insert("insert into user(username,password) value (#{username},#{password})")
    User selectfindBy(@Param("username")String username,@Param("password")String password);

    @Select("select * from tb_user where username = #{username}")
    User selectfindusername(@Param("username")String username);

    @Select ("select password from tb_user where username = #{username}")
    User selectfindpassword(@Param("username")String username);

    @Select ("select * from tb_user where id in (select user_id from r_story_user where story_id=#{storyId})")
    User selectRelationAllStory();

    @Select ("select * from tb_user where id in (select user_id from r_story_user where story_id=#{storyId})")
    User selectRelationByStoryId();
}
