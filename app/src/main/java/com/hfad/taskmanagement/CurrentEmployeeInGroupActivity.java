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
import com.hfad.taskmanagement.dto.EmployeeDTO;
import com.hfad.taskmanagement.dto.GroupUserDTO;
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

public class CurrentEmployeeInGroupActivity extends AppCompatActivity {

    private List<GroupUserDTO> groupUserDTOList;
    private List<EmployeeDTO> employeeDTOSList;
    private String groupId;
    private TextView txtErrorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_employee_in_group);
        Intent intent = getIntent();
        String errorManager = intent.getStringExtra("error");
        txtErrorManager = findViewById(R.id.txtErrorManager);
        if (errorManager == null) {
            txtErrorManager.setVisibility(View.GONE);
        } else {
            txtErrorManager.setVisibility(View.VISIBLE);
        }
        groupId = intent.getStringExtra("groupId");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", groupId);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/getCurrentEmployeeInGroup", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) findViewById(R.id.employee_in_group);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<GroupUserDTO>>() {
                            }.getType();
                            CurrentEmployeeInGroupActivity.this.groupUserDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CurrentEmployeeInGroupActivity.this);
                            CurrentEmployeeInGroupAdapter adapter = new CurrentEmployeeInGroupAdapter(CurrentEmployeeInGroupActivity.this.groupUserDTOList, CurrentEmployeeInGroupActivity.this);
                            taskRecycle.setLayoutManager(linearLayoutManager);
                            taskRecycle.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/getAvailableEmployee", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) findViewById(R.id.available_employee);
                            Gson gson = new Gson();
                            String listEmployeeJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<EmployeeDTO>>() {
                            }.getType();
                            CurrentEmployeeInGroupActivity.this.employeeDTOSList = gson.fromJson(listEmployeeJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CurrentEmployeeInGroupActivity.this);
                            AvailableEmployeeAdapter adapter = new AvailableEmployeeAdapter(CurrentEmployeeInGroupActivity.this.employeeDTOSList, CurrentEmployeeInGroupActivity.this, groupId);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeAdminActivity.class);
        intent.putExtra("username", ServerConfig.currentAccount.getUsername());
        finish();
        startActivity(intent);
    }
}
