package com.dusdemo.dusdependencies;

import com.dusdemo.ComponentResponseModel;
import com.flipkart.dus.dependencies.ComponentRequestInterface;
import com.flipkart.dus.dependencies.ResponseInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by surya.kanoria on 07/01/18.
 */


public class ComponentDownloader implements ComponentRequestInterface {
    static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void getResponseString(List<String> componentList, ResponseInterface<HashMap<String, String>> responseCallback) {
        OkHttpClient client = new OkHttpClient();
        HashMap<String, List> componentsToFetch = new HashMap<>();
        componentsToFetch.put("components", componentList);
        Gson gson = new Gson();
        String serializedBody = gson.toJson(componentsToFetch);

        RequestBody body = RequestBody.create(JSON, serializedBody);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/getComponents")
                .post(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String responseString = response.body().string();
            ComponentResponseModel componentsFetched = gson.fromJson(responseString, ComponentResponseModel.class);
            responseCallback.OnSuccess(componentsFetched.components);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
