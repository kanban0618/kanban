package com.kanban.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kanban.bean.Msg;
import com.kanban.entity.Story;
import com.kanban.mapper.StoryMapper;
import com.kanban.mapper.UserMapper;
import com.kanban.service.StoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/story")
@RestController
@Api("故事控制器")
public class StoryController {
    
    @Autowired
    StoryService storyService;

    @Autowired
    StoryMapper storyMapper;

    @GetMapping("/all")
    @ApiOperation("查询全部")
    public Msg all(){
        return storyService.queryAll();
    }

    @GetMapping("/findById/{id}")
    @ApiOperation("根据Id查找")
    public Msg findById(@PathVariable int id){
        return storyService.findById(id);
    }

    @PostMapping("/save")
    @ApiOperation("添加")
    public Msg save(@RequestBody Story story){
        return storyService.insert(story);
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public Msg update(@RequestBody Story story) {
        return storyService.update(story);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除")
    public Msg delete(@PathVariable int id){
        return storyService.delete(id);
    }

    /*@GetMapping("/delete2")
    public List<Story> delete2(){
        List<Story> list = storyService.findByName();
        storyService.delete2();
        return list;
    }*/

    @GetMapping("/findByName/{story}")
    public Msg findByName(@PathVariable String story){
        return storyService.findByName(story);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Msg findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "") String search) {
        return storyService.findPage(pageNum, pageSize, search);
    }
}


/**
 * 参考：
 * 1. https://blog.csdn.net/liuming690452074/article/details/125236755?csdn_share_tail=%7B%22type%22%3A%22blog%22%2C%22rType%22%3A%22article%22%2C%22rId%22%3A%22125236755%22%2C%22source%22%3A%22qq_58350711%22%7D&ctrtid=DrEm4 (热部署)
 * 2. https://www.bilibili.com/video/BV14y4y1M7Nc?p=3&spm_id_from=333.1007.top_right_bar_window_history.content.click (分页查询)
 */