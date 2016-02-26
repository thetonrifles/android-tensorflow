package com.thetonrifles.detection;

public class TensorFlowBridge {

    static {
        System.loadLibrary( "tensorflow_jni" );
    }

    public native float process(String filepath, float a, float b);

    public native float[] normalize(String filepath, float[] samples);

}
