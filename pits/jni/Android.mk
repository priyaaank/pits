LOCAL_PATH:= $(call my-dir)

# first lib, which will be built statically
#

include $(CLEAR_VARS)

LOCAL_MODULE    := duktape
LOCAL_SRC_FILES := duktape.c
LOCAL_CFLAGS += -std=c99
include $(BUILD_STATIC_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE    := pitslib
LOCAL_SRC_FILES := jsengine.c
LOCAL_CFLAGS += -std=c99
LOCAL_STATIC_LIBRARIES := duktape

include $(BUILD_SHARED_LIBRARY)