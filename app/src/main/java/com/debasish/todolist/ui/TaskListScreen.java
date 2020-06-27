package com.debasish.todolist.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.debasish.todolist.R;
import com.debasish.todolist.adapters.ItemListAdapter;
import com.debasish.todolist.data.RealmController;
import com.debasish.todolist.databinding.ActivityAddTaskScreenBinding;
import com.debasish.todolist.databinding.ActivityMainBinding;
import com.debasish.todolist.entity.TaskEntity;
import com.debasish.todolist.entity.TaskModel;
import com.debasish.todolist.interfaces.TaskListCallbacks;
import com.debasish.todolist.interfaces.TaskListUiCallBacks;
import com.debasish.todolist.viewmodel.AddTaskViewModel;
import com.debasish.todolist.viewmodel.TaskListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class TaskListScreen extends AppCompatActivity implements TaskListUiCallBacks, TaskListCallbacks {

    ActivityMainBinding activityTaskListBinding;
    TaskListViewModel taskListViewModel;
    TaskModel taskModel;
    TaskListUiCallBacks taskListUiCallBacks;
    Realm realm;
    RecyclerView taskListView;
    ItemListAdapter itemListAdapter;
    RealmResults<TaskModel> taskModelObjects;
    TaskListCallbacks taskListCallbacks;
    EditText etSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTaskListBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityTaskListBinding.setTaskListViewModel(taskListViewModel);
        taskListUiCallBacks = this;
        taskListCallbacks = this;
        initViews();
        initListViews();
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.to_do_text_title));
    }

    void initViews(){
        taskListView = findViewById(R.id.rvTaskList);
        etSearchView = findViewById(R.id.etSearchBar);
    }

    private void initListViews() {
        taskListView.setLayoutManager(new LinearLayoutManager(this));
        taskListView.setItemAnimator(new DefaultItemAnimator());
        //taskListView.setAdapter(itemListAdapter);
    }

    private void initObject() {
        taskListViewModel = new TaskListViewModel();
        taskModel = new TaskModel();
        realm = RealmController.with(this).getRealm();
        taskListViewModel = new TaskListViewModel(TaskListScreen.this, taskListUiCallBacks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_todo_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                startActivity(new Intent(TaskListScreen.this, AddTaskScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initObject();
    }

    @Override
    public void onSuccessfulDataFetchedFromRealm(boolean status, RealmResults<TaskModel> TaskModels) {

    }

    @Override
    public void onSuccessfullyTaskStatusChanged(boolean status) {

    }

    @Override
    public void populateRecyclerView(boolean status, RealmResults<TaskModel> taskModelRealmResult) {
        if(status){
            taskModelObjects = taskModelRealmResult;
            itemListAdapter = new ItemListAdapter(this, taskModelObjects, taskListViewModel, taskListCallbacks);
            taskListView.setAdapter(itemListAdapter);
            taskModelRealmResult.addChangeListener(taskModels -> itemListAdapter.notifyDataSetChanged());
        }else{
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public String getSearchedToken() {
        return etSearchView.getText().toString().trim();
    }
}