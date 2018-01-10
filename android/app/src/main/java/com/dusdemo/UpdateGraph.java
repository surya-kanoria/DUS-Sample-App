package com.dusdemo;

import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by surya.kanoria on 10/01/18.
 */

public class UpdateGraph {
    @SerializedName("currentUpdateGraph")
    public ArrayMap<String, ArrayList<String>> currentUpdateGraph;

    @SerializedName("currentUpdateGraphVersion")
    public String currentUpdateGraphVersion;
}
