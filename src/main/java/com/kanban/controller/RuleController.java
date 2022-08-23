package com.kanban.controller;

import com.kanban.bean.Msg;
import com.kanban.entity.Rule;
import com.kanban.service.RuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rule")
@Api("令牌控制器")
public class RuleController {
    @Autowired
    RuleService ruleService;

    @GetMapping("/all")
    @ApiOperation("查询所有令牌")
    public Msg all() {
        return ruleService.queryAll();
    }

    @GetMapping("/findById/{id}")
    @ApiOperation("根据id查询令牌")
    public Msg findById(@PathVariable int id) {
        return ruleService.findById(id);
    }


    @GetMapping("/findByUserId/{id}")
    @ApiOperation("根据用户id查询令牌")
    public Msg findByUserId(@PathVariable int id) {
        return ruleService.findByUserId(id);
    }

    @PostMapping("/fuzzyQuery")
    @ApiOperation("模糊查询")
    public Msg fuzzyQuery(@RequestBody Object rule) {
        return ruleService.fuzzyQuery(rule);
    }

    @PostMapping("/save")
    @ApiOperation("添加令牌")
    public Msg save(@RequestBody Rule rule) {
        return ruleService.insert(rule);
    }

    @PostMapping("/update")
    @ApiOperation("修改令牌")
    public Msg update(@RequestBody Rule rule) {
        return ruleService.update(rule);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("根据id删除令牌")
    public Msg delete(@PathVariable int id) {
        return ruleService.delete(id);
    }

}
