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

import com.hfad.taskmanagement.dto.EmployeeDTO;

import java.util.List;

public class EmployeeCardViewAdapterAdmin extends
        RecyclerView.Adapter<EmployeeCardViewAdapterAdmin.ViewHolder>{
    private List<EmployeeDTO> list;
    private Activity activity;

    public EmployeeCardViewAdapterAdmin(List<EmployeeDTO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout cardView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_of_group, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LinearLayout cardView = holder.cardView;
        TextView txtEmployeeName = (TextView)cardView.findViewById(R.id.txtEmployeeName);
        TextView txtEmail = (TextView) cardView.findViewById(R.id.txtEmail);
        TextView txtPhone = (TextView) cardView.findViewById(R.id.txtPhone);

        txtEmployeeName.setText(list.get(position).getName());
        txtEmail.setText(list.get(position).getEmail());
        txtPhone.setText(list.get(position).getPhone());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeCardViewAdapterAdmin.this.activity, InfoOfUserInAdminActivity.class);
                intent.putExtra("username", list.get(position).getUsername());
                EmployeeCardViewAdapterAdmin.this.activity.startActivity(intent);
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
