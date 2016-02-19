LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS := -llog

LOCAL_MODULE    := tensorflow_jni
LOCAL_SRC_FILES := tensorflow_jni.cc

include $(BUILD_SHARED_LIBRARY)
