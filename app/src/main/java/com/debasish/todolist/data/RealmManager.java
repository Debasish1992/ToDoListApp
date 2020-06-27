package com.debasish.todolist.data;


import android.util.Log;
import android.widget.Toast;

import com.debasish.todolist.entity.TaskEntity;
import com.debasish.todolist.entity.TaskModel;
import com.debasish.todolist.interfaces.TaskCallbacks;
import com.debasish.todolist.utils.ShowLogs;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmManager {

    private static RealmManager realmManager = null;


    public static RealmManager getInstance() {
        if (realmManager == null)
            realmManager = new RealmManager();
        return realmManager;
    }


    /**
     * Function responsible for saving the task into the Local DB
     * @param taskEntity
     * @param taskId
     * @param taskCompleteStatus
     */
    public static void saveTaskIntoDb(Realm realm, TaskEntity taskEntity,
                                      String taskId, int taskCompleteStatus, TaskCallbacks taskCallbacks){
        try{
            realm.beginTransaction();
            TaskModel taskModel = realm.createObject(TaskModel.class, taskId);
            taskModel.setTaskTitle(taskEntity.getTaskTitle());
            taskModel.setTaskDesc(taskEntity.getTaskDesc());
            taskModel.setTaskCompleteStatus(taskCompleteStatus);
            realm.commitTransaction();
            getAllTasksFromDb(realm);
            taskCallbacks.onSuccessfulTaskSavedToDb(true, null);
        }catch(Exception ex){
            ex.printStackTrace();
            taskCallbacks.onSuccessfulTaskSavedToDb(false, ex);
        }
    }

    public static void getAllTasksFromDb(Realm realm){
        try{
            RealmQuery<TaskModel> taskModels = realm.where(TaskModel.class);
            RealmResults<TaskModel> getAllData = taskModels.findAll();
            int getCount = getAllData.size();
            ShowLogs.displayLog("The Task Count is"+getCount);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}


