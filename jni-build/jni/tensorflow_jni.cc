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

using namespace tensorflow;

JNIEXPORT jfloat JNICALL
TENSORFLOW_METHOD(sum)(JNIEnv* env, jobject self, jstring path, jfloat num_a, jfloat num_b) {
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