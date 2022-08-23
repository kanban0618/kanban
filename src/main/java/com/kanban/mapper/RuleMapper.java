package com.kanban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kanban.entity.Rule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RuleMapper extends BaseMapper<Rule> {
}
