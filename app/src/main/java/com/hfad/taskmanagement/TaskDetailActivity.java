package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.HistoryTaskDTO;
import com.hfad.taskmanagement.dto.InsertNewTaskDTO;
import com.hfad.taskmanagement.dto.TaskCreatedDTO;
import com.hfad.taskmanagement.dto.TaskDetailDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class TaskDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView txtStartDate, txtEndDate, txtCreatedDate, txtUserCreation, txtStatus;
    private EditText txtTaskName, txtDesctiptionTask;
    private Button btnEndDate, btnStartDate, btnSaveInfoTask;
    private List<HistoryTaskDTO> historyTaskDTOS;
    private String taskId;
    private DatePickerDialog datePickerDialog;
    private DatePicker picker;
    private TextView txtErrorName, txtErrorDate, txtErrorDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = this.getIntent();
        taskId = intent.getStringExtra("taskId");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
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
                            txtDesctiptionTask = TaskDetailActivity.this.findViewById(R.id.txtDesctiptionTaskEmployee);
                            txtStartDate = TaskDetailActivity.this.findViewById(R.id.txtStartDate);
                            txtEndDate = TaskDetailActivity.this.findViewById(R.id.txtEndDate);
                            txtCreatedDate = TaskDetailActivity.this.findViewById(R.id.txtCreatedDate);
                            txtUserCreation = TaskDetailActivity.this.findViewById(R.id.txtUserCreation);
                            txtStatus = TaskDetailActivity.this.findViewById(R.id.txtStatus);

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

                            if (taskDetailDTO.getStatus().equals("Pending")) {
                                txtTaskName.setFocusableInTouchMode(true);
                                txtDesctiptionTask.setFocusableInTouchMode(true);
                                txtStartDate.setVisibility(View.VISIBLE);
                                txtEndDate.setVisibility(View.VISIBLE);
                                TaskDetailActivity.this.findViewById(R.id.btnEndDate).setVisibility(View.VISIBLE);
                                TaskDetailActivity.this.findViewById(R.id.btnStartDate).setVisibility(View.VISIBLE);
                                TaskDetailActivity.this.findViewById(R.id.btnSaveInfoTask).setVisibility(View.VISIBLE);
                            }

                            if (taskDetailDTO.getStatus().equals("Doing")) {
                                TaskDetailActivity.this.findViewById(R.id.btnSubmitTask).setVisibility(View.VISIBLE);
                            }
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

    public void clickToGetStartDate(View view) {
        showDatePickerDialog("startDate");
    }

    public void clickToGetEndDate(View view) {
        showDatePickerDialog("endDate");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String result = dayOfMonth + "-" + (month + 1) + "-" + year;
        if (view.getTag() == "startDate") {
            txtStartDate.setText(result);
        } else if (view.getTag() == "endDate") {
            txtEndDate.setText(result);
        }
    }

    public void clickToSaveDetailTask(View view) {
        txtTaskName = findViewById(R.id.txtTaskName);
        txtDesctiptionTask = findViewById(R.id.txtDesctiptionTaskEmployee);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        String txtStartDateString = txtStartDate.getText().toString();
        String txtEndDateString = txtEndDate.getText().toString();
        String txtTaskNameString = txtTaskName.getText().toString().trim();
        String txtDescriptionTaskString = txtDesctiptionTask.getText().toString().trim();
        txtErrorName = findViewById(R.id.txtErrorTaskName);
        txtErrorDate = findViewById(R.id.txtErrorDate);
        txtErrorDescription = findViewById(R.id.txtErrorTaskdescription);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date startDate = simpleDateFormat.parse(txtStartDateString);
            Date endDate = simpleDateFormat.parse(txtEndDateString);
            if (startDate.getTime() > endDate.getTime() && txtTaskNameString.equals("") && txtDescriptionTaskString.equals("")) {
                txtErrorDate.setVisibility(View.VISIBLE);
                txtErrorName.setVisibility(View.VISIBLE);
                txtErrorDescription.setVisibility(View.VISIBLE);
                return;
            }
            if (startDate.getTime() > endDate.getTime()) {
                txtErrorDate.setVisibility(View.VISIBLE);
                return;
            } else {
                txtErrorDate.setVisibility(View.GONE);
            }
            if (txtTaskNameString.equals("")) {
                txtErrorName.setVisibility(View.VISIBLE);
                return;
            } else {
                txtErrorName.setVisibility(View.GONE);
            }
            if (txtDescriptionTaskString.equals("")) {
                txtErrorDescription.setVisibility(View.VISIBLE);
                return;
            } else {
                txtErrorDescription.setVisibility(View.GONE);
            }
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            TaskCreatedDTO taskCreatedDTO = null;
            taskCreatedDTO = new TaskCreatedDTO(taskId, txtTaskNameString, txtDescriptionTaskString,
                        txtStartDateString, txtEndDateString);
            Gson gson = new Gson();
            String taskDetail = gson.toJson(taskCreatedDTO);
            StringEntity stringEntity = new StringEntity(taskDetail.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/updatePendingTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(TaskDetailActivity.this, HomeEmployeeActivity.class);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
        } catch (ParseException e) {
            txtErrorDate.setVisibility(View.VISIBLE);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDatePickerDialog(String current) {
        datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        picker = datePickerDialog.getDatePicker();
        picker.setTag(current);
        datePickerDialog.show();
    }

    public void clickToSubmitTask(View view) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Gson gson = new Gson();
        try {
            InsertNewTaskDTO insertNewTaskDTO = new InsertNewTaskDTO(taskId, ServerConfig.currentAccount.getUsername());
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(gson.toJson(insertNewTaskDTO));
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/submitTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(TaskDetailActivity.this, HomeEmployeeActivity.class);
                            if (ServerConfig.currentAccount.getRoleId() == 3) {
                                intent = new Intent(TaskDetailActivity.this, HomeManagerActivity.class);
                            }
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
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
