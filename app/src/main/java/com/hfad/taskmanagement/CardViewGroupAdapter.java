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

import com.hfad.taskmanagement.dto.GroupDTO;
import com.hfad.taskmanagement.server.ServerConfig;

import java.util.List;

public class CardViewGroupAdapter extends
        RecyclerView.Adapter<CardViewGroupAdapter.ViewHolder>{
    private List<GroupDTO> list;
    private Activity activity;

    public CardViewGroupAdapter(List<GroupDTO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout cardView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_group, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LinearLayout cardView = holder.cardView;
        TextView txtGroupName = (TextView)cardView.findViewById(R.id.txtGroupName);
        TextView txtDescriptionGroup = (TextView) cardView.findViewById(R.id.txtDescriptionGroup);
        TextView txtNameMager = (TextView) cardView.findViewById(R.id.txtManagerName);

        txtGroupName.setText(list.get(position).getGroupName());
        txtDescriptionGroup.setText(list.get(position).getGroupDescription());
        txtNameMager.setText(list.get(position).getManagerName());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardViewGroupAdapter.this.activity, CurrentEmployeeInGroupActivity.class);
                intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                intent.putExtra("taskId", list.get(position).getGroupId());
                CardViewGroupAdapter.this.activity.startActivity(intent);
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
