package com.thetonrifles.detection;

import android.content.Context;
import android.text.TextUtils;

import com.thetonrifles.detection.http.ModelStorage;

public class ContextDetector {

    private Context mContext;

    public ContextDetector(Context context) {
        mContext = context;
    }

    public float[] normalize(float[] samples) throws UnavailableModelException {
        String filename = ModelStorage.getInstance().readLastUpdateFileName(mContext);
        if (!TextUtils.isEmpty(filename)) {
            return (new TensorFlowBridge()).normalize(filename, samples);
        }
        throw new UnavailableModelException();
    }

}
