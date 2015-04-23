package com.pits.initializer;

import android.content.Context;

import com.pits.bridge.EngineBridgeInterface;

import java.util.ArrayList;
import java.util.List;

public class AppBootstrapper {

    private boolean isReady = false;
    private JavascriptLoader javascriptLoader;
    private List<BootStatusListener> bootStatusListeners;
    private EngineBridgeInterface bridgeInterface;

    private static final String PITS_LIB_NAME = "pitslib";

    static {
        System.loadLibrary(PITS_LIB_NAME);
    }

    public AppBootstrapper(Context applicationContext) {
        javascriptLoader = new JavascriptLoader(applicationContext);
        bridgeInterface = new EngineBridgeInterface();
        bootStatusListeners = new ArrayList<BootStatusListener>();
    }

    public void initiateBoot() {
        bridgeInterface.initializeEngine(javascriptLoader.javascriptAsString());
        for(BootStatusListener listener : bootStatusListeners) {
            listener.bootStarted();
        }
        bridgeInterface.callJSMethod("something", "processLine", "", "");
    }

    public boolean isBootFinished() {
        return isReady;
    }

    public interface BootStatusListener {

        public void bootStarted();

        public void bootFinished();

    }

}
