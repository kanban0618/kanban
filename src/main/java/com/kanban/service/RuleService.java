package com.kanban.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kanban.bean.Msg;
import com.kanban.entity.Rule;
import com.kanban.entity.Task;
import com.kanban.mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {
    @Autowired
    RuleMapper ruleMapper;

    //查询全部记录
    public Msg queryAll() {
        Msg msg = new Msg();
        msg.setData(ruleMapper.selectList(null));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //根据id查询
    public Msg findById(int id) {
        Msg msg = new Msg();
        msg.setData(ruleMapper.selectById(id));
        msg.setResult(true);
        msg.setMessage("查询成功");
        return msg;
    }

    //插入一条记录
    public Msg insert(Rule rule) {
        Msg msg = new Msg();
        int insert = ruleMapper.insert(rule);
        msg.setData(rule);
        msg.setResult(insert == 1);
        msg.setMessage(insert == 1 ? "添加成功" : "添加失败");
        return msg;
    }

    //修改一条记录
    public Msg update(Rule rule) {
        Msg msg = new Msg();
        msg.setData(ruleMapper.updateById(rule));
        msg.setResult(true);
        msg.setMessage("修改成功");
        return msg;
    }

    //删除一条记录
    public Msg delete(int id) {
        Msg msg = new Msg();
        msg.setData(ruleMapper.deleteById(id));
        msg.setResult(true);
        msg.setMessage("删除成功");
        return msg;
    }

    public Msg findByUserId(int userId) {
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId);
        return getMsg(queryWrapper);
    }

    private Msg getMsg(QueryWrapper<Rule> queryWrapper) {
        Msg msg = new Msg();
        List<Rule> rules = ruleMapper.selectList(queryWrapper);
        msg.setData(rules);
        msg.setResult(rules.size()>=1 ? true : false);
        msg.setMessage(rules.size()>=1 ? "查询成功" : "查询失败");
        return msg;
    }

    public Msg fuzzyQuery(Object rule) {
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        System.out.println("传参: " + rule.toString());
        boolean bl;
        Boolean Boolean;
        if (rule.toString().equals("true") || rule.toString().equals("false")) {
            Boolean = new Boolean(rule.toString());
            bl = true;
        } else {
            bl = false;
            Boolean = new Boolean(null);
        }
        if (!bl) {
            queryWrapper.like("userid", rule).or()
                    .like("url", rule);
        } else {
            queryWrapper.like("userid", rule).or()
                    .like("url", rule).or()
                    .eq("token", Boolean);
        }

        return getMsg(queryWrapper);
    }

//    public Msg fuzzyQuery(Object rule) {
//        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
//        System.out.println("传参: " + rule.toString());
//        queryWrapper.like("userid", rule).or()
//                .like("url", rule).or()
//                .like("token", rule);
//
//        Msg msg = new Msg();
//        msg.setData(ruleMapper.selectList(queryWrapper));
//        msg.setResult(true);
//        msg.setMessage("查询成功");
//        return msg;
//    }

}
