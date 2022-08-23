package com.kanban.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@TableName(value = "tb_user")//指定表名
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String jobno;
    @NonNull
    private String position;

    private String headimg;

    @TableField(exist = false) //不是用户表的字段
    private List<Story> storyList;
    @TableField(exist = false) //不是用户表的字段
    private List<Task>  taskList;
}
