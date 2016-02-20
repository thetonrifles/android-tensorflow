#include "tensorflow_jni.h"

#include <fstream>
#include <android/log.h>

#include "tensorflow/core/platform/logging.h"

JNIEXPORT jstring JNICALL
TENSORFLOW_METHOD(loadModel)(JNIEnv* env, jobject self, jstring path) {
    // getting file path as string
    const char* filepath = env->GetStringUTFChars(path, NULL);
    // try opening file with model
    LOG(INFO) << "loading model from " << filepath;
    std::ifstream ifs(filepath);
    std::string content((std::istreambuf_iterator<char>(ifs)),
                        (std::istreambuf_iterator<char>()));
    jstring result = env->NewStringUTF(content.c_str());
    env->ReleaseStringUTFChars(path, filepath);
    // returning read string
    return result;
}