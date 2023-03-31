package com.truthdemo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import android.app.Activity;


import id.truuth.passportreader.EPassport;

public class TruthNFCModule extends ReactContextBaseJavaModule  {
    TruthNFCModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "TruthNFCModule";
    }

    @ReactMethod
    public void initialize() {
        //make sure currentActivity is not null when you call initialize function.
        // Please check if it is the best way getting activity object.
        Activity currentActivity = getCurrentActivity();
        if(currentActivity != null) EPassport.Companion.initialize(currentActivity);
    }

    @ReactMethod
    public void checkIsSupported(final Promise result) {
        boolean isSupported = EPassport.Companion.isNFCSupported();
        result.resolve(isSupported);
    }

    @ReactMethod
    public void checkIsEnabled(final Promise result) {
        boolean isEnabled = EPassport.Companion.isNFCEnabled();
        result.resolve(isEnabled);
    }

    @ReactMethod
    public void scanPassport(final String passportNumber, final String dob, final String doe, final Promise result) {
        new AsyncTask<String, Void, Map<String, String>>() {
            @Override
            protected Map<String, String> doInBackground(String... params) {
                String passportNumber, dob, doe;
                passportNumber = params[0];
                dob = params[1];
                doe = params[2];
                CompletableFuture<Map<String, String>> result = EPassport.Companion.readFromJava(passportNumber, dob, doe);
                try {
                    return result.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Map<String, String> data) {
                result.resolve(String.valueOf(data));
            }
        }.execute(passportNumber, dob, doe);
    }
}


