package com.hfad.taskmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.hfad.taskmanagement.dto.UserProfile;

public class EmployeeProfileActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtEmail, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        Intent intent = getIntent();
        UserProfile userProfile = (UserProfile) intent.getSerializableExtra("userProfile");
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edtName.setText(userProfile.getName());
        edtAddress.setText(userProfile.getAddress());
        edtEmail.setText(userProfile.getEmail());
        edtPhone.setText(userProfile.getPhone());
    }
}
