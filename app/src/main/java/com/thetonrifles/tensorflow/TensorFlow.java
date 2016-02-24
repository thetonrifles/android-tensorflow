package com.thetonrifles.tensorflow;

public class TensorFlow {

    static {
        System.loadLibrary( "tensorflow_jni" );
    }

    public native float process(String filepath, float a, float b);

}
