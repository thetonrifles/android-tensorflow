package com.thetonrifles.detection;

public class UnavailableModelException extends Exception {

    public UnavailableModelException() {
        super("model not available in storage");
    }

}
