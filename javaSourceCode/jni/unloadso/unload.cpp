//
// Created by kl on 2019/2/18.
//

#include "unload.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <dlfcn.h>
#include <unistd.h>
#include <sys/mman.h>
#include <android/log.h>
#include <jni.h>

#define tag   "unload"

#define LOGI( ... ) ((void)__android_log_print(ANDROID_LOG_INFO,tag,__VA_ARGS__))
using namespace std;

JNIEXPORT void JNICALL Java_kl_tools_load_SoLoader_dlclose
        (JNIEnv *env , jobject thiz,jstring jpath)
{
    const char* path= env->GetStringUTFChars (jpath,NULL);
    void *handle =dlopen (path,RTLD_LAZY);
    dlclose (handle);
    dlclose (handle);
}





