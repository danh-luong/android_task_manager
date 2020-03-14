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

import com.hfad.taskmanagement.dto.GroupUserDTO;
import com.hfad.taskmanagement.server.ServerConfig;

import java.util.List;

public class CurrentEmployeeInGroupAdapter extends
        RecyclerView.Adapter<CurrentEmployeeInGroupAdapter.ViewHolder>{

    private List<GroupUserDTO> list;
    private Activity activity;

    public CurrentEmployeeInGroupAdapter(List<GroupUserDTO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout cardView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_current_employee, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LinearLayout cardView = holder.cardView;
        TextView txtEmployeeName = (TextView)cardView.findViewById(R.id.txtEmployeeName);
        TextView txtGroupName = (TextView) cardView.findViewById(R.id.txtRoleName);

        txtEmployeeName.setText(list.get(position).getEmployeeName());
        txtGroupName.setText(list.get(position).getRoleName());
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
