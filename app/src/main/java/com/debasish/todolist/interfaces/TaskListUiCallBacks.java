package com.debasish.todolist.interfaces;

import com.debasish.todolist.entity.TaskModel;

import io.realm.RealmResults;

public interface TaskListUiCallBacks {
    void populateRecyclerView(boolean status, RealmResults<TaskModel> taskModelRealmResult);
    String getSearchedToken();
}
