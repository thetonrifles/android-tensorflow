package com.thetonrifles.detection.http;

public interface HttpResponseListener {

	void onSuccess();

	void onFailure(Exception ex);

}