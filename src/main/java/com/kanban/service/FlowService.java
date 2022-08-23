package com.kanban.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kanban.bean.Msg;
import com.kanban.entity.Flow;
import com.kanban.mapper.FlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FlowService {
    @Autowired
    FlowMapper flowMapper;

    //查询全部
    public Msg queryAll() {
        QueryWrapper<Flow> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by sort asc");
        Msg msg = new Msg();
        msg.setData(flowMapper.selectList(queryWrapper));
        msg.setResult(true);
        msg.setMessage("获取成功");
        return msg;
    }

    //主键查找
    public Msg findById(int id) {
        Msg msg = new Msg();
        msg.setData(flowMapper.selectById(id));
        msg.setResult(true);
        msg.setMessage("获取成功");
        return msg;
    }

    //添加
    public Msg insert(Flow flow) {
        Msg msg = new Msg();
        flowMapper.insert(flow);
        msg.setData(flow);
        msg.setResult(true);
        msg.setMessage("获取成功");
        return msg;
    }

    //修改
    public Msg update(Flow flow) {
        Msg msg = new Msg();
        msg.setData(flowMapper.updateById(flow));
        msg.setResult(true);
        msg.setMessage("修改成功");
        return msg;
    }

    //删除
    public Msg delete(int id) {
        Msg msg = new Msg();
        msg.setData(flowMapper.deleteById(id));
        msg.setResult(true);
        msg.setMessage("删除成功");
        return msg;
    }

    //查找
    public Msg findByName(String flow) {
        QueryWrapper<Flow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workname", flow).or()
                .eq("category", flow).or()
                .eq("workname", flow);
        queryWrapper.last("order by sort ASC");
        Msg msg = new Msg();
        msg.setData(flowMapper.selectList(queryWrapper));
        msg.setResult(true);
        msg.setMessage("获取成功");
        return msg;
    }

}