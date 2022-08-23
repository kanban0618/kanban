package com.kanban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kanban.entity.Flow;
import com.kanban.entity.Story;
import com.kanban.entity.Task;
import com.kanban.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
//将接口提交给Spring管理
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    @Select("select * from tb_task where id=#{id}")
    @Results({
            @Result(column = "id", property = "id",id = true),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "po", property = "po"),
            @Result(column = "sponsor", property = "sponsor"),
            @Result(column = "publishtime", property = "publishtime"),
            @Result(column = "ectime", property = "ectime"),
            @Result(column = "actime", property = "actime"),
            @Result(column = "storyid", property = "storyid"),
            @Result(column = "estimate", property = "estimate"),
            @Result(column = "storyid", property = "storyid"),
            @Result(column = "flowid", property = "flowid"),
            @Result(column = "state", property = "state"),
            @Result(property = "flow", column = "id", many = @Many(select = "com.kanban.mapper.TaskMapper.selectFlowId")),
            @Result(property = "sponsorList",column = "id",many = @Many(select = "com.kanban.mapper.TaskMapper.selectUserId")),
            @Result(column = "sort", property = "sort"),
    })
    Task selectById(@Param("id") int id);


    @Select("select * from tb_task order by sort asc")
    @Results({
            @Result(column = "id", property = "id",id = true),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "po", property = "po"),
            @Result(column = "sponsor", property = "sponsor"),
            @Result(column = "publishtime", property = "publishtime"),
            @Result(column = "ectime", property = "ectime"),
            @Result(column = "actime", property = "actime"),
            @Result(column = "storyid", property = "storyid"),
            @Result(column = "estimate", property = "estimate"),
            @Result(column = "storyid", property = "storyid"),
            @Result(column = "flowid", property = "flowid"),
            @Result(column = "state", property = "state"),
            @Result(property = "flow", column = "id", many = @Many(select = "com.kanban.mapper.TaskMapper.selectFlowId")),
            @Result(property = "sponsorList",column = "id",many = @Many(select = "com.kanban.mapper.TaskMapper.selectUserHeadImgId")),
            @Result(column = "sort", property = "sort"),
    })
    List<Task> selectTaskList();
    //模糊查询
    @Select("select * from tb_task where id=#{term} or title=#{term} or content=#{term} or po=#{term} or sponsor=#{term} or state=#{term} order by sort asc")
    @Results({
            @Result(column = "id", property = "id",id = true),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "po", property = "po"),
            @Result(column = "sponsor", property = "sponsor"),
            @Result(column = "publishtime", property = "publishtime"),
            @Result(column = "ectime", property = "ectime"),
            @Result(column = "actime", property = "actime"),
            @Result(column = "storyid", property = "storyid"),
            @Result(column = "estimate", property = "estimate"),
            @Result(column = "storyid", property = "storyid"),
            @Result(column = "flowid", property = "flowid"),
            @Result(column = "state", property = "state"),
            @Result(property = "flow", column = "id", many = @Many(select = "com.kanban.mapper.TaskMapper.selectFlowId")),
            @Result(property = "sponsorList",column = "id",many = @Many(select = "com.kanban.mapper.TaskMapper.selectUserId")),
            @Result(column = "sort", property = "sort"),
    })
    List<Task> selectByFuzzyQuery(@Param("term") String term);

    //关联查询用户
    @Select("select * from tb_user where id in (select user_id from r_user_task where task_id=#{id})")
    List<User> selectUserId(@Param("id") int id);
    //关联查询用户头像
    @Select("select headimg from tb_user where id in (select user_id from r_user_task where task_id=#{id})")
    List<User> selectUserHeadImgId(@Param("id") int id);

    @Select("select * from tb_flow where id in (select flow_id from r_flow_task where task_id=#{id})")
    Flow selectFlowId(@Param("id") int id);

    //一对多查询任务ID
    @Select("select * from tb_task where flowid=#{flowId}")
    List<Task> selectRelationByFlowId(@Param("flowId") int flowId);

    @Select("select * from tb_task where id in (select task_id from r_user_task where user_id=#{userId})")
    List<Task> selectRelationByUserId(@Param("userId") int userId);

    @Insert("insert into r_user_task (user_id, task_id) values (#{userId}, #{taskId})")
    int insertUserTask(@Param("userId") int userId,@Param("taskId") int taskId);

    @Insert("insert into r_flow_task (flow_id, task_id) values (#{flowId}, #{taskId})")
    int insertFlowTask(@Param("flowId") int flowId,@Param("taskId") int taskId);

    //一对多关联查询：通过ID查询故事中包含所有任务集合属性taskList
    @Select("select * from tb_task where id in (select task_id from r_story_task where story_id=#{storyId})")
    List<Task> selectRelationByStoryId(@Param("storyId") int storyId);
}
