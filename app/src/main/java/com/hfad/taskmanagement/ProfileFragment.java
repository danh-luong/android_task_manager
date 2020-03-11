package com.hfad.taskmanagement;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hfad.taskmanagement.dto.UserProfile;
import com.hfad.taskmanagement.jwt.JWT;
import com.hfad.taskmanagement.server.ServerConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private String imgUrl;
    private EditText edtName, edtPhone, edtEmail, edtAddress;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("username");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(getContext(), ServerConfig.BASE_URL + "/profile", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Gson gson = new Gson();
                            String userProfileJson = new String(responseBody);
                            UserProfile userProfile = gson.fromJson(userProfileJson, UserProfile.class);
                            edtName = ProfileFragment.this.getActivity().findViewById(R.id.edtName);
                            edtPhone = ProfileFragment.this.getActivity().findViewById(R.id.edtPhone);
                            edtEmail = ProfileFragment.this.getActivity().findViewById(R.id.edtEmail);
                            edtAddress = ProfileFragment.this.getActivity().findViewById(R.id.edtAddress);
                            edtName.setText(userProfile.getName());
                            edtAddress.setText(userProfile.getAddress());
                            edtEmail.setText(userProfile.getEmail());
                            edtPhone.setText(userProfile.getPhone());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
            Button logOutBtn = (Button) rootView.findViewById(R.id.btnLogOut);
            logOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileFragment.this.getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    ProfileFragment.this.getActivity().finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

}
