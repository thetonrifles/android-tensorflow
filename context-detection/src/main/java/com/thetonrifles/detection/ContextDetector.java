package com.thetonrifles.detection;

import android.content.Context;
import android.text.TextUtils;

public class ContextDetector {

    private Context mContext;

    public ContextDetector(Context context) {
        mContext = context;
    }

    public float sum(float a, float b) {
        String filename = ModelStorage.getInstance().readLastUpdateFileName(mContext);
        if (!TextUtils.isEmpty(filename)) {
            return (new TensorFlowBridge()).process(filename, a, b);
        }
        throw new IllegalStateException("model not available in storage");
    }

    public float[] normalize(float[] samples) {
        String filename = ModelStorage.getInstance().readLastUpdateFileName(mContext);
        if (!TextUtils.isEmpty(filename)) {
            return (new TensorFlowBridge()).normalize(filename, samples);
        }
        throw new IllegalStateException("model not available in storage");
    }

}
