package com.thetonrifles.detection;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.thetonrifles.detection.http.HttpClient;
import com.thetonrifles.detection.http.HttpResponseException;
import com.thetonrifles.detection.http.HttpResponseListener;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

public class ContextDetector {

    private static final String LOG_TAG = ContextDetector.class.getSimpleName();

    private Context mContext;
    private HttpClient mHttp;

    public ContextDetector(Context context) {
        mContext = context;
        mHttp = new HttpClient();
    }

    public float[] normalize(float[] samples) throws UnavailableModelException {
        ModelInfo info = readModelInfo();
        if (info != null && !TextUtils.isEmpty(info.getLocalFile())) {
            return (new TensorFlowBridge()).normalize(info.getLocalFile(), samples);
        }
        throw new UnavailableModelException();
    }

    public void getModel(String modelUrl, HttpResponseListener listener) {
        ModelInfo info = readModelInfo();
        // model url to be updated before downloading?
        if (!TextUtils.isEmpty(modelUrl)) {
            info.setRemoteUrl(modelUrl);
            info.setLastUpdate(new Date().getTime());
            writeModelInfo(info);
        }
        getModel(listener);
    }

    public void getModel(final HttpResponseListener listener) {
        final ModelInfo info = readModelInfo();
        mHttp.getBinary(info.getRemoteUrl(), new HttpClient.BinaryResponseListener() {

            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    // writing file
                    File file = new File(info.getLocalFile());
                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    sink.writeAll(body.source());
                    sink.close();
                    // updating model
                    info.setLastUpdate(new Date().getTime());
                    writeModelInfo(info);
                    if (listener != null) {
                        listener.onSuccess();
                    }
                } catch (IOException ex) {
                    Log.e(LOG_TAG, ex.getMessage(), ex);
                    onFailure(new HttpResponseException(500, ex.getMessage()));
                }
            }

            @Override
            public void onFailure(HttpResponseException ex) {
                if (listener != null) {
                    listener.onFailure(ex);
                }
            }

        });
    }

    public synchronized void writeModelInfo(ModelInfo info) {
        String json = getGson().toJson(info);
        getPrefs().edit().putString("model_info", json).apply();
    }

    public synchronized ModelInfo readModelInfo() {
        String json = getPrefs().getString("model_info", null);
        if (!TextUtils.isEmpty(json)) {
            return getGson().fromJson(json, ModelInfo.class);
        }
        return new ModelInfo(BuildConfig.MODEL_URL, 0l);
    }

    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        return builder.create();
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences("storage", Context.MODE_PRIVATE);
    }

    private String md5(String s) {
        try {
            // create md5 hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // create hex string
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        }
        return "";
    }

    public class ModelInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        private String mRemoteUrl;
        private String mLocalFile;
        private long mLastUpdate;

        public ModelInfo(String remoteUrl, long lastUpdate) {
            mRemoteUrl = remoteUrl;
            mLocalFile = (new File(mContext.getFilesDir(), md5(remoteUrl))).getAbsolutePath();
            mLastUpdate = lastUpdate;
        }

        public String getRemoteUrl() {
            return mRemoteUrl;
        }

        public void setRemoteUrl(String remoteUrl) {
            mRemoteUrl = remoteUrl;
        }

        public String getLocalFile() {
            return mLocalFile;
        }

        public void setLocalFile(String localFile) {
            mLocalFile = localFile;
        }

        public long getLastUpdate() {
            return mLastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            mLastUpdate = lastUpdate;
        }

    }

}
