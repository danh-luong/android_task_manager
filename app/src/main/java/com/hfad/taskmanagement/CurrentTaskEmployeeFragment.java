package com.hfad.taskmanagement;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.taskmanagement.dto.TaskDTO;
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
public class CurrentTaskEmployeeFragment extends Fragment {

    private List<TaskDTO> taskDTOList;
    private String username;

    public CurrentTaskEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_current_task_employee, container, false);
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            asyncHttpClient.addHeader(JWT.HEADER, JWT.jwt.get(JWT.HEADER));
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            asyncHttpClient.post(getContext(), ServerConfig.BASE_URL + "/currentTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) rootView.findViewById(R.id.task_recycler);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<TaskDTO>>(){}.getType();
                            CurrentTaskEmployeeFragment.this.taskDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            CardViewAdapter adapter = new CardViewAdapter(CurrentTaskEmployeeFragment.this.taskDTOList, CurrentTaskEmployeeFragment.this.getActivity(), 1);
                            taskRecycle.setLayoutManager(linearLayoutManager);
                            taskRecycle.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
            asyncHttpClient.post(getContext(), ServerConfig.BASE_URL + "/pendingEmployeeTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) rootView.findViewById(R.id.task_recycler_pending_employee);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<TaskDTO>>(){}.getType();
                            CurrentTaskEmployeeFragment.this.taskDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            CardViewAdapter adapter = new CardViewAdapter(CurrentTaskEmployeeFragment.this.taskDTOList, CurrentTaskEmployeeFragment.this.getActivity(), 1);
                            taskRecycle.setLayoutManager(linearLayoutManager);
                            taskRecycle.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
            asyncHttpClient.post(getContext(), ServerConfig.BASE_URL + "/suspendEmployeeTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) rootView.findViewById(R.id.task_recycler_suspend_employee);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<TaskDTO>>(){}.getType();
                            CurrentTaskEmployeeFragment.this.taskDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            CardViewAdapter adapter = new CardViewAdapter(CurrentTaskEmployeeFragment.this.taskDTOList, CurrentTaskEmployeeFragment.this.getActivity(), 1);
                            taskRecycle.setLayoutManager(linearLayoutManager);
                            taskRecycle.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
            asyncHttpClient.post(getContext(), ServerConfig.BASE_URL + "/declineEmployeeTask", stringEntity, "application/json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            RecyclerView taskRecycle = (RecyclerView) rootView.findViewById(R.id.task_recycler_decline_employee);
                            Gson gson = new Gson();
                            String listTaskJson = new String(responseBody);
                            Type type = new TypeToken<ArrayList<TaskDTO>>(){}.getType();
                            CurrentTaskEmployeeFragment.this.taskDTOList = gson.fromJson(listTaskJson, type);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            CardViewAdapter adapter = new CardViewAdapter(CurrentTaskEmployeeFragment.this.taskDTOList, CurrentTaskEmployeeFragment.this.getActivity(), 1);
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
        Button btnCreateTask = (Button)rootView.findViewById(R.id.btnCreateNewTask);
        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentTaskEmployeeFragment.this.getActivity(), CreatedTaskActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
