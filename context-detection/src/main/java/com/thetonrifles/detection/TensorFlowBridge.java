package com.thetonrifles.detection;

public class TensorFlowBridge {

    static {
        System.loadLibrary("tensorflow_jni");
    }

    public native float[] normalize(String filepath, float[] samples);

}
