package com.debasish.todolist.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.debasish.todolist.R;

import java.util.Objects;

public class TaskListScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.to_do_text_title));

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
}