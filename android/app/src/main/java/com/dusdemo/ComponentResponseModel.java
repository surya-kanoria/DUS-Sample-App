package com.dusdemo;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by surya.kanoria on 08/01/18.
 */

public class ComponentResponseModel {
    @SerializedName("components")
    public HashMap<String, String> components;
}
