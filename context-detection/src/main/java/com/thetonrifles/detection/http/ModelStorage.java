package com.thetonrifles.detection.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class ModelStorage {

    private static final String LOG_TAG = "Storage";

    private static ModelStorage instance;

    public static synchronized ModelStorage getInstance() {
        if (instance == null) {
            instance = new ModelStorage();
        }
        return instance;
    }

    public File getModelFile(Context context, String url) {
        File file = null;
        String filename = md5(url);
        if (!TextUtils.isEmpty(filename)) {
            file = new File(context.getFilesDir(), filename);
        }
        return file;
    }

    public String md5(String s) {
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

    public synchronized String readLastUpdateFileName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        return prefs.getString("filename", null);
    }

    protected synchronized void writeLastUpdateFileName(Context context, String filename) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        prefs.edit().putString("filename", filename).apply();
    }

    public synchronized Date readLastUpdateTimestamp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        long timestamp = prefs.getLong("timestamp", 0l);
        if (timestamp != 0) {
            return new Date(timestamp);
        }
        // no registered updates yet
        return null;
    }

    protected synchronized void writeLastUpdateTimestamp(Context context, Date timestamp) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        prefs.edit().putLong("timestamp", timestamp.getTime()).apply();
    }

}