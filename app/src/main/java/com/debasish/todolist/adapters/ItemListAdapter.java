package com.debasish.todolist.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.debasish.todolist.R;
import com.debasish.todolist.entity.TaskModel;
import com.debasish.todolist.interfaces.RefreshDataInAdapter;
import com.debasish.todolist.interfaces.TaskListCallbacks;
import com.debasish.todolist.viewmodel.TaskListViewModel;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> implements RefreshDataInAdapter {

    Activity ctx;
    RealmResults<TaskModel> taskModelObjects;
    TaskListViewModel taskListViewModelInAdapter;
    TaskListCallbacks callbacks;
    RefreshDataInAdapter refeRefreshDataInAdapter;

    public ItemListAdapter(Activity activity, RealmResults<TaskModel> taskModels,
                           TaskListViewModel taskListViewModel,
                           TaskListCallbacks taskListCallbacks) {
        this.ctx = activity;
        this.taskModelObjects = taskModels;
        this.taskListViewModelInAdapter = taskListViewModel;
        this.callbacks = taskListCallbacks;
        refeRefreshDataInAdapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel taskModel = taskModelObjects.get(position);
        holder.tvTaskTitle.setText(taskModel.getTaskTitle());
        holder.tvTaskDesc.setText(taskModel.getTaskDesc());

        //in some cases, it will prevent unwanted situations
        holder.cbTaskCompleteStatus.setOnCheckedChangeListener(null);

        if(taskModel.getTaskCompleteStatus() == 1){
            holder.cbTaskCompleteStatus.setChecked(true);
            holder.cbTaskCompleteStatus.setEnabled(false);
            holder.view.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }

        holder.cbTaskCompleteStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
               int pos = position;
               holder.cbTaskCompleteStatus.setChecked(false);
               String getTaskId = taskModelObjects.get(pos).getTaskId();
               int getTaskStatus = taskModelObjects.get(pos).getTaskCompleteStatus();

               if(getTaskId != null && getTaskStatus == 0){
                   taskListViewModelInAdapter.markTaskAsDone(getTaskId, refeRefreshDataInAdapter);
               }
            }/*else{
                holder.cbTaskCompleteStatus.setChecked(true);
            }*/
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return taskModelObjects.size();
    }

    @Override
    public void refreshData(boolean status) {
        if(status)
            notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle, tvTaskDesc;
        CheckBox cbTaskCompleteStatus;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDesc = itemView.findViewById(R.id.tvTaskDesc);
            cbTaskCompleteStatus = itemView.findViewById(R.id.cbTaskStatus);
        }
    }
}
