package com.kanban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kanban.entity.User;
import com.kanban.entity.UserTask;
import org.apache.ibatis.annotations.*;

@Mapper //表明这是Mapper，也可以在启动类上加上包扫描
//Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
public interface UserTaskMapper extends BaseMapper<UserTask> {

    @Insert("insert into r_user_task(user_id,task_id) values(#{user_id},#{task_id})")
    int insert(@Param("user_id")int userId,@Param("task_id")int taskId);

}
