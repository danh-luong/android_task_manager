package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.AcceptDeclineDTO;
import com.hfad.taskmanagement.dto.TaskDetailDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class TaskDetailPendingActivity extends AppCompatActivity {

    private TextView txtTaskName, txtDesctiptionTask, txtStartDate, txtEndDate, txtCreatedDate, txtUserCreation, txtStatus;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_pending);
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
                                txtTaskName = TaskDetailPendingActivity.this.findViewById(R.id.txtTaskName);
                                txtDesctiptionTask = TaskDetailPendingActivity.this.findViewById(R.id.txtDesctiptionTask);
                                txtStartDate = TaskDetailPendingActivity.this.findViewById(R.id.txtStartDate);
                                txtEndDate = TaskDetailPendingActivity.this.findViewById(R.id.txtEndDate);
                                txtCreatedDate = TaskDetailPendingActivity.this.findViewById(R.id.txtCreatedDate);
                                txtUserCreation = TaskDetailPendingActivity.this.findViewById(R.id.txtUserCreation);
                                txtStatus = TaskDetailPendingActivity.this.findViewById(R.id.txtStatus);

                                txtTaskName.setText(taskDetailDTO.getTaskName());
                                txtDesctiptionTask.setText(taskDetailDTO.getDescriptionTask());
                                txtStartDate.setText(taskDetailDTO.getStartDate());
                                txtEndDate.setText(taskDetailDTO.getEndDate());
                                txtCreatedDate.setText(taskDetailDTO.getCreatedDate());
                                txtUserCreation.setText(taskDetailDTO.getUserCreation());
                                txtStatus.setText(taskDetailDTO.getStatus());
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
        try {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            JSONObject jsonObject = new JSONObject();
            AcceptDeclineDTO acceptDeclineDTO = new AcceptDeclineDTO(taskId, ServerConfig.currentAccount.getUsername());
            Gson gson = new Gson();
            String acceptDeclineDTOString = gson.toJson(acceptDeclineDTO);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(acceptDeclineDTOString);
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/acceptTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(TaskDetailPendingActivity.this, HomeManagerActivity.class);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
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
        try {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("taskId", taskId);
            jsonObject.put("userId", ServerConfig.currentAccount.getUsername());
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/declineTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(TaskDetailPendingActivity.this, HomeManagerActivity.class);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
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
