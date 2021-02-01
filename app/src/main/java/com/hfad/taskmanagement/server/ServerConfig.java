package com.hfad.taskmanagement.server;

import com.hfad.taskmanagement.dto.AccountDTO;

import java.io.Serializable;

public class ServerConfig implements Serializable {

    public static final String BASE_URL = "http://192.168.100.7:8080";
    public static AccountDTO currentAccount = null;
}
