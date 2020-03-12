package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CreatedTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView txtStartDate, txtEndDate;
    private DatePickerDialog datePickerDialog;
    private DatePicker picker;
    private String username;
    private TextView txtErrorDate, txtErrorTaskName, txtAssignee;
    private EditText edtTaskName, edtDesctiptionTask;
    private Spinner spAssignee;
    private LinearLayout lnAssignee;
    private List<UserDTO> listAssignees;
    private String assigneeName;
    private List<String> usernameList = new ArrayList<>();
    private int choosenPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_task);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtErrorDate = findViewById(R.id.txtErrorDate);
        txtErrorTaskName = findViewById(R.id.txtErrorTaskName);
        edtTaskName = findViewById(R.id.edtTaskName);
        edtDesctiptionTask = findViewById(R.id.txtDescriptionTask);
        spAssignee = findViewById(R.id.spAssignee);
        txtAssignee = findViewById(R.id.txtAssignee);
        lnAssignee = findViewById(R.id.lnAssignee);
        if (ServerConfig.currentAccount.getRoleId() != 1) {
            lnAssignee.setVisibility(View.VISIBLE);
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                asyncHttpClient.post(CreatedTaskActivity.this, ServerConfig.BASE_URL + "/loadEmployeeByGroup", stringEntity, "application/json",
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String listUserJson = new String(responseBody);
                                Type type = new TypeToken<ArrayList<UserDTO>>(){}.getType();
                                Gson gson = new Gson();
                                spAssignee = CreatedTaskActivity.this.findViewById(R.id.spAssignee);
                                txtAssignee = CreatedTaskActivity.this.findViewById(R.id.txtAssignee);
                                if (ServerConfig.currentAccount.getRoleId() == 2) {
                                    spAssignee.setVisibility(View.GONE);
                                    txtAssignee.setVisibility(View.GONE);
                                }
                                CreatedTaskActivity.this.listAssignees = gson.fromJson(listUserJson, type);
                                List<String> listOfAssigneeName = new ArrayList<>();
                                for (int i = 0; i < CreatedTaskActivity.this.listAssignees.size(); i++) {
                                    if (ServerConfig.currentAccount.getRoleId() == 3) {
                                        if (Integer.parseInt(CreatedTaskActivity.this.listAssignees.get(i).getRoleId()) != 1) {
                                            if (ServerConfig.currentAccount.getUsername().equals(CreatedTaskActivity.this.listAssignees.get(i).getUsername())) {
                                                listOfAssigneeName.add("My Self");
                                                CreatedTaskActivity.this.usernameList.add(ServerConfig.currentAccount.getUsername());
                                            } else {
                                                listOfAssigneeName.add(CreatedTaskActivity.this.listAssignees.get(i).getName());
                                                CreatedTaskActivity.this.usernameList.add(CreatedTaskActivity.this.listAssignees.get(i).getUsername());
                                            }
                                        }
                                    } else if (ServerConfig.currentAccount.getRoleId() == 1) {
                                            listOfAssigneeName.add(CreatedTaskActivity.this.listAssignees.get(i).getName());
                                    }
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreatedTaskActivity.this,
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
            if (ServerConfig.currentAccount.getRoleId() == 2) {
                taskCreatedDTO = new TaskCreatedDTO(txtTaskName, txtDescriptionTask,
                        txtStartDateString, txtEndDateString, dateFormat.format(currentDate).toString(),
                        null, "Pending", username);
            } else {
                taskCreatedDTO = new TaskCreatedDTO(txtTaskName, txtDescriptionTask,
                        txtStartDateString, txtEndDateString, dateFormat.format(currentDate).toString(),
                        username, "Pending", usernameList.get(choosenPosition));
            }
            Gson gson = new Gson();
            String taskDetail = gson.toJson(taskCreatedDTO);
            StringEntity stringEntity = new StringEntity(taskDetail.toString());
            asyncHttpClient.post(CreatedTaskActivity.this, ServerConfig.BASE_URL + "/insertNewTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = null;
                            if (ServerConfig.currentAccount.getRoleId() == 2) {
                                intent = new Intent(CreatedTaskActivity.this, HomeEmployeeActivity.class);
                            } else {
                                intent = new Intent(CreatedTaskActivity.this, HomeManagerActivity.class);
                            }
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
}
