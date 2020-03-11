package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.HistoryTaskDTO;
import com.hfad.taskmanagement.dto.TaskDetailDTO;
import com.hfad.taskmanagement.dto.UpdatedTaskDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class TaskDetailInManagerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView txtStartDate, txtEndDate, txtCreatedDate, txtUserCreation, txtStatus;
    private EditText txtTaskName, txtDesctiptionTask;
    private List<HistoryTaskDTO> historyTaskDTOS;
    private String taskId;
    private DatePickerDialog datePickerDialog;
    private DatePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_in_manager);
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
                                txtTaskName = TaskDetailInManagerActivity.this.findViewById(R.id.txtTaskName);
                                txtDesctiptionTask = TaskDetailInManagerActivity.this.findViewById(R.id.txtDesctiptionTask);
                                txtStartDate = TaskDetailInManagerActivity.this.findViewById(R.id.txtStartDate);
                                txtEndDate = TaskDetailInManagerActivity.this.findViewById(R.id.txtEndDate);
                                txtCreatedDate = TaskDetailInManagerActivity.this.findViewById(R.id.txtCreatedDate);
                                txtUserCreation = TaskDetailInManagerActivity.this.findViewById(R.id.txtUserCreation);
                                txtStatus = TaskDetailInManagerActivity.this.findViewById(R.id.txtStatus);

                                String[] formatTextStartDate = taskDetailDTO.getStartDate().split("-");
                                String[] formatTextEndDate = taskDetailDTO.getEndDate().split("-");
                                String startDate = formatTextStartDate[2];

                                for (int i = 1; i >= 0; i--) {
                                    startDate += "-" + formatTextStartDate[i];
                                }

                                String endDate = formatTextEndDate[2];

                                for (int i = 1; i >= 0; i--) {
                                    endDate += "-" + formatTextEndDate[i];
                                }

                                txtTaskName.setText(taskDetailDTO.getTaskName());
                                txtDesctiptionTask.setText(taskDetailDTO.getDescriptionTask());
                                txtStartDate.setText(startDate);
                                txtEndDate.setText(endDate);
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
                                RecyclerView historyTaskRecycler = (RecyclerView) TaskDetailInManagerActivity.this.findViewById(R.id.history_task);
                                String taskHistoryJson = new String(responseBody);
                                Type type = new TypeToken<ArrayList<HistoryTaskDTO>>() {
                                }.getType();
                                TaskDetailInManagerActivity.this.historyTaskDTOS = gson.fromJson(taskHistoryJson, type);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TaskDetailInManagerActivity.this);
                                HistoryCardViewAdapter adapter = new HistoryCardViewAdapter(TaskDetailInManagerActivity.this.historyTaskDTOS);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToUpdateTask(View view) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        try {
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            Gson gson = new Gson();
            txtTaskName = TaskDetailInManagerActivity.this.findViewById(R.id.txtTaskName);
            txtDesctiptionTask = TaskDetailInManagerActivity.this.findViewById(R.id.txtDesctiptionTask);
            txtEndDate = TaskDetailInManagerActivity.this.findViewById(R.id.txtEndDate);
            UpdatedTaskDTO updatedTaskDTO = new UpdatedTaskDTO(taskId, txtTaskName.getText().toString(),
                    txtDesctiptionTask.getText().toString(), txtEndDate.getText().toString());
            String updatedTaskDTOJson = gson.toJson(updatedTaskDTO);
            StringEntity stringEntity = new StringEntity(updatedTaskDTOJson);
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/updateTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            finish();
                            startActivity(getIntent());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToSuspendTask(View view) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskId", taskId);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/suspendTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            txtStatus = TaskDetailInManagerActivity.this.findViewById(R.id.txtStatus);
                            txtStatus.setText("Suspend");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToGetEndDate(View view) {
        showDatePickerDialog("endDate");
    }

    private void showDatePickerDialog(String current) {
        datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String result = dayOfMonth + "-" + (month + 1) + "-" + year;
        txtEndDate.setText(result);
    }
}
