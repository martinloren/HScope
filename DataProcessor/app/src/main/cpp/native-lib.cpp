#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_dataprocessor_MainActivity_stringFromJNI(
    JNIEnv *env,
    jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_dataprocessor_MainActivity_process(JNIEnv *env, jobject instance,
                                                    jdoubleArray input_, jdoubleArray output_,
                                                    jint len) {
    jdouble *input = env->GetDoubleArrayElements(input_, NULL);
    jdouble *output = env->GetDoubleArrayElements(output_, NULL);

    // TODO EXAMPLE
    for (int i=0; i<len;i++) {
       *(output+i)=*(input+1)+1;
    }

    env->ReleaseDoubleArrayElements(input_, input, 0);
    env->ReleaseDoubleArrayElements(output_, output, 0);

    return 0;
}