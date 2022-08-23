package com.kanban.controller;

import com.kanban.bean.Msg;
import com.kanban.entity.Flow;
import com.kanban.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow")
public class FlowController {
    @Autowired
    FlowService flowService;

    //查询所有
    @GetMapping("/all")
    public Msg all() {
        return flowService.queryAll();
    }

    //主键查找
    @GetMapping("/findById/{id}")
    public Msg findById(@PathVariable int id) {
        return flowService.findById(id);
    }

    //添加工作流
    @PostMapping("/save")
    public Msg save(@RequestBody Flow flow) {
        return flowService.insert(flow);
    }

    //修改工作流
    @PostMapping("/update")
    public Msg update(@RequestBody Flow flow) {
        return flowService.update(flow);
    }

    //删除工作流
    @GetMapping("/delete/{id}")
    public Msg delete(@PathVariable int id) {
        return flowService.delete(id);
    }

    //全局查找工作流
    @GetMapping("/findByName/{flow}")
    public Msg findByName(@PathVariable String flow) {
        return flowService.findByName(flow);
    }
}
