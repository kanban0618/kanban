package com.kanban.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 方浩
 * 任务实体类
 */
@Data
@Builder
@TableName(value = "tb_task")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Task implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @NonNull
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String po;
    @NonNull
    private String sponsor;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
    private Date publishtime;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
    private Date ectime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
    private Date actime;
    @NonNull
    private int storyid;
    //故事表id,外键
    @NonNull
    private int estimate;
    //估算点
    @NonNull
    private int flowid;
    //工作流id
    @NonNull
    private String state;
    //排序
    @NonNull
    private int sort;

    //工作流
    @TableField(exist = false)
    public Flow flow;
    @TableField(exist = false)
    public List<User> sponsorList;


}
