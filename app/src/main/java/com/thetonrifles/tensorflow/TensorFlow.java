package com.thetonrifles.tensorflow;

public class TensorFlow {

    static {
        System.loadLibrary( "tensorflow_jni" );
    }

    public native float sum(String filepath, float a, float b);

}
