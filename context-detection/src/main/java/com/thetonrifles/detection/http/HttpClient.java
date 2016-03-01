package com.thetonrifles.detection.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class HttpClient {

    private static final String LOG_TAG = "HttpClient";

    private Context mContext;
    private OkHttpClient mHttp;

    public HttpClient(Context context) {
        mContext = context;
        mHttp = new OkHttpClient();
    }

    public void getModel(HttpBinaryResponseListener listener, HttpHeader... headers) {
        String url = ModelStorage.getInstance().getModelRemoteUrl(mContext);
        getBinary(url, listener, headers);
    }

    public HttpBinaryResponse getModel(HttpHeader... headers) {
        String url = ModelStorage.getInstance().getModelRemoteUrl(mContext);
        return getBinary(url, headers);
    }

    private HttpBinaryResponse getBinary(String url, HttpHeader... headers) {
        Log.i(LOG_TAG, "GET URL " + url);
        Call call = buildGetCall(url, headers);
        return buildBinaryResponse(call);
    }

    private void getBinary(
            final String url, final HttpBinaryResponseListener listener, final HttpHeader... headers) {
        Log.i(LOG_TAG, "GET URL " + url);
        Call call = buildGetCall(url, headers);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final File file = writeFile(response.body().source());
                    if (listener != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (file != null) {
                                    listener.onSuccess(file);
                                } else {
                                    listener.onFailure(new HttpResponseException(500, "internal error"));
                                }
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

    private HttpBinaryResponse buildBinaryResponse(Call call) {
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                File file = writeFile(response.body().source());
                return HttpBinaryResponse.success(file);
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

    private File writeFile(BufferedSource source) throws IOException {
        // building file to write and getting absolute path
        String url = ModelStorage.getInstance().getModelRemoteUrl(mContext);
        File file = ModelStorage.getInstance().getModelFile(mContext, url);
        String filename = file.getAbsolutePath();
        // writing file
        BufferedSink sink = Okio.buffer(Okio.sink(file));
        sink.writeAll(source);
        sink.close();
        // updating storage
        ModelStorage.getInstance().writeLastUpdateFileName(mContext, filename);
        ModelStorage.getInstance().writeLastUpdateTimestamp(mContext, new Date());
        return file;
    }

    protected Call executeCall(Request.Builder builder, HttpHeader... reqHeaders) {
        for (HttpHeader header : reqHeaders) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        final Request request = builder.build();
        return mHttp.newCall(request);
    }

}