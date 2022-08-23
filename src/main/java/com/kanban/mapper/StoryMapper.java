package com.kanban.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kanban.entity.Story;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @version 1.0.0
 * @time 2022/6/21 18:34
 * @Author SmallWatermelon
 */

@Mapper
public interface StoryMapper extends BaseMapper<Story> {

    @Select("select * from tb_story where id in (select story_id from r_user_story where user_id=#{userId})")
    List<Story> selectRelationByUserId(@Param("userId") int userId);

    /**
     * 一对多查询故事ID
     * @param flowId
     * @return
     */
    @Select("select * from tb_story where flow_id=#{flowId}")
    List<Story> selectRelationByFlowId(@Param("flowId") int flowId);

    @Select("select * from tb_story where id=#{id}")
    @Results({  //返回设置
            @Result(column = "id", property = "id",id = true),
            @Result(column = "content", property = "content"),
            @Result(column = "title", property = "title"),
            @Result(column = "originator", property = "originator"),
            @Result(column = "est_com_time", property = "estComTime"),
            @Result(column = "person_in_charge", property = "personInCharge"),
            @Result(column = "estimation_point", property = "estimationPoint"),
            @Result(column = "act_com_time", property = "actComTime"),
            @Result(column = "state", property = "state"),
            @Result(column = "release_time", property = "releaseTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "flow_id", property = "flowId"),
            //一对多关联查询：通过ID查询故事中包含所有任务集合属性taskList
            @Result(property = "taskList", column = "id", many = @Many(select = "com.kanban.mapper.TaskMapper.selectRelationByStoryId")),
            //多对一关联查询：通过ID查询故事中包含负责人用户对象user
            @Result(property = "user", column = "id", one = @One(select = "com.kanban.mapper.UserMapper.selectRelationByStoryId")),
            //多对一关联查询：通过ID查询故事中包含工作流对象
            @Result(property = "flow", column = "id", one = @One(select = "com.kanban.mapper.FlowMapper.selectRelationByStoryId"))
    })
    Story selectStoryById(@Param("id") int id);

    @Select("select * from tb_story")
    @Results({  //返回设置
            @Result(column = "id", property = "id",id = true),
            @Result(column = "content", property = "content"),
            @Result(column = "title", property = "title"),
            @Result(column = "originator", property = "originator"),
            @Result(column = "est_com_time", property = "estComTime"),
            @Result(column = "person_in_charge", property = "personInCharge"),
            @Result(column = "estimation_point", property = "estimationPoint"),
            @Result(column = "act_com_time", property = "actComTime"),
            @Result(column = "state", property = "state"),
            @Result(column = "release_time", property = "releaseTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "flow_id", property = "flowId"),

            @Result(property = "user", column = "id", one = @One(select = "com.kanban.mapper.UserMapper.selectRelationAllStory"))
    })
    Page<Story> selectPage0(Page page, Wrapper<Story> queryWrapper);

    @Select("SELECT * FROM tb_story,tb_user WHERE tb_story.person_in_charge=tb_user.id")
    Page<Story> selectPage2(Page page, Wrapper<Story> queryWrapper);

    @Select("select * from tb_story")
    @Results({  //返回设置
            @Result(column = "id", property = "id",id = true),
            @Result(column = "content", property = "content"),
            @Result(column = "title", property = "title"),
            @Result(column = "originator", property = "originator"),
            @Result(column = "est_com_time", property = "estComTime"),
            @Result(column = "person_in_charge", property = "personInCharge"),
            @Result(column = "estimation_point", property = "estimationPoint"),
            @Result(column = "act_com_time", property = "actComTime"),
            @Result(column = "state", property = "state"),
            @Result(column = "release_time", property = "releaseTime"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "flow_id", property = "flowId"),
            @Result(property = "user", column = "id", many = @Many(select = "com.kanban.mapper.UserMapper.selectRelationAllStory"))
    })
    List<Story> selectAllStory2(QueryWrapper<Story> queryWrapper);

    @Select("SELECT * FROM tb_story,tb_user WHERE tb_story.person_in_charge=tb_user.id")
    List<Story> selectAllStory(QueryWrapper<Story> queryWrapper);
}
