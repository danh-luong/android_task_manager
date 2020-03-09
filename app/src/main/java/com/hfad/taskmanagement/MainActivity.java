package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.AccountDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsername =  findViewById(R.id.txtUsername);
        edtPassword = findViewById(R.id.txtPassword);
        errorTextView = findViewById(R.id.errorTextView);
    }

    public void loginUser(View view) {
        String txtUsername = edtUsername.getText().toString();
        String txtPassword = edtPassword.getText().toString();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", txtUsername);
            jsonObject.put("password", txtPassword);
            StringEntity entity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/login", entity, "application/json",
                    new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String[] token = headers[0].toString().split(":");
                    JWT.jwt.put(token[0], token[1]);
                    String accountJson = new String(responseBody);
                    Gson gson = new Gson();
                    AccountDTO accountDTO = gson.fromJson(accountJson, AccountDTO.class);
                    ServerConfig.currentAccount = accountDTO;
                    if (accountDTO.getRoleId() == 1) {
                        Intent adminIntent = new Intent(MainActivity.this, HomeAdminActivity.class);
                        adminIntent.putExtra("username", accountDTO.getUsername());
                        startActivity(adminIntent);
                    } else if (accountDTO.getRoleId() == 2) {
                        Intent employeeIntent = new Intent(MainActivity.this, HomeEmployeeActivity.class);
                        employeeIntent.putExtra("username", accountDTO.getUsername());
                        startActivity(employeeIntent);
                    } else if (accountDTO.getRoleId() == 3) {
                        Intent managerIntent = new Intent(MainActivity.this, HomeManagerActivity.class);
                        managerIntent.putExtra("username", accountDTO.getUsername());
                        startActivity(managerIntent);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    errorTextView.setVisibility(View.VISIBLE);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
