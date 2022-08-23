package com.kanban.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@TableName(value = "tb_rule")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rule implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private int userid;
    private String url;
    private boolean token;
}
