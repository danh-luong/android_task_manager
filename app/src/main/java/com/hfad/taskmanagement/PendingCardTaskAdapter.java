package com.hfad.taskmanagement;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.taskmanagement.dto.TaskDTO;

import java.util.List;

public class PendingCardTaskAdapter extends
        RecyclerView.Adapter<PendingCardTaskAdapter.ViewHolder>{

    private List<TaskDTO> list;
    private Activity activity;

    public PendingCardTaskAdapter(List<TaskDTO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PendingCardTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout cardView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_card_task, parent, false);
        return new PendingCardTaskAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LinearLayout cardView = holder.cardView;
        TextView txtTaskName = (TextView)cardView.findViewById(R.id.txtTaskName);
        TextView txtAssignDate = (TextView) cardView.findViewById(R.id.txtAssignDate);
        TextView txtStartDate = (TextView) cardView.findViewById(R.id.txtStartDate);
        TextView txtEndDate = (TextView) cardView.findViewById(R.id.txtEndDate);
        TextView txtAssignee = (TextView) cardView.findViewById(R.id.txtAssignee);

        txtTaskName.setText(list.get(position).getTxtTaskName());
        txtAssignDate.setText(list.get(position).getTxtAssignDate());
        txtStartDate.setText(list.get(position).getTxtStartDate());
        txtEndDate.setText(list.get(position).getTxtEndDate());
        txtAssignee.setText(list.get(position).getTxtAssignee());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendingCardTaskAdapter.this.activity, TaskDetailPendingActivity.class);
                intent.putExtra("taskId", list.get(position).getTxtTaskId());
                PendingCardTaskAdapter.this.activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout cardView;

        public ViewHolder(LinearLayout cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
