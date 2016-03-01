package com.thetonrifles.tensorflow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.thetonrifles.detection.http.HttpBinaryResponseListener;
import com.thetonrifles.detection.http.HttpClient;
import com.thetonrifles.detection.http.HttpResponseException;

import java.io.File;

public class DownloadFragment extends Fragment implements HttpBinaryResponseListener {

    private static final String LOG_TAG = "Downloader";

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

    public void downloadModel() {
        // download file with async task and use callback
        // for providing data to parent activity
        mOnProgress = true;
        if (mCallback != null) {
            mCallback.onPrepare();
        }
        HttpClient http = new HttpClient(getContext());
        http.getModel(this);
    }

    @Override
    public void onSuccess(File file) {
        Log.d(LOG_TAG, "Download completed!");
        mOnProgress = false;
        if (mCallback != null) {
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

        void onDownloadCompleted();

        void onDownloadFailed(Exception ex);

    }

}