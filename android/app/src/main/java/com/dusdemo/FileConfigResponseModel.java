package com.dusdemo;

import android.support.annotation.Keep;
import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by surya.kanoria on 08/01/18.
 */

@Keep
public class FileConfigResponseModel {
    @SerializedName("updateGraph")
    public ArrayMap<String, ArrayList<String>> updateGraph;

    @SerializedName("currentUpdateGraphVersion")
    public String currentUpdateGraphVersion;
}
