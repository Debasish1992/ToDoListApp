package com.debasish.todolist.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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


    /**
     * Initializing the View Model and all the Objects
     * @param taskListScreen Instance of the Task List Activity
     * @param taskListUiCallBacks CallBack
     */
    public TaskListViewModel(TaskListScreen taskListScreen,
                             TaskListUiCallBacks taskListUiCallBacks) {
        this.taskListScreenContext = taskListScreen;
        this.taskListUiCallBacks = taskListUiCallBacks;
        taskCallbacks = this;
        realm = RealmController.with((Activity) taskListScreenContext).getRealm();
        realmManager = RealmManager.getInstance();
        getAllTasks();
    }


    /**
     * Function responsible for getting all the Task from the local DB
     */
    public void getAllTasks() {
        RealmManager.getAllTasksFromDb(realm, taskCallbacks);
    }

    /**
     * Making the Task as Done
     * @param taskId Id Of the Task
     * @param callBack
     */
    public void markTaskAsDone(String taskId, RefreshDataInAdapter callBack) {
        callBackRefresh = callBack;
        if (realm == null)
            realm = RealmController.with((Activity) taskListScreenContext).getRealm();

        // Function responsible for marking the task as Done
        RealmManager.markTaskAsDone(realm, taskId, this);
    }


    // Getting the Callback from the Data Binding
    public void onTextChanged(CharSequence phrase, int start, int before, int count) {
        if(phrase != null){
            RealmManager.getAllSearchedTasksFromDb(phrase.toString(), realm, taskCallbacks);
        }
    }

    @Override
    public void onSuccessfulDataFetchedFromRealm(boolean status, RealmResults<TaskModel> TaskModels) {
        taskListUiCallBacks.populateRecyclerView(status, TaskModels);
    }

    @Override
    public void onSuccessfullyTaskStatusChanged(boolean status) {
        if (status) {
            callBackRefresh.refreshData(true);
        }
    }
}
