package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.HistoryTaskDTO;
import com.hfad.taskmanagement.dto.ReviewDTO;
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

public class TaskReviewDetailActivity extends AppCompatActivity {

    private TextView txtStartDate, txtEndDate, txtCreatedDate, txtUserCreation, txtStatus;
    private EditText txtTaskName, txtDesctiptionTask;
    private List<HistoryTaskDTO> historyTaskDTOS;
    private String taskId;
    private TextView txtFeedback, txtRate, txtReviewDate, txtSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_review_detail);
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
                            txtTaskName = TaskReviewDetailActivity.this.findViewById(R.id.txtTaskName);
                            txtDesctiptionTask = TaskReviewDetailActivity.this.findViewById(R.id.txtDesctiptionTaskEmployee);
                            txtStartDate = TaskReviewDetailActivity.this.findViewById(R.id.txtStartDate);
                            txtEndDate = TaskReviewDetailActivity.this.findViewById(R.id.txtEndDate);
                            txtCreatedDate = TaskReviewDetailActivity.this.findViewById(R.id.txtCreatedDate);
                            txtUserCreation = TaskReviewDetailActivity.this.findViewById(R.id.txtUserCreation);
                            txtStatus = TaskReviewDetailActivity.this.findViewById(R.id.txtStatus);

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
                            RecyclerView historyTaskRecycler = (RecyclerView) TaskReviewDetailActivity.this.findViewById(R.id.history_task);
                            String taskHistoryJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<HistoryTaskDTO>>(){}.getType();
                            TaskReviewDetailActivity.this.historyTaskDTOS = gson.fromJson(taskHistoryJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TaskReviewDetailActivity.this);
                            HistoryCardViewAdapter adapter = new HistoryCardViewAdapter(TaskReviewDetailActivity.this.historyTaskDTOS);
                            historyTaskRecycler.setLayoutManager(linearLayoutManager);
                            historyTaskRecycler.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/reviewTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            txtFeedback =  TaskReviewDetailActivity.this.findViewById(R.id.txtReviewedFeedBack);
                            txtRate = TaskReviewDetailActivity.this.findViewById(R.id.txtRate);
                            txtReviewDate = TaskReviewDetailActivity.this.findViewById(R.id.txtReviewedDate);
                            txtSummary = TaskReviewDetailActivity.this.findViewById(R.id.txtSummary);

                            String reviewedTaskDTOJSON = new String(responseBody);
                            Gson gson = new Gson();
                            ReviewDTO reviewDTO = gson.fromJson(reviewedTaskDTOJSON, ReviewDTO.class);

                            String[] formatTextCreatedDate = reviewDTO.getCreatedFeedback().split("-");
                            String createdDate = formatTextCreatedDate[2];
                            for (int i = 1; i >= 0; i--) {
                                createdDate += "-" + formatTextCreatedDate[i];
                            }

                            txtFeedback.setText(reviewDTO.getFeedback());
                            txtRate.setText(reviewDTO.getRate());
                            txtReviewDate.setText(createdDate);
                            txtSummary.setText(reviewDTO.getSummary());
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
