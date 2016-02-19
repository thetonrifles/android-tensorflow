package com.thetonrifles.tensorflow;

public class TensorFlow {

    static {
        System.loadLibrary( "tensorflow" );
    }

    public native String loadModel(String filepath);

}
