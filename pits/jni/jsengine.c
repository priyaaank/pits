#include <jni.h>
#include "include/jsengine.h"

duk_context *ctx = NULL;
jclass bridgeInterfaceClazz;
jmethodID logErrorMethod;
jmethodID sendResultMethod;

JNIEXPORT void JNICALL Java_com_pits_bridge_EngineBridgeInterface_initializeEngineWith(JNIEnv *env, jobject thisobject, jstring javascriptAsString)
{
    bridgeInterfaceClazz = (*env)->FindClass(env, "com/pits/bridge/EngineBridgeInterface");
    logErrorMethod = (*env)->GetMethodID(env, bridgeInterfaceClazz, "logError", "(Ljava/lang/String;)V");
    sendResultMethod = (*env)->GetMethodID(env, bridgeInterfaceClazz, "resultForMethodCall", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    ctx = duk_create_heap_default();
    if(!ctx) {
        (*env)->CallObjectMethod(env, thisobject, logErrorMethod, (*env)->NewStringUTF(env, "Failed to create a Duktape heap.\n"));
        exit(1);
    }

    const char *javascriptObjectsAsString = (*env)->GetStringUTFChars(env, javascriptAsString, 0);
    duk_push_string(ctx, javascriptObjectsAsString);

    if(duk_peval(ctx) != 0) {
        const char* errorMessage;
        errorMessage = "Error: %s\n", duk_safe_to_string(ctx, -1);
        (*env)->CallObjectMethod(env, thisobject, logErrorMethod, (*env)->NewStringUTF(env, errorMessage));
        duk_destroy_heap(ctx);
        exit(0);
    }

    duk_pop(ctx);
    duk_push_global_object(ctx);
}


JNIEXPORT void JNICALL Java_com_pits_bridge_EngineBridgeInterface_callMethod(JNIEnv *env, jobject thisobject, jstring namespace, jstring methodName, jstring paramsAsJson, jstring callbackMessageName)
{
    const char *methodToCall = (*env)->GetStringUTFChars(env, methodName, 0);
    duk_get_prop_string(ctx, -1, methodToCall);

    if(duk_pcall(ctx, 0) != 0) {
        const char* errorMessage;
        errorMessage = "Error: %s\n", duk_safe_to_string(ctx, -1);
        (*env)->CallObjectMethod(env, thisobject, logErrorMethod, (*env)->NewStringUTF(env, errorMessage));
    } else {
        const char* result;
        result = "Result obtained from the engine is ------> %s\n", duk_safe_to_string(ctx, -1);
        (*env)->CallObjectMethod(env, thisobject, logErrorMethod, (*env)->NewStringUTF(env, result));
    }

    duk_pop(ctx);
}