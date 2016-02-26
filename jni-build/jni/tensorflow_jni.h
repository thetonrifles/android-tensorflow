#ifndef ANDROID_TENSORFLOW_TENSORFLOW_JNI_H // NOLINT
#define ANDROID_TENSORFLOW_TENSORFLOW_JNI_H // NOLINT

#ifdef __cplusplus
extern "C" {
#endif  // __cplusplus

#include <jni.h>

//#define LOG_TAG "TensorFlow"
//#define LOG(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define TENSORFLOW_METHOD(METHOD_NAME) \
  Java_com_thetonrifles_tensorflow_TensorFlow_##METHOD_NAME  // NOLINT

JNIEXPORT jfloat JNICALL TENSORFLOW_METHOD(process)(
        JNIEnv* env, jobject self, jstring path, jfloat num_a, jfloat num_b);

JNIEXPORT jfloatArray JNICALL TENSORFLOW_METHOD(normalize)(
        JNIEnv* env, jobject self, jstring path, jfloatArray samples);

#ifdef __cplusplus
}  // extern "C"
#endif  // __cplusplus

#endif //ANDROID_TENSORFLOW_TENSORFLOW_JNI_H // NOLINT
