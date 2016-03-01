package com.thetonrifles.detection.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClient {

    private static final String LOG_TAG = "HttpClient";

    private OkHttpClient mHttp;

    public HttpClient() {
        mHttp = new OkHttpClient();
    }

    public void getBinary(
            final String url, final BinaryResponseListener listener, final HttpHeader... headers) {
        Log.i(LOG_TAG, "GET URL " + url);
        Call call = buildGetCall(url, headers);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (listener != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(response.body());
                            }
                        });
                    }
                } else {
                    Log.e(LOG_TAG, response.message());
                    if (listener != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                int code = response.code();
                                listener.onFailure(new HttpResponseException(code, response.message()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                if (listener != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(new HttpResponseException(500, e.getMessage()));
                        }
                    });
                }
            }

        });
    }

    private Call buildGetCall(String url, HttpHeader... reqHeaders) {
        Log.i(LOG_TAG, "GET URL " + url);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        return executeCall(builder, reqHeaders);
    }

    protected Call executeCall(Request.Builder builder, HttpHeader... reqHeaders) {
        for (HttpHeader header : reqHeaders) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        final Request request = builder.build();
        return mHttp.newCall(request);
    }

    public interface BinaryResponseListener {

        void onSuccess(ResponseBody body);

        void onFailure(HttpResponseException ex);

    }

}