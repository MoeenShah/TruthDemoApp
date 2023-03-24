package com.truthdemo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import id.truuth.passportreader.EPassport;

public class TruthNFCModule extends ReactContextBaseJavaModule  {
    TruthNFCModule(ReactApplicationContext context) {
        super(context);
    }


    //    @Override
    protected void onResume() {
        super.onResume();
        EPassport.Companion.initialize(this);
    }


    @Override
    public String getName() {
        return "TruthNFCModule";
    }

    @ReactMethod
    public boolean checkIsSupported(String dob, String expiryDate, String passportNumber) {
        boolean isSupported = EPassport.Companion.isNFCSupported();
        return isSupported;
    }

    @ReactMethod
    public boolean checkIsEnabled(String dob, String expiryDate, String passportNumber) {
        boolean isEnabled = EPassport.Companion.isNFCEnabled();
        return isEnabled;
    }

    @ReactMethod
    public void scanPassport(String dob, String expiryDate, String passportNumber) {
        Log.d("TruthNFCModule",   passportNumber
                + " and dates: " + dob + expiryDate);
//        AsyncTaskRunner runner = new AsyncTaskRunner();
//        runner.execute(passportNumber, dob, expiryDate);
    }


//    private class AsyncTaskRunner extends AsyncTask<String, Void, Map<String, String>> {
//
//        @Override
//        protected Map<String, String> doInBackground(String... params) {
//            String passportNumber, dob, doe;
//            passportNumber=params[0];
//            dob=params[1];
//            doe=params[2];
//            CompletableFuture<Map<String, String>> result =  EPassport.Companion.readFromJava(passportNumber, dob, doe);
//            try {
//                return result.get();
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//
//        @Override
//        protected void onPostExecute(Map<String, String> passportData) {
//            if(passportData == null) {
//                Toast.makeText(getApplicationContext(), "Data not found.", Toast.LENGTH_SHORT).show();
//            }
//            else if (passportData.get("error") != null) {
//                Toast.makeText(getApplicationContext(), passportData.get("error").toString(), Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Log.d("TruthNFCModuleResult", passportData.toString());
//            }
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//        }
//    }
}
//}


