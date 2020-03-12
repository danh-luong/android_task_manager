package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.TaskCreatedDTO;
import com.hfad.taskmanagement.dto.UserDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CreatedTaskActivityAdmin extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog datePickerDialog;
    private DatePicker picker;
    private TextView txtStartDate, txtEndDate;
    private Spinner spAssignee;
    private String username;
    private String assigneeName;
    private List<UserDTO> listAssignees;
    private EditText edtTaskName, edtDesctiptionTask;
    private TextView txtErrorDate, txtErrorTaskName;
    private List<String> usernameList = new ArrayList<>();
    private int choosenPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_task_admin);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        edtTaskName = findViewById(R.id.edtTaskName);
        edtDesctiptionTask = findViewById(R.id.txtDescriptionTask);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        txtErrorDate = findViewById(R.id.txtErrorDate);
        txtErrorTaskName = findViewById(R.id.txtErrorTaskName);
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(CreatedTaskActivityAdmin.this, ServerConfig.BASE_URL + "/loadEmployeeByGroupAdmin", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String listUserJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<UserDTO>>(){}.getType();
                            Gson gson = new Gson();
                            spAssignee = CreatedTaskActivityAdmin.this.findViewById(R.id.spAssignee);
                            CreatedTaskActivityAdmin.this.listAssignees = gson.fromJson(listUserJson, type);
                            List<String> listOfAssigneeName = new ArrayList<>();
                            for (int i = 0; i < CreatedTaskActivityAdmin.this.listAssignees.size(); i++) {
                                listOfAssigneeName.add(CreatedTaskActivityAdmin.this.listAssignees.get(i).getName());
                                CreatedTaskActivityAdmin.this.usernameList.add(CreatedTaskActivityAdmin.this.listAssignees.get(i).getName());
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreatedTaskActivityAdmin.this,
                                    android.R.layout.simple_spinner_item, listOfAssigneeName);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spAssignee.setAdapter(dataAdapter);
                            spAssignee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    assigneeName = spAssignee.getSelectedItem().toString();
                                    choosenPosition = position;
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
    }

    public void clickToGetStartDate(View view) {
        showDatePickerDialog("startDate");
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
        picker = datePickerDialog.getDatePicker();
        picker.setTag(current);
        datePickerDialog.show();
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

    public void clickToAddNewTask(View view) {
        String txtStartDateString = txtStartDate.getText().toString();
        String txtEndDateString = txtEndDate.getText().toString();
        String txtTaskName = edtTaskName.getText().toString().trim();
        String txtDescriptionTask = edtDesctiptionTask.getText().toString().trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        try {
            Date startDate = simpleDateFormat.parse(txtStartDateString);
            Date endDate = simpleDateFormat.parse(txtEndDateString);
            if (startDate.getTime() > endDate.getTime() && txtTaskName.equals("")) {
                txtErrorDate.setVisibility(View.VISIBLE);
                txtErrorTaskName.setVisibility(View.VISIBLE);
                return;
            }
            if (startDate.getTime() > endDate.getTime()) {
                txtErrorDate.setVisibility(View.VISIBLE);
                return;
            } else {
                txtErrorDate.setVisibility(View.GONE);
            }
            if (txtTaskName.equals("")) {
                txtErrorTaskName.setVisibility(View.VISIBLE);
                return;
            } else {
                txtErrorTaskName.setVisibility(View.GONE);
            }
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            TaskCreatedDTO taskCreatedDTO = null;
            taskCreatedDTO = new TaskCreatedDTO(txtTaskName, txtDescriptionTask,
                    txtStartDateString, txtEndDateString, dateFormat.format(currentDate).toString(),
                    username, "Pending", usernameList.get(choosenPosition));
            Gson gson = new Gson();
            String taskDetail = gson.toJson(taskCreatedDTO);
            StringEntity stringEntity = new StringEntity(taskDetail.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/insertNewTaskAdmin", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = null;
                            intent = new Intent(CreatedTaskActivityAdmin.this, HomeAdminActivity.class);
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
            txtErrorDate.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }
}
