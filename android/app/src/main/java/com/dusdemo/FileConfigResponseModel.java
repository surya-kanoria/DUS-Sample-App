package com.dusdemo;

import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

/**
 * Created by surya.kanoria on 08/01/18.
 */

@Keep
public class FileConfigResponseModel {
    @SerializedName("updateGraph")
    public UpdateGraph updateGraph;
}
