package com.kanban.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
@Data
@Builder
@TableName(value = "r_user_task")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserTask implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NonNull
    private int userId;
    @NonNull
    private int taskId;
}
