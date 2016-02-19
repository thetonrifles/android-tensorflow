package com.thetonrifles.tensorflow;

public class TensorFlow {

    static {
        System.loadLibrary( "tensorflow_jni" );
    }

    public native String loadModel(String filepath);

}
