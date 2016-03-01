package com.thetonrifles.detection;

import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import com.thetonrifles.detection.http.HttpBinaryResponseListener;
import com.thetonrifles.detection.http.HttpClient;
import com.thetonrifles.detection.http.HttpResponseException;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ContextService extends GcmTaskService implements HttpBinaryResponseListener {

    private static final String LOG_TAG = "Downloader";

    @Override
    public int onRunTask(TaskParams taskParams) {
        HttpClient http = new HttpClient(this);
        http.getModel(this);
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    public static PeriodicTask buildModelDownloadTask() {
        return new PeriodicTask.Builder()
                .setTag("sync.model")
                .setService(ContextService.class)
                .setPeriod(TimeUnit.MINUTES.toSeconds(15))
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .build();
    }

    @Override
    public void onSuccess(File file) {
        Log.d(LOG_TAG, "Download completed!");
    }

    @Override
    public void onFailure(HttpResponseException ex) {
        Log.e(LOG_TAG, "Download failed!");
    }

}
