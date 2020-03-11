package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.TaskDTO;
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

public class TaskOfEmployeeInManagerActivity extends AppCompatActivity {

    private List<TaskDTO> taskDTOList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_of_employee_in_manager);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/currentTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = TaskOfEmployeeInManagerActivity.this.findViewById(R.id.task_recycler);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<TaskDTO>>(){}.getType();
                            TaskOfEmployeeInManagerActivity.this.taskDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TaskOfEmployeeInManagerActivity.this);
                            CardViewAdapter adapter = new CardViewAdapter(TaskOfEmployeeInManagerActivity.this.taskDTOList, TaskOfEmployeeInManagerActivity.this, 2);
                            taskRecycle.setLayoutManager(linearLayoutManager);
                            taskRecycle.setAdapter(adapter);
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
