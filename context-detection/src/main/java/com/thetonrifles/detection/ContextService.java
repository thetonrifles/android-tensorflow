package com.thetonrifles.detection;

import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import com.thetonrifles.detection.events.ModelUpdatedEvent;
import com.thetonrifles.detection.http.HttpResponseException;
import com.thetonrifles.detection.http.HttpResponseListener;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

public class ContextService extends GcmTaskService implements HttpResponseListener {

    private static final String LOG_TAG = "Downloader";

    @Override
    public int onRunTask(TaskParams taskParams) {
        ContextDetector detector = new ContextDetector(this);
        detector.getModel(this);
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    public static PeriodicTask buildModelDownloadTask() {
        return new PeriodicTask.Builder()
                .setTag("sync.model")
                .setService(ContextService.class)
                .setPeriod(TimeUnit.SECONDS.toSeconds(10))
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .build();
    }

    @Override
    public void onSuccess() {
        Log.d(LOG_TAG, "Download completed!");
        EventBus.getDefault().post(new ModelUpdatedEvent());
    }

    @Override
    public void onFailure(HttpResponseException ex) {
        Log.e(LOG_TAG, "Download failed!");
    }

}
