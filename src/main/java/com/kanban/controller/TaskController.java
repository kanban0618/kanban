package com.kanban.controller;

import com.kanban.bean.Msg;
import com.kanban.entity.Task;
import com.kanban.mapper.UserTaskMapper;
import com.kanban.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
@Api("任务控制器")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserTaskMapper userTaskMapper;

    //查询所有
    @GetMapping("/all")
    @ApiOperation("查询所有任务")
    public Msg all() {
        return taskService.queryAll();
    }

    //查询所有任务数
    @GetMapping("/count")
    @ApiOperation("查询所有任务数")
    public Msg count() {
        return taskService.count();
    }

    //查询已完成任务
    @GetMapping("/activeCount")
    @ApiOperation("查询所有任务数")
    public Msg activeCount() {
        return taskService.activeCount();
    }

    //查询未完成任务
    @GetMapping("/finishedCount")
    @ApiOperation("查询所有任务数")
    public Msg finishedCount() {
        return taskService.finishedCount();
    }

    //根据id查询
    @GetMapping("/findById/{id}")
    @ApiOperation("根据id查询任务")
    public Msg findById(@PathVariable int id) {
        return taskService.findById(id);
    }

    //条件查找
    @GetMapping("/findByName/{task}")
    @ApiOperation("根据条件查询任务")
    public Msg findByName(@PathVariable String task) {
        return taskService.findByName(task);
    }

    @GetMapping("/findByFlow/{id}")
    @ApiOperation("根据工作流查找任务")
    public Msg findByFlow(@PathVariable int id) {
        return taskService.findByFlow(id);
    }

    @GetMapping("/currentUserTasks/{id}")
    @ApiOperation("根据用户查找归属任务")
    public Msg currentUserTasks(@PathVariable int id) {
        return taskService.findByUser(id);
    }


    //添加任务
    @PostMapping("/save")
    @ApiOperation("添加任务")
    public Msg save(@RequestBody Task task) {
        //获取工具
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        task.setPublishtime(date);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 2);
        task.setActime(calendar.getTime());

        return taskService.insert(task);
    }

    //修改
    @PostMapping("/update")
    @ApiOperation("修改任务")
    public Msg update(@RequestBody Task task) {
        return taskService.update(task);
    }
    //修改
    @GetMapping("/addPo/{userId}/{taskId}")
    @ApiOperation("给任务添加负责人")
    public Msg addPo(@PathVariable int userId, @PathVariable int taskId) {
        int insert = userTaskMapper.insert(userId, taskId);
        if (insert == 1) {
            return Msg.builder().result(true).build();
        }else {
            return Msg.builder().result(false).build();
        }
    }

    //完成任务更新完成时间
    @GetMapping("/endTask/{id}")
    @ApiOperation("更新任务完成时间")
    public Msg endTask(@PathVariable int id) {
        Msg msg = findById(id);
        Task task = (Task) msg.getData();
        task.setActime(new Date());
        task.setState("已完成");
        return update(task);
    }

    //删除
    @GetMapping("/delete/{id}")
    @ApiOperation("根据id删除任务")
    public Msg delete(@PathVariable int id) {
        return taskService.delete(id);
    }
//    @GetMapping("/delete2/")
//    public List<Task> delete2(String task) {
//        List<Task> list = taskService.findByName(task);
//        taskService.delete2();
//        return list;
//    }


}
