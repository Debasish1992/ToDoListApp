package com.debasish.todolist.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.debasish.todolist.data.RealmController;
import com.debasish.todolist.data.RealmManager;
import com.debasish.todolist.entity.TaskModel;
import com.debasish.todolist.interfaces.RefreshDataInAdapter;
import com.debasish.todolist.interfaces.TaskListCallbacks;
import com.debasish.todolist.interfaces.TaskListUiCallBacks;
import com.debasish.todolist.ui.TaskListScreen;

import io.realm.Realm;
import io.realm.RealmResults;

public class TaskListViewModel extends ViewModel implements TaskListCallbacks {

    Context taskListScreenContext;
    Realm realm;
    RealmManager realmManager = null;
    TaskListUiCallBacks taskListUiCallBacks;
    TaskListCallbacks taskCallbacks;
    RefreshDataInAdapter callBackRefresh;

    public TaskListViewModel() {

    }

    public TaskListViewModel(TaskListScreen taskListScreen,
                             TaskListUiCallBacks taskListUiCallBacks) {
        this.taskListScreenContext = taskListScreen;
        this.taskListUiCallBacks = taskListUiCallBacks;
        taskCallbacks = this;
        realm = RealmController.with((Activity) taskListScreenContext).getRealm();
        realmManager = RealmManager.getInstance();
        getAllTasks();
    }

    public void getAllTasks() {
        RealmManager.getAllTasksFromDb(realm, taskCallbacks);
    }

    public void getSearchedPlaces(){
        RealmManager.getAllTasksFromDb(realm, taskCallbacks);
    }

    public void markTaskAsDone(String taskId, RefreshDataInAdapter callBack) {
        callBackRefresh = callBack;
        if (realm == null)
            realm = RealmController.with((Activity) taskListScreenContext).getRealm();
        RealmManager.markTaskAsDone(realm, taskId, this);
    }

    public void onTextChanged(CharSequence phrase, int start, int before, int count) {
        String getValue = taskListUiCallBacks.getSearchedToken();
        RealmManager.getAllSearchedTasksFromDb(taskListUiCallBacks.getSearchedToken(), realm, taskCallbacks);
    }

    @Override
    public void onSuccessfulDataFetchedFromRealm(boolean status, RealmResults<TaskModel> TaskModels) {
        taskListUiCallBacks.populateRecyclerView(status, TaskModels);
    }

    @Override
    public void onSuccessfullyTaskStatusChanged(boolean status) {
        if (status) {
            callBackRefresh.refreshData(true);
          //  Toast.makeText(taskListScreenContext, "Task Successfully Marked as Done", Toast.LENGTH_LONG).show();
        } /*else {
            Toast.makeText(taskListScreenContext, "There was an exception.", Toast.LENGTH_LONG).show();
        }*/

    }
}
