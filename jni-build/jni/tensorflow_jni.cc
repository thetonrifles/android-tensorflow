#include "tensorflow_jni.h"

#include <fstream>
#include <pthread.h>
#include <unistd.h>
#include <queue>
#include <sstream>
#include <string>
#include <android/log.h>

#include "tensorflow/core/framework/types.pb.h"
#include "tensorflow/core/platform/logging.h"
#include "tensorflow/core/public/session.h"

static std::unique_ptr<tensorflow::Session> session;

static const int INPUT_SIZE = 10;
static const int OUTPUT_SIZE = 5;

using namespace tensorflow;

JNIEXPORT jfloatArray JNICALL
TENSORFLOW_METHOD(normalize)(JNIEnv* env, jobject self, jstring path, jfloatArray input) {
    const char* filepath = env->GetStringUTFChars(path, NULL);

    jfloatArray result;
    result = env->NewFloatArray(OUTPUT_SIZE);
    if (result == NULL) {
        LOG(ERROR) << "out of memory in allocating result";
        return NULL; 
    }

    LOG(INFO) << "loading graph from binary file: " << filepath;
    tensorflow::GraphDef graph_def;
    Status load_graph_status = ReadBinaryProto(Env::Default(), filepath, &graph_def);
    if (!load_graph_status.ok()) {
        LOG(ERROR) << "could not create tensorflow graph: " << load_graph_status;
        return NULL;
    }
    LOG(INFO) << "graph created";

    LOG(INFO) << "creating session to run graph";
    session.reset(tensorflow::NewSession(tensorflow::SessionOptions()));
    tensorflow::Status session_create_status = session->Create(graph_def);
    if (!session_create_status.ok()) {
        LOG(ERROR) << "could not create session: " << session_create_status;
        return NULL;
    }
    LOG(INFO) << "session created";

    graph_def.Clear();

    LOG(INFO) << "building input tensors";
    jfloat* samples = env->GetFloatArrayElements(input, 0);
    jfloat* normalized = (jfloat*) malloc(INPUT_SIZE * sizeof(jfloat));
    // building tensor flow input vector (1 row, INPUT_SIZE columns) 
    Tensor x(tensorflow::DT_FLOAT, tensorflow::TensorShape({1, INPUT_SIZE}));
    for(int i = 0; i<INPUT_SIZE; i++) {
        float value = samples[i];
        x.matrix<float>()(0, i) = value;
    }

    LOG(INFO) << "running session...";
    std::vector<tensorflow::Tensor> output_tensors;
    Status run_status = session->Run({{"in_matrix", x}}, {"out_matrix"}, {}, &output_tensors); 
    LOG(INFO) << "end computing";
    if (!run_status.ok()) {
        LOG(ERROR) << run_status.ToString() << "\n";
        return NULL;
    }

    Tensor& output_tensor = output_tensors[0];
    tensorflow::TTypes<float>::Flat output_flat = output_tensor.flat<float>();

    for(int i=0; i<OUTPUT_SIZE; i++) {
        const float value = output_flat(i);
        normalized[i] = value;
    }

    env->ReleaseFloatArrayElements(input, samples, 0);
    env->SetFloatArrayRegion(result, 0, OUTPUT_SIZE, normalized);
    free(normalized);
    return result;
}
