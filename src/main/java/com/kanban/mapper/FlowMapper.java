package com.kanban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kanban.entity.Flow;
import org.apache.ibatis.annotations.*;



@Mapper //表明这是Mapper，也可以在启动类上加上包扫描
//Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
public interface FlowMapper extends BaseMapper<Flow> {

    //多对一关联查询：通过ID查询故事中包含工作流对象
    @Select("select * from tb_flow where id in (select flow_id from r_story_flow where story_id=#{storyId})")
    Flow selectRelationByStoryId(@Param("storyId") int storyId);

    @Select("select * from tb_flow where id in (select flow_id from r_flow_task where task_id=#{taskId})")
    Flow selectRelationByTaskId(@Param("taskId") int taskId);


    @Select("SELECT * FROM `tb_flow` WHERE id = #{id}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "workname", property = "workname"),
                    @Result(column = "category", property = "category"),
                    @Result(column = "capacity", property = "capacity"),
                    @Result(column = "sort", property = "sort"),
                    @Result(property = "storyList", column = "id", many = @Many(select = "com.kanban.mapper.StoryMapper.selectRelationByFlowId")),//一对多故事StoryMapper.selectRelationByFlowId
                    @Result(property = "taskList", column = "id", many = @Many(select = "com.kanban.mapper.TaskMapper.selectRelationByFlowId"))//一对多任务TaskMapper.selectRelationByFlowId
            }
    )
    Flow selectById(@Param("id") int id);

}
