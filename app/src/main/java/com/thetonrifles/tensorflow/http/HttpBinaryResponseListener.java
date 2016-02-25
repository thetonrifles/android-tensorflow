package com.thetonrifles.tensorflow.http;

public interface HttpBinaryResponseListener {

	void onProgress(int progress);

	void onSuccess(byte[] bytes);

	void onSuccess(String content);

	void onFailure(HttpResponseException ex);

}