package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.GroupDTO;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CreateNewGroupActivity extends AppCompatActivity {

    private EditText edtDescriptionGroup, edtGroupName;
    private TextView txttErrorGroupName, txtErrorDescriptionGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
        edtDescriptionGroup = findViewById(R.id.edtDescriptionGroup);
        edtGroupName = findViewById(R.id.edtGroupName);
        txttErrorGroupName = findViewById(R.id.txttErrorGroupName);
        txtErrorDescriptionGroup = findViewById(R.id.txtErrorDescriptionGroup);
    }

    public void clickToCreateNewGroup(View view) {
        String txtGroupName = edtGroupName.getText().toString().trim();
        String txtDescriptionGroup = edtDescriptionGroup.getText().toString().trim();
        if (txtGroupName.equals("") && txtDescriptionGroup.equals("")) {
            txtErrorDescriptionGroup.setVisibility(View.VISIBLE);
            txttErrorGroupName.setVisibility(View.VISIBLE);
            return;
        }
        if (txtGroupName.equals("")) {
            txttErrorGroupName.setVisibility(View.VISIBLE);
            return;
        }
        if (txtDescriptionGroup.equals("")) {
            txtErrorDescriptionGroup.setVisibility(View.VISIBLE);
            return;
        }
        txttErrorGroupName.setVisibility(View.GONE);
        txtErrorDescriptionGroup.setVisibility(View.GONE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Gson gson = new Gson();
        try {
            GroupDTO groupDTO = new GroupDTO(txtGroupName, txtDescriptionGroup);
            String groupDTOJSON = gson.toJson(groupDTO);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(groupDTOJSON);
            asyncHttpClient.post(this, ServerConfig.BASE_URL + "/createNewGroup", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Intent intent = new Intent(CreateNewGroupActivity.this, HomeAdminActivity.class);
                            intent.putExtra("username", ServerConfig.currentAccount.getUsername());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
