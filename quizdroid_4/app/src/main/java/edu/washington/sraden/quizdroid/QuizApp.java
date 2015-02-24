package edu.washington.sraden.quizdroid;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Steven on 2/16/15.
 */
public class QuizApp extends Application {

    private final static String TAG = "QuizApp";
    private static QuizApp instance;
    private TopicsRepo repository;

    private boolean isDownloading;
    private PendingIntent pendingIntent; //Background intent for the alarm
    private boolean started; //Whether the alarm has be started
    private static final int INTENT_ID = 1;


    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            Log.e(TAG, "Created new QuizApp");
            throw new RuntimeException("Multiple app exception");
        }
    }

    public static QuizApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Sets the default values - false means only on first use.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        this.repository = new TopicsRepo();
        Log.i(TAG, "Holy crap it works!");
    }

    public TopicsRepo getRepository() {
        return repository;
    }

    public void setDownloading(boolean setting) {
        isDownloading = setting;
    }

    public boolean getDownloading() {
        return isDownloading;
    }

    public void start(int interval) {
        started = true;
        interval = interval * 1000;// * 60; //Converts min to milli

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        started = false;
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

}
