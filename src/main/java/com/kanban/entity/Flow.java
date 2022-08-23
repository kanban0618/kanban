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
@TableName(value = "tb_flow")//指定表名
public class Flow implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @NonNull
    private Integer id;
    @NonNull
    private String workname;//工作流名称
    @NonNull
    private String category;//类别
    @NonNull
    private int capacity;//在制品限制
    @NonNull
    private int sort;//排序
    @TableField(exist = false)
    public List<Story> storyList;//故事集
    @TableField(exist = false)
    public List<Task> taskList;//任务集

    public Flow() {
    }

    public Flow(@NonNull Integer id, @NonNull String workname, @NonNull String category, @NonNull int capacity, @NonNull int sort, List<Story> storyList, List<Task> taskList) {
        this.id = id;
        this.workname = workname;
        this.category = category;
        this.capacity = capacity;
        this.sort = sort;
        this.storyList = storyList;
        this.taskList = taskList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
