package com.thetonrifles.tensorflow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.thetonrifles.tensorflow.http.HttpBinaryResponseListener;
import com.thetonrifles.tensorflow.http.HttpClient;
import com.thetonrifles.tensorflow.http.HttpResponseException;

public class DownloadFragment extends Fragment implements HttpBinaryResponseListener {

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
        mUrl = url;
        mOnProgress = true;
        if (mCallback != null) {
            mCallback.onPrepare();
        }
        HttpClient http = new HttpClient();
        http.getBinary(url, this);
    }

    @Override
    public void onProgress(int progress) {
        Log.d(LOG_TAG, "downloading... " + progress + "% completed");
        if (mCallback != null) {
            mCallback.onProgress(progress);
        }
    }

    @Override
    public void onSuccess(byte[] bytes) {
    }

    @Override
    public void onSuccess(String content) {
        Log.d(LOG_TAG, "Download completed!");
        mOnProgress = false;
        if (mCallback != null) {
            FileStorage.getInstance().writeFile(getContext(), mUrl, content);
            mCallback.onDownloadCompleted();
        }
    }

    @Override
    public void onFailure(HttpResponseException ex) {
        Log.e(LOG_TAG, "Download failed!");
        mOnProgress = false;
        if (mCallback != null) {
            mCallback.onDownloadFailed(ex);
        }
    }

    public interface Callback {

        void onPrepare();

        void onProgress(int percentage);

        void onDownloadCompleted();

        void onDownloadFailed(Exception ex);

    }

}