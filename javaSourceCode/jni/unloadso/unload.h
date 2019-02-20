//
// Created by kl on 2019/2/18.
//

#ifndef NDKDEMO_UNLOAD_H
#define NDKDEMO_UNLOAD_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <dlfcn.h>
#include <android/log.h>
#include <jni.h>
#include <string>
#include <unistd.h>
#include <sys/mman.h>
#include <memory>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_kl_tools_load_SoLoader_dlclose
 (JNIEnv *, jobject,jstring);

#ifdef __cplusplus
};
#endif  //end __cplusplus 

#endif //NDKDEMO_UNLOAD_H
