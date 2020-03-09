package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.HistoryTaskDTO;
import com.hfad.taskmanagement.dto.TaskDetailDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView txtTaskName, txtDesctiptionTask, txtStartDate, txtEndDate, txtCreatedDate, txtUserCreation, txtStatus;
    private List<HistoryTaskDTO> historyTaskDTOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = this.getIntent();
        String taskId = intent.getStringExtra("taskId");
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
                            txtTaskName = TaskDetailActivity.this.findViewById(R.id.txtTaskName);
                            txtDesctiptionTask = TaskDetailActivity.this.findViewById(R.id.txtDesctiptionTask);
                            txtStartDate = TaskDetailActivity.this.findViewById(R.id.txtStartDate);
                            txtEndDate = TaskDetailActivity.this.findViewById(R.id.txtEndDate);
                            txtCreatedDate = TaskDetailActivity.this.findViewById(R.id.txtCreatedDate);
                            txtUserCreation = TaskDetailActivity.this.findViewById(R.id.txtUserCreation);
                            txtStatus = TaskDetailActivity.this.findViewById(R.id.txtStatus);

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
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/historyTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Gson gson = new Gson();
                            RecyclerView historyTaskRecycler = (RecyclerView) TaskDetailActivity.this.findViewById(R.id.history_task);
                            String taskHistoryJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<HistoryTaskDTO>>(){}.getType();
                            TaskDetailActivity.this.historyTaskDTOS = gson.fromJson(taskHistoryJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TaskDetailActivity.this);
                            HistoryCardViewAdapter adapter = new HistoryCardViewAdapter(TaskDetailActivity.this.historyTaskDTOS);
                            historyTaskRecycler.setLayoutManager(linearLayoutManager);
                            historyTaskRecycler.setAdapter(adapter);
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
