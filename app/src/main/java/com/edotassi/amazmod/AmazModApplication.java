package com.edotassi.amazmod;

import android.app.Application;
import android.content.ContextWrapper;
import android.util.Log;

import com.edotassi.amazmod.setup.Setup;
import com.edotassi.amazmod.support.Logger;
import com.edotassi.amazmod.watch.Watch;
import com.pixplicity.easyprefs.library.Prefs;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;

import amazmod.com.transport.Constants;

public class AmazModApplication extends Application {

    public static Locale defaultLocale;
    private static boolean isWatchConnected;
    private static Timestamp timeLastSeen;
    //public static int syncInterval;

    public static long timeLastSync;
    public static long timeLastWatchfaceDataSend;

    public static int currentScreenBrightness;
    public static int currentScreenBrightnessMode;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init();
        FlowManager.init(this);

        Watch.init(getApplicationContext());

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setUseDefaultSharedPreference(true)
                .build();


        setWatchConnected(true);
        setupLocale();

        Setup.run(getApplicationContext());
    }

    private void setupLocale() {
        defaultLocale = Locale.getDefault();
    }

    public static void setWatchConnected(boolean connected){
        isWatchConnected = connected;
        if (connected){
            timeLastSeen = new Timestamp(System.currentTimeMillis());
        }
        Log.d(Constants.TAG,"AmazModApplication setWatchConnected - connected: " + connected + " | last seen: " + getTimeLastSeen());
    }

    public static boolean isWatchConnected(){
        return isWatchConnected;
    }

    public static String getTimeLastSeen(){
        DateFormat localizedDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        return localizedDateFormat.format(timeLastSeen);
    }
}
