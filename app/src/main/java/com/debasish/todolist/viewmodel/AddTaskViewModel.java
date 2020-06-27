package com.debasish.todolist.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.debasish.todolist.R;
import com.debasish.todolist.data.RealmController;
import com.debasish.todolist.data.RealmManager;
import com.debasish.todolist.entity.TaskEntity;
import com.debasish.todolist.interfaces.TaskCallbacks;
import com.debasish.todolist.interfaces.TaskUiCallback;
import com.debasish.todolist.ui.AddTaskScreen;
import com.debasish.todolist.utils.ShowLogs;
import com.debasish.todolist.utils.UUIDGenerator;

import io.realm.Realm;

public class AddTaskViewModel extends ViewModel implements TaskCallbacks {

    Context addTaskScreen;
    Realm realm;
    RealmManager realmManager = null;
    TaskUiCallback taskUiCallback;
    TaskCallbacks taskCallbacks;

    /**
     * initializing all the objects and varibales
     * @param addTaskScreen
     * @param taskUiCallbackRefresh
     */
    public AddTaskViewModel(AddTaskScreen addTaskScreen,
                            TaskUiCallback taskUiCallbackRefresh) {
        this.addTaskScreen = addTaskScreen;
        this.taskUiCallback = taskUiCallbackRefresh;
        taskCallbacks = this;
        realm = RealmController.with((Activity)addTaskScreen).getRealm();
        realmManager = RealmManager.getInstance();
    }


    /**
     * Function responsible for saving the task into local DB
     * @param taskEntity Entity for Login
     */
    public void addTask(TaskEntity taskEntity){

        // Validating the Task title and Desc
        if (validateTitleAndDesc(taskEntity.getTaskTitle().trim())) {
            if (validateTitleAndDesc(taskEntity.getTaskDesc().trim())) {

                // Generating the Task Id
                String taskId = UUIDGenerator.randomUUID(4);

                // Saving the task in the Realm DB
                RealmManager.saveTaskIntoDb(realm, taskEntity, taskId, 0, taskCallbacks);
            } else {

                // Handling the error situation
                showMessage(addTaskScreen.getString(R.string.err_msg_task_desc));
            }
        } else {

            // Handling the error situation
            showMessage(addTaskScreen.getString(R.string.err_msg_task_title));
        }
    }


    // Cancelling the Task screen
    public void cancelTask(){
        taskUiCallback.onCancelClicked();
    }


    /**
     * Function responsible for showing the text message
     * @param message
     */
    private void showMessage(String message) {
        Toast.makeText(addTaskScreen, message, Toast.LENGTH_SHORT).show();
    }



    /**
     * Function responsible for validating the Task title
     * @param taskTitle
     * @return
     */
    private boolean validateTitleAndDesc(String taskTitle) {
        if (TextUtils.isEmpty(taskTitle)) {
            return false;
        }
        return true;
    }



    @Override
    public void onSuccessfulTaskSavedToDb(boolean status, Exception ex) {
        if(status){
            showMessage("Task Saved Successfully");
            taskUiCallback.refreshUi(true);
        }else{
            showMessage(ex.getLocalizedMessage());
        }
    }
}
