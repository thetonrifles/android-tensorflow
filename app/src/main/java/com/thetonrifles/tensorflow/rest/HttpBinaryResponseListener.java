package com.thetonrifles.tensorflow.rest;

public interface HttpBinaryResponseListener {

	void onProgress(int progress);

	void onSuccess(byte[] bytes);

	void onSuccess(String content);

	void onFailure(HttpResponseException ex);

}