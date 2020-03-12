package com.hfad.taskmanagement;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.EmployeeDTO;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageEmployeeFragment extends Fragment {

    private List<EmployeeDTO> employeeDTOList;
    private String username;

    public ManageEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_manage_employee, container, false);
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(getContext(), ServerConfig.BASE_URL + "/currentEmployeeAdmin", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) rootView.findViewById(R.id.task_recycler);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<EmployeeDTO>>(){}.getType();
                            ManageEmployeeFragment.this.employeeDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            EmployeeCardViewAdapter adapter = new EmployeeCardViewAdapter(ManageEmployeeFragment.this.employeeDTOList, ManageEmployeeFragment.this.getActivity());
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
        return rootView;
    }
}
