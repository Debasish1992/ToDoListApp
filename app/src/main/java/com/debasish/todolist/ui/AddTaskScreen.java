package com.debasish.todolist.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;

import com.debasish.todolist.R;
import com.debasish.todolist.data.RealmController;
import com.debasish.todolist.databinding.ActivityAddTaskScreenBinding;
import com.debasish.todolist.entity.TaskEntity;
import com.debasish.todolist.interfaces.TaskUiCallback;
import com.debasish.todolist.utils.KeyBoardUtils;
import com.debasish.todolist.viewmodel.AddTaskViewModel;

import java.util.Objects;

import io.realm.Realm;

public class AddTaskScreen extends AppCompatActivity implements TaskUiCallback {
    private ActivityAddTaskScreenBinding activityAddTaskScreenBinding;
    TaskEntity taskEntity = null;
    AddTaskViewModel loginViewModel = null;
    private Realm realm;
    TaskUiCallback taskUiCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding the VIew
        activityAddTaskScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_task_screen);
        taskUiCallback = this;
        initObject();

        // Setting up the View Model and Model
        activityAddTaskScreenBinding.setAddTaskModel(taskEntity);
        activityAddTaskScreenBinding.setAddTaskViewModel(loginViewModel);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.new_todo)); // set the top title
        actionBar.setDisplayHomeAsUpEnabled(true);// set the top title

        // Hiding the Keyboard
        KeyBoardUtils.hideKeyboard(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                AddTaskScreen.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Function responsible for initializing all the objects
    private void initObject() {
        taskEntity = new TaskEntity();
        loginViewModel = new AddTaskViewModel(AddTaskScreen.this, taskUiCallback);
        realm = RealmController.with(this).getRealm();
    }

    @Override
    public void refreshUi(boolean status) {
        if(status){
            dismissAddTaskScreen();
            KeyBoardUtils.hideKeyboard(this);
        }
    }

    @Override
    public void onCancelClicked() {
        dismissAddTaskScreen();
    }

    void dismissAddTaskScreen(){
        AddTaskScreen.this.finish();
    }
}