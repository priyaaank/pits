package com.pits.bridge;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class EngineBridgeInterface {

    private BridgeExecutionThread executionThread;
    private Handler executionThreadMessageHandler;

    public EngineBridgeInterface() {
        this.executionThread = new BridgeExecutionThread();
        this.executionThreadMessageHandler = executionThread.messageHandler;
        this.executionThread.start();
    }

    public void initializeEngine(final String stringifiedJavascriptObjects) {
        executionThreadMessageHandler.post(new Runnable() {
            @Override
            public void run() {
                initializeEngineWith(stringifiedJavascriptObjects);
            }
        });
    }

    public void callJSMethod(final String namespace, final String methodName, final String argumentsAsJson, final String callbackMessageName) {
        executionThreadMessageHandler.post(new Runnable() {
            @Override
            public void run() {
                callMethod(namespace, methodName, argumentsAsJson, callbackMessageName);
            }
        });
    }

    public void resultForMethodCall(String namespace, String methodName, String resultAsJson, String callbackMessageName) {
        //TODO - Implement how to handle a message response
    }

    private void logError(String message) {
        //LOG MESSAGE
    }

    private native void initializeEngineWith(String javascriptClassesAsString);

    private native void callMethod(String namespace, String methodName, String argumentsAsJson, String callbackMessageName);

    class BridgeExecutionThread extends Thread {

        private final String TAG = BridgeExecutionThread.class.getSimpleName();
        private Handler messageHandler;

        @Override
        public void run() {
            try {
                Looper.prepare();

                messageHandler = new Handler() {

                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }

                };

                Looper.loop();
            } catch(Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        }

    }

}
