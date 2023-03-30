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
        Activity currentActivity = getCurrentActivity();
        EPassport.Companion.initialize(currentActivity);
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
    public void scanPassport(String dob, String expiryDate, String passportNumber) {
//       Log.d("TruthNFCModule",   passportNumber
//               + " and dates: " + dob + expiryDate + "Data received on android native side");
       AsyncTaskRunner runner = new AsyncTaskRunner();
       runner.execute(passportNumber, dob, expiryDate);
    }


   private class AsyncTaskRunner extends AsyncTask<String, Void, Map<String, String>> {

       @Override
       protected Map<String, String> doInBackground(String... params) {
           String passportNumber, dob, doe;
           passportNumber=params[0];
           dob=params[1];
           doe=params[2];
           CompletableFuture<Map<String, String>> result =  EPassport.Companion.readFromJava(passportNumber, dob, doe);
           try {
               return result.get();
           } catch (ExecutionException e) {
               throw new RuntimeException(e);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }


       @Override
       protected void onPostExecute(Map<String, String> passportData) {
           if(passportData == null) {
//               Log.d("TruthNFCModuleResult", "Data not found.");
           }
           else if (passportData.get("error") != null) {
//               Log.d("TruthNFCModuleResult", passportData.get("error").toString());
           }
           else{
//               Log.d("TruthNFCModuleResult", String.valueOf(passportData));
           }
       }


       @Override
       protected void onPreExecute() {
       }
   }
}


