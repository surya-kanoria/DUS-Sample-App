package com.dusdemo.dusdependencies;


import com.dusdemo.FileConfigResponseModel;
import com.flipkart.dus.dependencies.FileConfigRequestInterface;
import com.flipkart.dus.dependencies.FileConfigResponseInterface;
import com.flipkart.dus.models.FileConfig;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by surya.kanoria on 08/01/18.
 */

public class FileConfigDownloader implements FileConfigRequestInterface {
    @Override
    public void getResponseString(String updateGraphName, String updateGraphVersion, FileConfigResponseInterface responseCallback) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/updateGraph?appVersion=default")
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String responseString = response.body().string();
            FileConfigResponseModel responseObject = gson.fromJson(responseString, FileConfigResponseModel.class);
            FileConfig fileConfig = new FileConfig();
            fileConfig.setCurrentUpdateGraph(responseObject.updateGraph.currentUpdateGraph);
            fileConfig.setCurrentUpdateGraphVersion(responseObject.updateGraph.currentUpdateGraphVersion);
            responseCallback.onSuccess(fileConfig, "0.0.0.0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
