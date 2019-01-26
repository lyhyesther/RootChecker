package com.hook.rooter;

public class RooterNative {
    static boolean libraryLoaded = false;

    // Used to load the 'native-lib' library on application startup.
    static {
        try {
            System.loadLibrary("native-lib");
            libraryLoaded = true;
        } catch (Exception e) {

        }
    }

    public boolean wasNativeLibraryLoaded() {
        return libraryLoaded;
    }

    public native int setLogDebugMessages(boolean logDebugMessages);
    public native int checkForRoot(Object[] pathArray);
}
