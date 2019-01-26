#include <jni.h>
#include <android/log.h>


#include <string>
#include <stdio.h>

#define  LOG_TAG    "RootBeer"
#define  LOGD(...)  if (DEBUG) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__);

/* Set to 1 to enable debug log traces. */
static int DEBUG = 1;
/*****************************************************************************
 * Description: Checks if a file exists
 *
 * Parameters: fname - filename to check
 *
 * Return value: 0 - non-existant / not visible, 1 - exists
 *
 *****************************************************************************/
int exists(const char *fname)
{
    FILE *file;
    if ((file = fopen(fname, "r")))
    {
        LOGD("LOOKING FOR BINARY: %s PRESENT!!!",fname);
        fclose(file);
        return 1;
    }
    LOGD("LOOKING FOR BINARY: %s Absent :(",fname);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_hook_rooter_RooterNative_checkForRoot(JNIEnv *env, jobject instance,
                                               jobjectArray pathArray) {

    int binariesFound = 0;

    int stringCount = (env)->GetArrayLength(pathArray);

    for (int i=0; i<stringCount; i++) {
        jstring string = (jstring) (env)->GetObjectArrayElement(pathArray, i);
        const char *pathString = (env)->GetStringUTFChars(string, 0);

        binariesFound+=exists(pathString);

        (env)->ReleaseStringUTFChars(string, pathString);
    }

    return binariesFound>0;

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_hook_rooter_RooterNative_setLogDebugMessages(JNIEnv *env, jobject instance,
                                                      jboolean debug) {

    if (debug){
        DEBUG = 1;
    }
    else{
        DEBUG = 0;
    }

    return 1;
}