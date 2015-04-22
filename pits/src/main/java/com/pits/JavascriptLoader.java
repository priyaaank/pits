package com.pits;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class JavascriptLoader {

    private static final String TAG = JavascriptLoader.class.getSimpleName();
    private static final String BLANK = "";

    private final Context applicationContext;
    private final String javascriptDirectory = "js";

    public JavascriptLoader(final Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String javascriptAsString() {
        String javascriptContent = BLANK;
        try {
            String[] javascriptFiles = applicationContext.getAssets().list(javascriptDirectory);
            if(javascriptFiles.length > 0) {
                javascriptContent = loadFilesAsString(javascriptFiles);
            }
        } catch (IOException ioException) {
            Log.e(TAG, ioException.toString());
        }

        return javascriptContent;
    }

    private String loadFilesAsString(String[] javascriptFiles) throws IOException {
        StringBuffer javascriptBuffer = new StringBuffer(BLANK);
        for(String filePath : javascriptFiles) {
            javascriptBuffer.append(readFileAsString(filePath));
        }
        return javascriptBuffer.toString();
    }

    private byte[] readFileAsString(String filePath) throws IOException {
        InputStream inputStream = applicationContext.getAssets().open(javascriptDirectory + filePath);
        int contentSize = inputStream.available();
        byte[] buffer = new byte[contentSize];
        inputStream.read(buffer);
        inputStream.close();
        return buffer;
    }

}
