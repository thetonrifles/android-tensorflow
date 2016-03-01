package com.thetonrifles.detection;

import android.content.Context;

import com.thetonrifles.detection.http.HttpBinaryDownloader;
import com.thetonrifles.detection.http.HttpResponseListener;

import java.io.File;
import java.util.Date;

public class ContextDetector {

    private Context mContext;
    private HttpBinaryDownloader mHttp;

    public ContextDetector(Context context) {
        mContext = context;
        mHttp = new HttpBinaryDownloader();
    }

    /**
     * Normalize input samples.
     */
    public float[] normalize(float[] samples) throws UnavailableModelException {
        ModelInfo info = ModelInfo.get(mContext);
        // model available?
        if (info != null && (new File(info.getLocalFile()).exists())) {
            // available... let's return normalized samples
            return (new TensorFlowBridge()).normalize(info.getLocalFile(), samples);
        }
        throw new UnavailableModelException();
    }

    /**
     * Retrieves updated model from known remote url.
     */
    public void getModel(final HttpResponseListener listener) {
        String remoteUrl = ModelInfo.get(mContext).getRemoteUrl();
        getModel(remoteUrl, listener);
    }

    /**
     * Retrieves updated model from provided remote url.
     */
    public void getModel(final String modelUrl, final HttpResponseListener listener) {
        final String localPath = ModelInfo.buildLocalPath(mContext, modelUrl);
        mHttp.getBinary(modelUrl, localPath, new HttpResponseListener() {

            @Override
            public void onSuccess() {
                // updating model
                ModelInfo info = ModelInfo.get(mContext);
                info.setLocalFile(localPath);
                info.setRemoteUrl(modelUrl);
                info.setLastUpdate(new Date().getTime());
                ModelInfo.put(mContext, info);
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(Exception ex) {
                if (listener != null) {
                    listener.onFailure(ex);
                }
            }

        });
    }

}
