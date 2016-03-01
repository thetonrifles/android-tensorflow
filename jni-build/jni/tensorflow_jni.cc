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

static const int INPUT_SIZE = 10;
static const int OUTPUT_SIZE = 5;
static std::unique_ptr<tensorflow::Session> session;

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

    LOG(INFO) << "loading tensorflow model from: " << filepath;

    tensorflow::SessionOptions options;
    tensorflow::ConfigProto& config = options.config;
    session.reset(tensorflow::NewSession(options));
    tensorflow::GraphDef graph_def;
    ReadBinaryProto(Env::Default(), filepath, &graph_def);
    
    LOG(INFO) << "graph created";

    tensorflow::Status status = session->Create(graph_def);

    if (!status.ok()) {
        LOG(ERROR) << "could not create tensorflow graph: " << status;
        return NULL;
    }

    //LOG(INFO) << graph_def;

    LOG(INFO) << "session created";

    //graph_def.Clear();

    LOG(INFO) << "building input tensors";

    jfloat* samples = env->GetFloatArrayElements(input, 0);
    jfloat* normalized = (jfloat*) malloc(INPUT_SIZE * sizeof(jfloat));

    LOG(INFO) << "matrix x";

    // building tensor flow input vector (1 row, INPUT_SIZE columns) 
    Tensor x(tensorflow::DT_FLOAT, tensorflow::TensorShape({1, INPUT_SIZE}));
    for(int i = 0; i<INPUT_SIZE; i++) {
        float value = samples[i];
        x.matrix<float>()(0, i) = value;
    }

    LOG(INFO) << "running session...";

    // declaring output tensor
    std::vector<tensorflow::Tensor> output_tensors;

    Status run_status = session->Run({{"in_vector", x}}, {"out_vector"}, {}, &output_tensors); 

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

JNIEXPORT jfloat JNICALL
TENSORFLOW_METHOD(process)(JNIEnv* env, jobject self, jstring path, jfloat num_a, jfloat num_b) {
    const char* filepath = env->GetStringUTFChars(path, NULL);

    LOG(INFO) << "loading tensorflow model from: " << filepath;
    
    tensorflow::SessionOptions options;
    tensorflow::ConfigProto& config = options.config;
    session.reset(tensorflow::NewSession(options));
    tensorflow::GraphDef graph_def;
    ReadBinaryProto(Env::Default(), filepath, &graph_def);
    
    LOG(INFO) << "graph created";

    tensorflow::Status status = session->Create(graph_def);

    if (!status.ok()) {
        LOG(ERROR) << "could not create tensorflow graph: " << status;
        return -1;
    }

    LOG(INFO) << "session created";

    graph_def.Clear();

    LOG(INFO) << "running session...";

    Tensor a(tensorflow::DT_FLOAT, tensorflow::TensorShape());
    a.scalar<float>()() = num_a;

    Tensor b(tensorflow::DT_FLOAT, tensorflow::TensorShape());
    b.scalar<float>()() = num_b;

    std::vector<std::pair<string, tensorflow::Tensor>> input_tensors = 
        {{ "a", a }, { "b", b }}; 
    std::vector<tensorflow::Tensor> output_tensor;

    Status run_status = session->Run(input_tensors, {"c"}, {}, &output_tensor);

    LOG(INFO) << "end computing";
    if (!run_status.ok()) {
        LOG(ERROR) << run_status.ToString() << "\n";
        return -1;
    }

    auto output = output_tensor[0].scalar<float>();
    LOG(INFO) << "output: " << output();
    
    return (jfloat) output();
}