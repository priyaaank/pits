package com.pits.initializer;

import android.content.Context;

import java.util.List;

public class AppBootstrapper {

    private boolean isReady = false;
    private JavascriptLoader javascriptLoader;
    private List<BootStatusListener> bootStatusListeners;

    private static final String PITS_LIB_NAME = "pitslib";

    static {
        System.load(PITS_LIB_NAME);
    }

    public AppBootstrapper(Context applicationContext) {
        javascriptLoader = new JavascriptLoader(applicationContext);
    }

    public void initiateBoot() {
        for(BootStatusListener listener : bootStatusListeners) {
            listener.bootStarted();
        }
    }

    public boolean isBootFinished() {
        return isReady;
    }

    public interface BootStatusListener {

        public void bootStarted();

        public void bootFinished();

    }

}
