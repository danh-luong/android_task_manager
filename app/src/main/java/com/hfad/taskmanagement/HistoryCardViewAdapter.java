package com.hfad.taskmanagement;

import android.view.LayoutInflater;
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

        txtTaskName.setText(list.get(position).getTxtTaskName());
        txtAssignDate.setText(list.get(position).getTxtAssignDate());
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
