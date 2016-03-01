package com.thetonrifles.tensorflow;

import android.app.Application;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.thetonrifles.detection.ContextService;

public class TensorFlowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // scheduling periodic model download
        PeriodicTask task = ContextService.buildModelDownloadTask();
        GcmNetworkManager.getInstance(this).schedule(task);
    }

}
