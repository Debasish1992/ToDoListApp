package com.debasish.todolist.entity;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class TaskEntity extends BaseObservable {

    @Bindable
    String taskTitle;

    @Bindable
    String taskDesc;

    public String getTaskTitle() {
        if(TextUtils.isEmpty(taskTitle))
            taskTitle = "";
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        //notifyPropertyChanged(BR.taskTitle);

    }

    public String getTaskDesc() {
        if(TextUtils.isEmpty(taskDesc))
            taskDesc = "";
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
        //notifyPropertyChanged(BR.taskDesc);
    }
}
