package com.thetonrifles.detection.http;

import java.io.File;

public class HttpBinaryResponse {

    private boolean mSuccess;

    private File mFile;

    private HttpResponseException mException;

    public static HttpBinaryResponse success(File file) {
        return new HttpBinaryResponse(file);
    }

    public static HttpBinaryResponse failure(HttpResponseException ex) {
        return new HttpBinaryResponse(ex);
    }

    private HttpBinaryResponse(File file) {
        mSuccess = true;
        mFile = file;
    }

    private HttpBinaryResponse(HttpResponseException ex) {
        mSuccess = false;
        mException = ex;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public HttpResponseException getException() {
        return mException;
    }

    public File getResponse() {
        return mFile;
    }
}