package com.debasish.todolist.interfaces;

public interface TaskCallbacks {

    void onSuccessfulTaskSavedToDb(boolean status, Exception ex);
}
