#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <android/log.h>

#define LOG_TAG "TensorFlow"
#define LOG(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_thetonrifles_tensorflow_TensorFlow_loadModel(JNIEnv * env, jobject this, jstring path) {
    // getting file path as string
    const char* filepath = (*env)->GetStringUTFChars(env, path, NULL);
    // try opening file with model
    LOG("loading model from %s", filepath);
    FILE* file = fopen(filepath,"r");
    jstring result = NULL;
    // file opened?
    if (file) {
        // let's read it
        char * buffer = 0;
        size_t length;
        fseek (file, 0, SEEK_END);
        length = (size_t) ftell(file);
        fseek (file, 0, SEEK_SET);
        buffer = malloc(length);
        if (buffer) {
            fread(buffer, 1, length, file);
        }
        fclose(file);
        // file content read
        if (buffer) {
            LOG(buffer);
            result = (*env)->NewStringUTF(env, buffer);
        }
        free(buffer);
    }
    (*env)->ReleaseStringUTFChars(env, path, filepath);
    return result;
}