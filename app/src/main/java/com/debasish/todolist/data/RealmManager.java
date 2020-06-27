package com.debasish.todolist.data;


import com.debasish.todolist.entity.TaskEntity;
import com.debasish.todolist.entity.TaskModel;
import com.debasish.todolist.interfaces.TaskCallbacks;
import com.debasish.todolist.interfaces.TaskListCallbacks;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmManager {

    private static RealmManager realmManager = null;
    final static int TASK_STATUS_COMPLETE = 1;


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
        try {
            realm.beginTransaction();
            TaskModel taskModel = realm.createObject(TaskModel.class, taskId);
            taskModel.setTaskTitle(taskEntity.getTaskTitle());
            taskModel.setTaskDesc(taskEntity.getTaskDesc());
            taskModel.setTaskCompleteStatus(taskCompleteStatus);
            realm.commitTransaction();
            taskCallbacks.onSuccessfulTaskSavedToDb(true, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            taskCallbacks.onSuccessfulTaskSavedToDb(false, ex);
        }
    }


    /**
     * Function responsible for getting all the tasks from local DB
     * @param realm Realm Instance
     * @param taskCallbacks Callback
     */
    public static void getAllTasksFromDb(Realm realm, TaskListCallbacks taskCallbacks) {
        RealmResults<TaskModel> getAllData = null;
        try {
            RealmQuery<TaskModel> taskModels = realm.where(TaskModel.class);
            getAllData = taskModels.findAll();
            getAllData = getAllData.sort("taskCompleteStatus", Sort.ASCENDING);
            taskCallbacks.onSuccessfulDataFetchedFromRealm(true, getAllData);
        } catch (Exception ex) {
            ex.printStackTrace();
            taskCallbacks.onSuccessfulDataFetchedFromRealm(false, getAllData);
        }
    }


    /**
     * Function responsible for fetching all the records from the Db which are searched
     * @param searchPhrase phrase to search
     * @param realm realm Instance
     * @param taskCallbacks CallBack
     */
    public static void getAllSearchedTasksFromDb(String searchPhrase, Realm realm, TaskListCallbacks taskCallbacks) {
        RealmResults<TaskModel> getAllData = null;
        try {
            RealmQuery<TaskModel> taskModels = realm.where(TaskModel.class);
            taskModels.contains("taskTitle", searchPhrase, Case.INSENSITIVE);
            getAllData = taskModels.findAll();
            getAllData = getAllData.sort("taskCompleteStatus", Sort.ASCENDING);
            taskCallbacks.onSuccessfulDataFetchedFromRealm(true, getAllData);
        } catch (Exception ex) {
            ex.printStackTrace();
            taskCallbacks.onSuccessfulDataFetchedFromRealm(false, getAllData);
        }
    }


    /**
     * Function responsible for marking the Task as Done in the Database
     * @param realm Realm Instance
     * @param taskId Id of the Task
     * @param taskCallbacks Call back
     */
    public static void markTaskAsDone(Realm realm, String taskId, TaskListCallbacks taskCallbacks) {
        TaskModel getTask = null;
        try {

            // Getting the Matching object from the DB
            RealmQuery<TaskModel> taskModels = realm.where(TaskModel.class);
            taskModels.equalTo("taskId", taskId);
            getTask = taskModels.findFirst();

            // Checking the nullity and Changing the status of the task
            if (getTask != null) {
                realm.beginTransaction();
                getTask.setTaskCompleteStatus(TASK_STATUS_COMPLETE);
                realm.commitTransaction();

                // Sending the CallBack
                taskCallbacks.onSuccessfullyTaskStatusChanged(true);
            } else {
                taskCallbacks.onSuccessfullyTaskStatusChanged(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            taskCallbacks.onSuccessfullyTaskStatusChanged(false);
        }
    }
}


