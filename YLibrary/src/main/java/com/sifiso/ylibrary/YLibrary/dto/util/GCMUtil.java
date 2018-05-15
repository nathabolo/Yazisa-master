package com.sifiso.ylibrary.YLibrary.dto.util;

/**
 * Created by aubreyM on 2014/10/12.
 */

/**
 * Created by aubreyM on 2014/05/11.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sifiso.ylibrary.YLibrary.dto.RequestDTO;
import com.sifiso.ylibrary.YLibrary.dto.ResponseDTO;

import java.io.IOException;


public class GCMUtil {
    public interface GCMUtilListener {
        public void onDeviceRegistered(String id);

        public void onGCMError();
    }

    static Context ctx;
    static GCMUtilListener gcmUtilListener;
    static String registrationID, msg;
    static final String LOG = "GCMUtil";
    static GoogleCloudMessaging gcm;
   static String mGcmSenderID;

    public static void startGCMRegistration(Context context, GCMUtilListener listener) {
        ctx = context;
        gcmUtilListener = listener;
        new GCMTask().execute();

    }

    public static final String GCM_SENDER_ID = "260429534791";

    static class GCMTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            Log.e(LOG, "... startin GCM registration");
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(ctx);
                }
               // gcm.unregister();
                registrationID = gcm.register(GCM_SENDER_ID);
                msg = "Device registered, registration ID = \n" + registrationID;
                SharedUtil.storeRegistrationId(ctx, registrationID);

            } catch (IOException e) {
                Log.e(LOG,"kdfgjdkgkdf{0}",e);
                return 101;
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.i(LOG, "onPostExecute... ending GCM registration");
            if (result > 0) {
                gcmUtilListener.onGCMError();
                //ErrorUtil.handleErrors(ctx, result);
                return;
            }
            gcmUtilListener.onDeviceRegistered(registrationID);
            Log.i(LOG, "onPostExecute GCM device registered OK");
        }

    }


    public static boolean checkPlayServices(Context ctx, Activity act) {
        Log.e(LOG, "checkPlayServices .................");
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(ctx);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, act,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(LOG, "This device is not supported.");
                return false;
            }
            return false;
        }
        return true;
    }


    static final int PLAY_SERVICES_RESOLUTION_REQUEST = 11;

}
