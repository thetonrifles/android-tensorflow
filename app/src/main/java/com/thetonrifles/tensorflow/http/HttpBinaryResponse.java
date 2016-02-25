package com.thetonrifles.tensorflow.http;

public class HttpBinaryResponse {

    private boolean mSuccess;

    private byte[] mResponse;

    private HttpResponseException mException;

    public static HttpBinaryResponse success(byte[] response) {
        return new HttpBinaryResponse(response);
    }

    public static HttpBinaryResponse failure(HttpResponseException ex) {
        return new HttpBinaryResponse(ex);
    }

    private HttpBinaryResponse(byte[] response) {
        mSuccess = true;
        mResponse = response;
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

    public byte[] getResponse() {
        return mResponse;
    }
}