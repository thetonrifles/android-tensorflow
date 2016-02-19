package com.thetonrifles.tensorflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class FileStorage {

    private static final String LOG_TAG = "Storage";

    private static FileStorage instance;

    public static synchronized FileStorage getInstance() {
        if (instance == null) {
            instance = new FileStorage();
        }
        return instance;
    }

    public synchronized void writeFile(Context context, String url, String content) {
        File file = getTempFile(context, url);
        if (file != null) {
            FileOutputStream out = null;
            try {
                Log.d(LOG_TAG, "writing file content in cache for: " + url);
                out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
                writeLastUpdateFileName(context, file.getAbsolutePath());
                writeLastUpdateTimestamp(context, new Date());
                Log.d(LOG_TAG, "written file content in cache for: " + url);
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    private File getTempFile(Context context, String url) {
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

    private synchronized void writeLastUpdateFileName(Context context, String filename) {
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

    private synchronized void writeLastUpdateTimestamp(Context context, Date timestamp) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        prefs.edit().putLong("timestamp", timestamp.getTime()).apply();
    }

}