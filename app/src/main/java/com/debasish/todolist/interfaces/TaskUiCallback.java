package com.debasish.todolist.interfaces;

public interface TaskUiCallback {
    void refreshUi(boolean status);
    void onCancelClicked();
}
