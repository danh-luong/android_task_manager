package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.Result;
import com.hfad.taskmanagement.dto.ScanDTO;
import com.hfad.taskmanagement.dto.UserProfile;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCodeActivity extends AppCompatActivity
        implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private Button btnScanByManager, btnScanByAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        btnScanByManager = findViewById(R.id.btnScanByManager);
        btnScanByAdmin = findViewById(R.id.btnScanByAdmin);
        if (ServerConfig.currentAccount.getRoleId() == 3) {
            btnScanByManager.setVisibility(View.VISIBLE);
        } else if (ServerConfig.currentAccount.getRoleId() == 1) {
            btnScanByAdmin.setVisibility(View.VISIBLE);
        }
    }

    public void clickToScan(View view) {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        try {
            int userId = Integer.parseInt(result.getText());
            if (ServerConfig.currentAccount.getRoleId() == 3) {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                Gson gson = new Gson();
                ScanDTO scanDTO = new ScanDTO(String.valueOf(userId), ServerConfig.currentAccount.getUsername());
                asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
                StringEntity stringEntity = new StringEntity(gson.toJson(scanDTO));
                asyncHttpClient.post(this, ServerConfig.BASE_URL + "/profileWithIdManager", stringEntity, "application/json",
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Gson gson = new Gson();
                                String userProfileJson = new String(responseBody);
                                UserProfile userProfile = gson.fromJson(userProfileJson, UserProfile.class);
                                Intent intent = new Intent(ScanQRCodeActivity.this, EmployeeProfileActivity.class);
                                intent.putExtra("userProfile", userProfile);
                                finish();
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(getApplicationContext(), "Invalid Id or this employee doesn\'t belong to your group", Toast.LENGTH_LONG).show();
                            }
                        });
            } else if (ServerConfig.currentAccount.getRoleId() == 1) {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", userId);
                asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                asyncHttpClient.post(this, ServerConfig.BASE_URL + "/profileWithAdmin", stringEntity, "application/json",
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Gson gson = new Gson();
                                String userProfileJson = new String(responseBody);
                                UserProfile userProfile = gson.fromJson(userProfileJson, UserProfile.class);
                                Intent intent = new Intent(ScanQRCodeActivity.this, EmployeeProfileActivity.class);
                                intent.putExtra("userProfile", userProfile);
                                finish();
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(getApplicationContext(), "Invalid Id or this employee doesn\'t belong to your group", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_LONG).show();
        }
        zXingScannerView.resumeCameraPreview(this);
    }

    public void clickToScanByAdmin(View view) {
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
}
