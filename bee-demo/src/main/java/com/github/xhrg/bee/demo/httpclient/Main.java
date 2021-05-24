package com.github.xhrg.bee.demo.httpclient;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Main {

    public static void main(String[] args) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://127.0.0.1:10000/ping").addHeader("Connection", "keep-alive")
                .build();
        okHttpClient.newCall(request).execute();
        while (true) {
            Thread.sleep(100);
            okHttpClient.newCall(request).execute();
        }
    }
}
