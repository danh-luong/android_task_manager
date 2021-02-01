package com.hfad.taskmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.taskmanagement.dto.HistoryTaskDTO;

import java.util.List;

public class HistoryCardViewAdapter extends
        RecyclerView.Adapter<HistoryCardViewAdapter.ViewHolder>{

    private List<HistoryTaskDTO> list;

    public HistoryCardViewAdapter(List<HistoryTaskDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout cardView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card_task, parent, false);
        return new HistoryCardViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout cardView = holder.cardView;
        TextView txtTaskName = (TextView) cardView.findViewById(R.id.txtTaskName);
        TextView txtAssignDate = (TextView) cardView.findViewById(R.id.txtAssignDate);
        TextView txtAssignee = (TextView) cardView.findViewById(R.id.txtAssignee);
        TextView txtStatus = (TextView) cardView.findViewById(R.id.txtStatus);

        if (list.get(position).getTxtSupendDate() != null) {
            LinearLayout lnSuspendDate = (LinearLayout) cardView.findViewById(R.id.lnSuspendDate);
            LinearLayout lnSuspendBy = (LinearLayout) cardView.findViewById(R.id.lnSuspendBy);
            lnSuspendDate.setVisibility(View.VISIBLE);
            lnSuspendBy.setVisibility(View.VISIBLE);
            TextView txtSupendBy = (TextView) cardView.findViewById(R.id.txtSupendBy);
            TextView txtSupendDate = (TextView) cardView.findViewById(R.id.txtSupendDate);
            txtSupendBy.setText(list.get(position).getTxtSuspendBy());
            txtSupendDate.setText(list.get(position).getTxtSupendDate());
        }

        String[] formatTextAssignDate = list.get(position).getTxtAssignDate().split("-");
        String assignDate = formatTextAssignDate[2];
        for (int i = 1; i >= 0; i--) {
            assignDate += "-" + formatTextAssignDate[i];
        }

        txtTaskName.setText(list.get(position).getTxtTaskName());
        txtAssignDate.setText(assignDate);
        txtAssignee.setText(list.get(position).getTxtAssignee());
        txtStatus.setText(list.get(position).getTxtStatus());
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
