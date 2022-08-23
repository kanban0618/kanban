package com.kanban.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kanban.bean.Msg;
import com.kanban.entity.Task;
import com.kanban.mapper.FlowMapper;
import com.kanban.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
//service将当前类自动注入spring容器
public class TaskService {
    @Autowired(required = false)
    TaskMapper taskMapper;
    @Autowired
    FlowMapper flowMapper;

    //查询全部任务
    public Msg queryAll() {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by sort asc");
        Msg msg = new Msg();
        msg.setData(taskMapper.selectTaskList());
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //查询全部任务
    public Msg count() {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by sort asc");
        Msg msg = new Msg();
        msg.setData(taskMapper.selectCount(queryWrapper));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //查询已完成任务
    public Msg activeCount() {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where state = '已完成'");
        Msg msg = new Msg();
        msg.setData(taskMapper.selectCount(queryWrapper));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //查询已完成任务
    public Msg finishedCount() {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where state != '已完成'");
        Msg msg = new Msg();
        msg.setData(taskMapper.selectCount(queryWrapper));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //根据id查询
    public Msg findById(int id) {
        Msg msg = new Msg();
        msg.setData(taskMapper.selectById(id));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //插入一条记录
    public Msg insert(Task task) {
        Msg msg = new Msg();
        try {
            int i = taskMapper.insert(task);
            if (i==0){
                msg.setData(null);
                msg.setResult(false);
                msg.setMessage("添加失败");
            }else{
                msg.setData(task);
                msg.setResult(true);
                msg.setMessage("添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

    //修改一条记录
    public Msg update(Task task) {
        Msg msg = new Msg();
        try {
            int i = taskMapper.updateById(task);
            if (i==0){
                msg.setData(null);
                msg.setResult(false);
                msg.setMessage("修改失败");
            }else {
                msg.setData(task);
                msg.setResult(true);
                msg.setMessage("修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public Msg delete(int id) {
        Msg msg = new Msg();
        msg.setData(taskMapper.deleteById(id));
        msg.setResult(true);
        msg.setMessage("删除成功");
        return msg;
    }

    public int delete2() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        return taskMapper.deleteByMap(map);
    }

    public Msg findByName(String task) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", task).or()
                .eq("content", task).or()
                .eq("po", task).or()
                .eq("sponsor", task).or()
                .eq("state", task);
        queryWrapper.last("order by sort ASC");
        Msg msg = new Msg();
//        msg.setData(taskMapper.selectList(queryWrapper));
        msg.setData(taskMapper.selectByFuzzyQuery(task));
        msg.setResult(true);
        msg.setMessage("查询成功");
        System.out.println(msg.toString());
        return msg;
    }

    public Msg findByFlow(int flowId) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flowid", flowId);
        Msg msg = new Msg();
        msg.setData(taskMapper.selectList(queryWrapper));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    public Msg findByUser(int user) {
        Msg msg = new Msg();
        msg.setData(taskMapper.selectRelationByUserId(user));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }


}
