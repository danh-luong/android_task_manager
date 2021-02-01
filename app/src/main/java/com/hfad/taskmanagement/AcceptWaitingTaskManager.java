package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.TaskAcceptDeclineDTO;
import com.hfad.taskmanagement.dto.TaskDetailDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AcceptWaitingTaskManager extends AppCompatActivity {

    private TextView txtTaskName, txtDesctiptionTask, txtStartDate, txtEndDate, txtCreatedDate, txtUserCreation, txtStatus;
    private String taskId;
    private Spinner spRate;
    private String rate;
    private EditText edtFeedBack;
    private TextView txtErrorFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_waiting_task_manager);
        edtFeedBack = findViewById(R.id.txtFeedBack);
        txtErrorFeedback = findViewById(R.id.txtErrorFeedBack);
        try {
            Intent intent = this.getIntent();
            taskId = intent.getStringExtra("taskId");
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("taskId", taskId);
                asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                asyncHttpClient.post(this, ServerConfig.BASE_URL + "/taskDetail", stringEntity, "application/json",
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Gson gson = new Gson();
                                String taskDetailJson = new String(responseBody);
                                TaskDetailDTO taskDetailDTO = gson.fromJson(taskDetailJson, TaskDetailDTO.class);
                                txtTaskName = AcceptWaitingTaskManager.this.findViewById(R.id.txtTaskName);
                                txtDesctiptionTask = AcceptWaitingTaskManager.this.findViewById(R.id.txtDesctiptionTask);
                                txtStartDate = AcceptWaitingTaskManager.this.findViewById(R.id.txtStartDate);
                                txtEndDate = AcceptWaitingTaskManager.this.findViewById(R.id.txtEndDate);
                                txtCreatedDate = AcceptWaitingTaskManager.this.findViewById(R.id.txtCreatedDate);
                                txtUserCreation = AcceptWaitingTaskManager.this.findViewById(R.id.txtUserCreation);
                                txtStatus = AcceptWaitingTaskManager.this.findViewById(R.id.txtStatus);

                                txtTaskName.setText(taskDetailDTO.getTaskName());
                                txtDesctiptionTask.setText(taskDetailDTO.getDescriptionTask());
                                txtStartDate.setText(taskDetailDTO.getStartDate());
                                txtEndDate.setText(taskDetailDTO.getEndDate());
                                txtCreatedDate.setText(taskDetailDTO.getCreatedDate());
                                txtUserCreation.setText(taskDetailDTO.getUserCreation());
                                txtStatus.setText(taskDetailDTO.getStatus());

                                spRate = AcceptWaitingTaskManager.this.findViewById(R.id.spnRate);
                                List<String> rate = new ArrayList<>();
                                for (int i = 0; i < 11; i++) {
                                    rate.add(String.valueOf(i));
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AcceptWaitingTaskManager.this,
                                        android.R.layout.simple_spinner_item, rate);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spRate.setAdapter(dataAdapter);
                                spRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        AcceptWaitingTaskManager.this.rate = spRate.getSelectedItem().toString();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToAcceptTask(View view) {
        String txtFeedBack = edtFeedBack.getText().toString();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        if (txtFeedBack.trim().equals("") || rate.trim().equals("")) {
            txtErrorFeedback.setVisibility(View.VISIBLE);
            return;
        }
        try {
            Gson gson = new Gson();
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            TaskAcceptDeclineDTO taskAcceptDeclineDTO = new TaskAcceptDeclineDTO(taskId, txtFeedBack, rate);
            StringEntity stringEntity = new StringEntity(gson.toJson(taskAcceptDeclineDTO));
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/finishTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(AcceptWaitingTaskManager.this, HomeManagerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToDeclineTask(View view) {
        String txtFeedBack = edtFeedBack.getText().toString();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        if (txtFeedBack.trim().equals("") || rate.trim().equals("")) {
            txtErrorFeedback.setVisibility(View.VISIBLE);
            return;
        }
        try {
            Gson gson = new Gson();
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            TaskAcceptDeclineDTO taskAcceptDeclineDTO = new TaskAcceptDeclineDTO(taskId, txtFeedBack, rate);
            StringEntity stringEntity = new StringEntity(gson.toJson(taskAcceptDeclineDTO));
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/declineSubmitTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(AcceptWaitingTaskManager.this, HomeManagerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
