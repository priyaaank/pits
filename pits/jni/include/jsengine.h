#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "duktape.h"

JNIEXPORT void JNICALL Java_com_pits_bridge_EngineBridgeInterface_initializeEngineWith
  (JNIEnv *, jobject, jstring);

JNIEXPORT void JNICALL Java_com_pits_bridge_EngineBridgeInterface_callMethod
  (JNIEnv *, jobject, jstring, jstring, jstring, jstring);

#ifdef __cplusplus
}
#endif