package com.thetonrifles.tensorflow;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadFragment extends Fragment {

    private static final String LOG_TAG = "Downloader";

    private String mUrl;
    private Callback mCallback;
    private boolean mOnProgress;

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("progress", mOnProgress);
    }

    public void executeDownload(String url) {
        // download file with async task and use callback
        // for providing data to parent activity
        DownloadFileTask task = new DownloadFileTask();
        task.execute(url);
    }

    /**
     * Async task for downloading file.
     */
    private class DownloadFileTask extends AsyncTask<String,Integer,Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mOnProgress = true;
            if (mCallback != null) {
                mCallback.onPrepare();
            }
        }

        @Override
        protected Object doInBackground(String... urls) {
            mUrl = urls[0];
            Object response;
            InputStream input = null;
            try {
                URL url = new URL(mUrl);
                OkHttpClient httpClient = new OkHttpClient();
                Call call = httpClient.newCall(new Request.Builder().url(url).get().build());
                Response res = call.execute();
                // successful response?
                if (res.code() != 200) {
                    // returning negative response details
                    response = new Exception("http " + res.code() + " " + res.message());
                }
                // getting file length for publishing download progress
                long fileLength = res.body().contentLength();
                input = new BufferedInputStream(res.body().byteStream());
                // building json string from stream
                StringBuilder sb = new StringBuilder();
                long total = 0;
                int b;
                while ((b = input.read()) != -1) {
                    if (isCancelled()) {
                        input.close();
                        response = new Exception("cancelled by user");
                    }
                    total += 1;
                    sb.append((char) b);
                    // publishing progress if
                    // file length is known
                    if (fileLength > 0) {
                        int progress = (int) (total * 100 / fileLength);
                        Log.d(LOG_TAG, "downloading... " + progress + "% completed");
                        publishProgress(progress);
                    }
                }
                response = sb.toString();
            } catch (Exception ex) {
                response = ex;
            } finally {
                try {
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
            }
            return response;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            if (mCallback != null) {
                mCallback.onProgress(progress);
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mOnProgress = false;
            if (result instanceof String) {
                Log.d(LOG_TAG, "Download completed!");
                if (mCallback != null) {
                    String content = (String) result;
                    FileStorage.getInstance().writeFile(getContext(), mUrl, content);
                    mCallback.onDownloadCompleted();
                }
            } else {
                Log.d(LOG_TAG, "Download failed!");
                if (mCallback != null) {
                    mCallback.onDownloadFailed((Exception) result);
                }
            }
        }

    }

    public interface Callback {

        void onPrepare();

        void onProgress(int percentage);

        void onDownloadCompleted();

        void onDownloadFailed(Exception ex);

    }

}