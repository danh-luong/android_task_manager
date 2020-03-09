package com.hfad.taskmanagement.server;

import com.hfad.taskmanagement.dto.AccountDTO;

import java.io.Serializable;

public class ServerConfig implements Serializable {

    public static final String BASE_URL = "http://10.0.2.2:8080";
    public static AccountDTO currentAccount = null;
}
