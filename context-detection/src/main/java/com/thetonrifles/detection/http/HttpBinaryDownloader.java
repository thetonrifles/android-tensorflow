package com.thetonrifles.detection.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class HttpBinaryDownloader {

    private static final String LOG_TAG = "HttpClient";

    private OkHttpClient mHttp;

    public HttpBinaryDownloader() {
        mHttp = new OkHttpClient();
    }

    public void getBinary(
            final String url, final String localPath,
            final HttpResponseListener listener, final HttpHeader... headers) {
        Log.i(LOG_TAG, "REQ GET URL - " + url);
        // building request
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.get();
        // adding headers
        for (HttpHeader header : headers) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        // building request and execute
        Request request = builder.build();
        mHttp.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i(LOG_TAG, "RES GET URL - " + url);
                if (response.isSuccessful()) {
                    try {
                        // writing file on storage
                        File file = new File(localPath);
                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                        sink.writeAll(response.body().source());
                        sink.close();
                        handleSuccess();
                    } catch (Exception ex) {
                        Log.e(LOG_TAG, ex.getMessage(), ex);
                        handleFailure(ex);
                    }
                } else {
                    Log.e(LOG_TAG, response.message());
                    handleFailure(new HttpResponseException(response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                handleFailure(new HttpResponseException(500, e.getMessage()));
            }

            private void handleSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess();
                    }
                });
            }

            private void handleFailure(final Exception ex) {
                if (listener != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(ex);
                        }
                    });
                }
            }

        });
    }

}