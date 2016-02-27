package com.thetonrifles.tensorflow;

import android.app.Application;

public class TensorFlowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // scheduling periodic model download
        //PeriodicTask task = ContextService.buildModelDownloadTask();
        //GcmNetworkManager.getInstance(this).schedule(task);
    }

}
