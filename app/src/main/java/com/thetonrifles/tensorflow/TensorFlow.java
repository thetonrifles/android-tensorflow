package com.thetonrifles.tensorflow;

public class TensorFlow {

    static {
        System.loadLibrary( "ResonanceAI" );
    }

    public native void helloLog(String logThis);

}
