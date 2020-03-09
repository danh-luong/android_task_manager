package com.hfad.taskmanagement.jwt;

import java.io.Serializable;
import java.util.HashMap;

public class JWT implements Serializable {

    public static HashMap<String, String> jwt = new HashMap<>();
    public static String HEADER = "Authorization";
}
