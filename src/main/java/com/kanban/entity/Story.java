package com.kanban.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Resources;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0.0
 * @time 2022/6/21 18:18
 * @Author SmallWatermelon
 */

@Data
@Builder
@TableName(value = "tb_story")//指定表名
@AllArgsConstructor
@RequiredArgsConstructor    //为不允许为空的字段创建构造方法
@NoArgsConstructor
public class Story implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)

    @NonNull    //java中不可为空
    private Integer id;


    private String title;


    private String originator;


    private Integer personInCharge;


    private String content;

    private Integer estimationPoint;

    private Integer flowId;

    private String state;

    @TableField(exist = false)
    private String headimg;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date releaseTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date estComTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date actComTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;


    private Integer sort;

    @TableField(exist = false)
    private List<Task> taskList;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Flow flow;
}
