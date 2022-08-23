package com.kanban.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kanban.bean.Msg;
import com.kanban.entity.Story;
import com.kanban.mapper.StoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class StoryService {
    @Autowired
    StoryMapper storyMapper;


    /**
     * 查询全部任务
     * @return
     */
    public Msg queryAll() {
        Msg msg = new Msg();
        QueryWrapper<Story> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by sort asc");
        List<Story> stories = storyMapper.selectAllStory(queryWrapper);
        if (stories == null) {
            msg.setResult(false);
            msg.setMessage("无信息");
            msg.setData(null);
        } else {
            msg.setResult(true);
            msg.setMessage("查询成功！");
            msg.setData(stories);
        }
        return msg;
    }

    //根据Id查找
    public Msg findById(int id) {
        Msg msg = new Msg();
        Story storyId = storyMapper.selectStoryById(id);
        if (storyId == null) {
            msg.setResult(false);
            msg.setMessage("无信息");
            msg.setData(null);
        } else {
            msg.setResult(true);
            msg.setMessage("查询成功！");
            msg.setData(storyId);
        }
        return msg;
    }

    //添加
    public Msg insert(Story story) {
        Msg msg = new Msg();
        int insert = storyMapper.insert(story);
        msg.setResult(true);
        msg.setMessage("插入成功！");
        msg.setData(insert);
        return msg;
    }

    //修改
    public Msg update(Story story) {
        Msg msg = new Msg();
        int updateById = storyMapper.updateById(story);
        msg.setResult(true);
        msg.setMessage("更新成功！");
        msg.setData(updateById);
        return msg;
    }

    //删除
    public Msg delete(int id) {
        Msg msg = new Msg();
        int deleteById = storyMapper.deleteById(id);
        msg.setResult(true);
        msg.setMessage("删除成功！");
        msg.setData(deleteById);
        return msg;
    }



    /**
     * 条件查询
     */
    public Msg findByName(String story) {
        Msg msg = new Msg();
        QueryWrapper<Story> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("content", story).or()
                .eq("title", story).or()
                .eq("originator", story).or()
                .eq("person_in_charge", story).or()
                .eq("estimation_point", story).or()
                .eq("state", story);
        List<Story> stories = storyMapper.selectList(queryWrapper);
        if (stories == null) {
            msg.setResult(false);
            msg.setMessage("无信息");
            msg.setData(null);
        } else {
            msg.setResult(true);
            msg.setMessage("查询成功！");
            msg.setData(stories);
        }
        return msg;
    }

    /**
     * 分页查询
     */
    public Msg findPage(Integer pageNum, Integer pageSize, String search) {
        Msg msg = new Msg();
        LambdaQueryWrapper<Story> wrapper = Wrappers.<Story>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Story::getTitle, search);
        }
        Page<Story> storyPage = storyMapper.selectPage2(new Page<>(pageNum, pageSize), wrapper);
        msg.setData(storyPage);
        return msg;
    }
}
