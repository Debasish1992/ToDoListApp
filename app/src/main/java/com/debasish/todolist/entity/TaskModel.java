package com.debasish.todolist.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaskModel extends RealmObject {

    @PrimaryKey
    String taskId;
    String taskTitle;
    String taskDesc;
    int taskCompleteStatus;

    public TaskModel(){

    }

    public TaskModel(String taskId, String taskTitle, String taskDesc, int taskCompleteStatus) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.taskCompleteStatus = taskCompleteStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getTaskCompleteStatus() {
        return taskCompleteStatus;
    }

    public void setTaskCompleteStatus(int taskCompleteStatus) {
        this.taskCompleteStatus = taskCompleteStatus;
    }
}
