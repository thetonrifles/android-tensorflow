package com.thetonrifles.detection.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.thetonrifles.detection.Params;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {

    private static final String LOG_TAG = "HttpClient";

    private OkHttpClient mHttp;

    public HttpClient() {
        mHttp = new OkHttpClient();
    }

    public void getModel(HttpBinaryResponseListener listener, HttpHeader... headers) {
        getBinary(Params.MODEL_URL, listener, headers);
    }

    public HttpBinaryResponse getModel(HttpHeader... headers) {
        return getBinary(Params.MODEL_URL, headers);
    }

    private void getBinary(
            final String url, final HttpBinaryResponseListener listener,
            final HttpHeader... headers) {
        Log.i(LOG_TAG, "GET URL " + url);
        Call call = buildGetCall(url, headers);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final long fileLength = response.body().contentLength();
                    BufferedInputStream input = new BufferedInputStream(response.body().byteStream());
                    StringBuilder sb = new StringBuilder();
                    long total = 0;
                    int b;
                    while ((b = input.read()) != -1) {
                        total += 1;
                        sb.append((char) b);
                        // publishing progress if
                        // file length is known
                        if (fileLength > 0) {
                            final int progress = (int) (total * 100 / fileLength);
                            Log.d(LOG_TAG, "downloading... " + progress + "% completed");
                            if (listener != null) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onProgress(progress);
                                    }
                                });
                            }
                        }
                    }
                    String result = sb.toString();
                    if (listener != null) {
                        final byte[] bytes = result.getBytes();
                        final String content = result;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(content);
                                listener.onSuccess(bytes);
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

    private HttpBinaryResponse getBinary(String url, HttpHeader... headers) {
        Call call = buildGetCall(url, headers);
        return buildBinaryResponse(call);
    }

    private Call buildGetCall(String url, HttpHeader... reqHeaders) {
        Log.i(LOG_TAG, "GET URL " + url);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        return executeCall(builder, reqHeaders);
    }

    protected HttpBinaryResponse buildBinaryResponse(Call call) {
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                InputStream in = response.body().byteStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String result, line = reader.readLine();
                result = line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                return HttpBinaryResponse.success(result.getBytes());
            } else {
                return HttpBinaryResponse.failure(new HttpResponseException(
                        response.code(), response.message()));
            }
        } catch (IOException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            int code = (response != null) ? response.code() : 500;
            return HttpBinaryResponse.failure(new HttpResponseException(code, ex.getMessage()));
        }
    }

    protected Call executeCall(Request.Builder builder, HttpHeader... reqHeaders) {
        for (HttpHeader header : reqHeaders) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        final Request request = builder.build();
        return mHttp.newCall(request);
    }

}