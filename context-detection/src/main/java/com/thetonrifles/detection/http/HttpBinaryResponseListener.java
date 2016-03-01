package com.thetonrifles.detection.http;

import java.io.File;

public interface HttpBinaryResponseListener {

	void onSuccess(File file);

	void onFailure(HttpResponseException ex);

}