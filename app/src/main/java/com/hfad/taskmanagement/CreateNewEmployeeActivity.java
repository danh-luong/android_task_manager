package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.GroupSpnDTO;
import com.hfad.taskmanagement.dto.NewEmployeeDTO;
import com.hfad.taskmanagement.dto.RoleSpnDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CreateNewEmployeeActivity extends AppCompatActivity {

    private TextView txtErrorInformation, txtErrorGroupIdAndRole;
    private EditText txtNameEmployee, txtEmailEmployee, txtAddressEmployee, txtPhoneEmployee, txtUserNameEmployee, txtPasswordEmployee;
    private Spinner spStatus, spRoleEmployee, spnGroupEmployee;
    private List<RoleSpnDTO> listRoleEmployee = new ArrayList<>();
    private List<String> listRoleEmployeeString = new ArrayList<>(), listRoleEmployeeId = new ArrayList<>();
    private int positionOfRoleId;
    private List<GroupSpnDTO> listGroupEmployee = new ArrayList<>();
    private List<String> listGroupEmployeeString = new ArrayList<>(), listGroupEmployeeId = new ArrayList<>();
    private int positionOfGroupId;
    private String selectedStatus;
    private TextView txtErrorUsername;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_employee);
        try {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/spnGroupDTO", null, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String spngroupDTOJson = new String(responseBody);
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<GroupSpnDTO>>() {
                            }.getType();
                            CreateNewEmployeeActivity.this.listGroupEmployee = gson.fromJson(spngroupDTOJson, type);
                            spnGroupEmployee = CreateNewEmployeeActivity.this.findViewById(R.id.spGroupEmployee);
                            CreateNewEmployeeActivity.this.listGroupEmployeeString.add("Not now");
                            CreateNewEmployeeActivity.this.listGroupEmployeeId.add("Nothing");
                            for (int i = 0; i < CreateNewEmployeeActivity.this.listGroupEmployee.size(); i++) {
                                CreateNewEmployeeActivity.this.listGroupEmployeeString.add(CreateNewEmployeeActivity.this.listGroupEmployee.get(i).getName());
                                CreateNewEmployeeActivity.this.listGroupEmployeeId.add(CreateNewEmployeeActivity.this.listGroupEmployee.get(i).getGroupId());
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreateNewEmployeeActivity.this,
                                    android.R.layout.simple_spinner_item, CreateNewEmployeeActivity.this.listGroupEmployeeString);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnGroupEmployee.setAdapter(dataAdapter);
                            spnGroupEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    positionOfGroupId = position;
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
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/spnRoleDTO", null, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String spnRoleDTOJson = new String(responseBody);
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<RoleSpnDTO>>() {
                            }.getType();
                            spRoleEmployee = CreateNewEmployeeActivity.this.findViewById(R.id.spRoleEmployee);
                            CreateNewEmployeeActivity.this.listRoleEmployee = gson.fromJson(spnRoleDTOJson, type);
                            CreateNewEmployeeActivity.this.listRoleEmployeeString.add("Not now");
                            CreateNewEmployeeActivity.this.listRoleEmployeeId.add("Nothing");
                            for (int i = 0; i < CreateNewEmployeeActivity.this.listRoleEmployee.size(); i++) {
                                CreateNewEmployeeActivity.this.listRoleEmployeeString.add(CreateNewEmployeeActivity.this.listRoleEmployee.get(i).getRoleName());
                                CreateNewEmployeeActivity.this.listRoleEmployeeId.add(CreateNewEmployeeActivity.this.listRoleEmployee.get(i).getRoleId());
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreateNewEmployeeActivity.this,
                                    android.R.layout.simple_spinner_item, CreateNewEmployeeActivity.this.listRoleEmployeeString);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spRoleEmployee.setAdapter(dataAdapter);
                            spRoleEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    positionOfRoleId = position;
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
            spStatus = findViewById(R.id.spStatus);
            List<String> status = new ArrayList<>();
            status.add("Active");
            status.add("Deactive");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, status);
            spStatus.setAdapter(dataAdapter);
            spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedStatus = spStatus.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToCreateNewEmployee(View view) {
        txtNameEmployee = findViewById(R.id.txtNameEmployee);
        txtEmailEmployee = findViewById(R.id.txtEmailEmployee);
        txtAddressEmployee = findViewById(R.id.txtAddressEmployee);
        txtPhoneEmployee = findViewById(R.id.txtPhoneEmployee);
        txtErrorInformation = findViewById(R.id.txtErrorInformation);
        txtErrorGroupIdAndRole = findViewById(R.id.txtErrorGroupIdAndRole);
        txtUserNameEmployee = findViewById(R.id.txtUserNameEmployee);
        txtPasswordEmployee = findViewById(R.id.txtPasswordEmployee);

        String txtNameEmployeeString = txtNameEmployee.getText().toString();
        String txtEmailEmployeeString = txtEmailEmployee.getText().toString();
        String txtAddressEmployeeString = txtAddressEmployee.getText().toString();
        String txtPhoneEmployeeString = txtPhoneEmployee.getText().toString();
        String userNameEmployee = txtUserNameEmployee.getText().toString();
        String passwordEmployee = txtPasswordEmployee.getText().toString();
        String roleId = null;
        String groupId = null;

        if (txtNameEmployeeString.trim().equals("") ||
                txtEmailEmployeeString.trim().equals("") ||
                txtAddressEmployeeString.trim().equals("") ||
                txtPhoneEmployeeString.trim().equals("") ||
                userNameEmployee.trim().equals("") ||
                passwordEmployee.trim().equals("")) {
            txtErrorInformation.setVisibility(View.VISIBLE);
            return;
        } else {
            txtErrorInformation.setVisibility(View.GONE);
        }

        if (listGroupEmployeeId.get(positionOfGroupId).equals("Nothing")
            && !listRoleEmployeeId.get(positionOfRoleId).equals("Nothing")) {
            txtErrorGroupIdAndRole.setVisibility(View.VISIBLE);
            return;
        } else {
            txtErrorGroupIdAndRole.setVisibility(View.GONE);
            if (listRoleEmployeeId.get(positionOfRoleId).equals("Nothing")
                    && listGroupEmployeeId.get(positionOfGroupId).equals("Nothing")) {
                roleId = null;
                groupId = null;
            } else {
                roleId = listRoleEmployeeId.get(positionOfRoleId);
                groupId = listGroupEmployeeId.get(positionOfGroupId);
            }
        }

        if (listRoleEmployeeId.get(positionOfRoleId).equals("Nothing")
            && !listGroupEmployeeId.get(positionOfGroupId).equals("Nothing")) {
            txtErrorGroupIdAndRole.setVisibility(View.VISIBLE);
            return;
        } else {
            txtErrorGroupIdAndRole.setVisibility(View.GONE);
            if (listRoleEmployeeId.get(positionOfRoleId).equals("Nothing")
                    && listGroupEmployeeId.get(positionOfGroupId).equals("Nothing")) {
                roleId = null;
                groupId = null;
            } else {
                roleId = listRoleEmployeeId.get(positionOfRoleId);
                groupId = listGroupEmployeeId.get(positionOfGroupId);
            }
        }

        NewEmployeeDTO newEmployeeDTO = new NewEmployeeDTO(txtNameEmployeeString, selectedStatus, txtEmailEmployeeString,
                txtAddressEmployeeString, txtPhoneEmployeeString, roleId,
                groupId,userNameEmployee, passwordEmployee);
        Gson gson = new Gson();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
        try {
            StringEntity stringEntity = new StringEntity(gson.toJson(newEmployeeDTO));
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/createNewEmployee", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(CreateNewEmployeeActivity.this, HomeAdminActivity.class);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            txtErrorUsername = findViewById(R.id.txtErrorUsername);
                            txtErrorUsername.setVisibility(View.VISIBLE);
                        }
                    });
            txtErrorUsername = findViewById(R.id.txtErrorUsername);
            txtErrorUsername.setVisibility(View.GONE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
