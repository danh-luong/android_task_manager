package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.UpdatedUserInfoDTO;
import com.hfad.taskmanagement.dto.UserInfoAdminDTO;
import com.hfad.taskmanagement.dto.UserProfile;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class InfoOfUserInAdminActivity extends AppCompatActivity {

    private String imgUrl;
    private EditText edtName, edtPhone, edtEmail, edtAddress, edtRole, edtGroup, edtStatus;
    private Button btnActive, btnDeactive;
    private UserInfoAdminDTO userInfoAdminDTO;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_of_user_in_admin);
        edtName = InfoOfUserInAdminActivity.this.findViewById(R.id.edtName);
        edtPhone = InfoOfUserInAdminActivity.this.findViewById(R.id.edtPhone);
        edtEmail = InfoOfUserInAdminActivity.this.findViewById(R.id.edtEmail);
        edtAddress = InfoOfUserInAdminActivity.this.findViewById(R.id.edtAddress);
        edtName.setFocusableInTouchMode(true);
        edtPhone.setFocusableInTouchMode(true);
        edtEmail.setFocusableInTouchMode(true);
        edtAddress.setFocusableInTouchMode(true);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/loadUserDetailFromAdmin", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Gson gson = new Gson();
                            String userProfileJson = new String(responseBody);
                            userInfoAdminDTO = gson.fromJson(userProfileJson, UserInfoAdminDTO.class);
                            edtName = InfoOfUserInAdminActivity.this.findViewById(R.id.edtName);
                            edtPhone = InfoOfUserInAdminActivity.this.findViewById(R.id.edtPhone);
                            edtEmail = InfoOfUserInAdminActivity.this.findViewById(R.id.edtEmail);
                            edtAddress = InfoOfUserInAdminActivity.this.findViewById(R.id.edtAddress);
                            edtRole = InfoOfUserInAdminActivity.this.findViewById(R.id.edtRole);
                            edtGroup = InfoOfUserInAdminActivity.this.findViewById(R.id.edtGroup);
                            edtStatus = InfoOfUserInAdminActivity.this.findViewById(R.id.edtStatus);
                            btnActive = InfoOfUserInAdminActivity.this.findViewById(R.id.btnActive);
                            btnDeactive = InfoOfUserInAdminActivity.this.findViewById(R.id.btnDeactive);

                            if (userInfoAdminDTO.getStatus().equalsIgnoreCase("active")) {
                                btnDeactive.setVisibility(View.VISIBLE);
                            } else {
                                btnActive.setVisibility(View.VISIBLE);
                            }

                            edtName.setText(userInfoAdminDTO.getName());
                            edtAddress.setText(userInfoAdminDTO.getAddress());
                            edtEmail.setText(userInfoAdminDTO.getEmail());
                            edtPhone.setText(userInfoAdminDTO.getPhone());
                            edtRole.setText(userInfoAdminDTO.getRoleName());
                            edtStatus.setText(userInfoAdminDTO.getStatus());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToDeactiveAccount(View view) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/deactiveUser", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(InfoOfUserInAdminActivity.this, HomeAdminActivity.class);
                            finish();
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
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

    public void clickToActiveAccount(View view) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/activeUser", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(InfoOfUserInAdminActivity.this, HomeAdminActivity.class);
                            finish();
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
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

    public void clickToUpdateUserInfo(View view) {
        String txtUserName = edtName.getText().toString();
        String txtPhone = edtPhone.getText().toString();
        String txtEmail = edtEmail.getText().toString();
        String address = edtAddress.getText().toString();

        UpdatedUserInfoDTO updatedUserInfoAdminDTO =
                new UpdatedUserInfoDTO(userInfoAdminDTO.getId(), txtUserName, txtEmail, txtPhone, address, ServerConfig.currentAccount.getUsername());
        try {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            Gson gson = new Gson();
            String updatedUserInfoAdminDTOJson = gson.toJson(updatedUserInfoAdminDTO);
            StringEntity stringEntity = new StringEntity(updatedUserInfoAdminDTOJson);
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/updateUserInfo", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(InfoOfUserInAdminActivity.this, HomeAdminActivity.class);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
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
