package com.hfad.taskmanagement;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.AcceptDeclineDTO;
import com.hfad.taskmanagement.dto.AssignEmployeeDTO;
import com.hfad.taskmanagement.dto.EmployeeDTO;
import com.hfad.taskmanagement.dto.GroupSpnDTO;
import com.hfad.taskmanagement.dto.RoleSpnDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AvailableEmployeeAdapter extends
        RecyclerView.Adapter<AvailableEmployeeAdapter.ViewHolder>{

    private List<EmployeeDTO> list;
    private Activity activity;
    private String groupId;
    private Spinner spRoleEmployee;
    private List<RoleSpnDTO> listRoleEmployee;
    private List<String> listRoleEmployeeString, listRoleEmployeeId;
    private int positionOfRoleId;

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
        final LinearLayout cardView = holder.cardView;
        TextView txtEmployeeName = (TextView)cardView.findViewById(R.id.txtEmployeeName);
        TextView txtEmail = (TextView) cardView.findViewById(R.id.txtEmail);
        TextView txtPhone = (TextView) cardView.findViewById(R.id.txtPhone);
        Button btnAssignToThisGroup = (Button) cardView.findViewById(R.id.btnAssignToThisGroup);

        txtEmployeeName.setText(list.get(position).getName());
        txtEmail.setText(list.get(position).getEmail());
        txtPhone.setText(list.get(position).getPhone());
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
        asyncHttpClient.post(activity, ServerConfig.BASE_URL + "/spnRoleDTO", null, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String spnRoleDTOJson = new String(responseBody);
                        listRoleEmployeeString = new ArrayList<>();
                        listRoleEmployeeId = new ArrayList<>();
                        listRoleEmployee = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<RoleSpnDTO>>() {
                        }.getType();
                        listRoleEmployee = gson.fromJson(spnRoleDTOJson, type);
                        spRoleEmployee = cardView.findViewById(R.id.spRoleEmployee);
                        for (int i = 0; i < listRoleEmployee.size(); i++) {
                            listRoleEmployeeString.add(listRoleEmployee.get(i).getRoleName());
                            listRoleEmployeeId.add(listRoleEmployee.get(i).getRoleId());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(activity,
                                android.R.layout.simple_spinner_item, listRoleEmployeeString);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spRoleEmployee.setAdapter(dataAdapter);
                        spRoleEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                positionOfRoleId = position;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
        btnAssignToThisGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
                    AssignEmployeeDTO acceptDeclineDTO = new AssignEmployeeDTO(groupId, list.get(position).getId(), listRoleEmployeeId.get(positionOfRoleId));
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
                                    Intent intent = new Intent(activity, CurrentEmployeeInGroupActivity.class);
                                    intent.putExtra("groupId", groupId);
                                    intent.putExtra("error", "You already assign manager for another user");
                                    activity.finish();
                                    activity.startActivity(intent);
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
