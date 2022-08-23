package com.kanban.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    Object data;//数据域, 可以存放User，Flow，Task，Story, List<Rule>
    boolean result;//查询结果是成功还是失败,true表示成功，false表示失败
    String message;//反馈的消息, success:执行成功， fail:执行失败, 注册用户已存在, 用户名非法, 工作流已存在
}
