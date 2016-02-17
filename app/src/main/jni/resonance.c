#include <jni.h>
#include <string.h>
#include <android/log.h>

#define LOG_TAG "NDK_MainActivity"
#define DO_LOG(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

void Java_com_thetonrifles_tensorflow_TensorFlow_helloLog(JNIEnv * env, jobject this, jstring logThis) {
    jboolean isCopy;
    const char * szLogThis = (*env)->GetStringUTFChars(env, logThis, &isCopy);
    DO_LOG(szLogThis);
    (*env)->ReleaseStringUTFChars(env, logThis, szLogThis);
}
