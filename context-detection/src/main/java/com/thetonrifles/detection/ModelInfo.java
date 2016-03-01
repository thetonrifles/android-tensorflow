package com.thetonrifles.detection;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ModelInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mRemoteUrl;
    private String mLocalFile;
    private long mLastUpdate;

    public ModelInfo(String remoteUrl, String localFile, long lastUpdate) {
        mRemoteUrl = remoteUrl;
        mLocalFile = localFile;
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

    public static synchronized void put(Context context, ModelInfo info) {
        String json = getGson().toJson(info);
        getPrefs(context).edit().putString("model_info", json).apply();
    }

    public static synchronized ModelInfo get(Context context) {
        String json = getPrefs(context).getString("model_info", null);
        if (!TextUtils.isEmpty(json)) {
            return getGson().fromJson(json, ModelInfo.class);
        }
        String localPath = buildLocalPath(context, BuildConfig.MODEL_URL);
        return new ModelInfo(BuildConfig.MODEL_URL, localPath, 0l);
    }

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        return builder.create();
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("storage", Context.MODE_PRIVATE);
    }

    public static String buildLocalPath(Context context, String remoteUrl) {
        return context.getFilesDir() + System.getProperty("file.separator") + md5(remoteUrl);
    }

    private static String md5(String s) {
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
            Log.e("MD5", ex.getMessage(), ex);
        }
        return "";
    }

}