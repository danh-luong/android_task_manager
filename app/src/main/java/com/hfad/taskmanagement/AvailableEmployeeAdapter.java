package com.hfad.taskmanagement;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.AcceptDeclineDTO;
import com.hfad.taskmanagement.dto.EmployeeDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AvailableEmployeeAdapter extends
        RecyclerView.Adapter<AvailableEmployeeAdapter.ViewHolder>{

    private List<EmployeeDTO> list;
    private Activity activity;
    private String groupId;

    public AvailableEmployeeAdapter(List<EmployeeDTO> list, Activity activity, String groupId) {
        this.list = list;
        this.activity = activity;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout cardView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_available_employee, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LinearLayout cardView = holder.cardView;
        TextView txtEmployeeName = (TextView)cardView.findViewById(R.id.txtEmployeeName);
        TextView txtEmail = (TextView) cardView.findViewById(R.id.txtEmail);
        TextView txtPhone = (TextView) cardView.findViewById(R.id.txtPhone);
        Button btnAssignToThisGroup = (Button) cardView.findViewById(R.id.btnAssignToThisGroup);

        txtEmployeeName.setText(list.get(position).getName());
        txtEmail.setText(list.get(position).getEmail());
        txtPhone.setText(list.get(position).getPhone());

        btnAssignToThisGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
                    AcceptDeclineDTO acceptDeclineDTO = new AcceptDeclineDTO(groupId, list.get(position).getId());
                    Gson gson = new Gson();
                    String acceptDeclineDTOJson = gson.toJson(acceptDeclineDTO);
                    StringEntity stringEntity = new StringEntity(acceptDeclineDTOJson);
                    asyncHttpClient.post(activity, ServerConfig.BASE_URL + "/addEmployeeToSpecificGroup", stringEntity, "application/json",
                            new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Intent intent = new Intent(activity, CurrentEmployeeInGroupActivity.class);
                                    intent.putExtra("groupId", groupId);
                                    activity.finish();
                                    activity.startActivity(intent);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
