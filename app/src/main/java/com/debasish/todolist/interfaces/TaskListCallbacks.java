package com.debasish.todolist.interfaces;

import com.debasish.todolist.entity.TaskModel;

import io.realm.RealmResults;

public interface TaskListCallbacks {

    void onSuccessfulDataFetchedFromRealm(boolean status, RealmResults<TaskModel> TaskModels);
    void onSuccessfullyTaskStatusChanged(boolean status);
}
