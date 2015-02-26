package edu.washington.sraden.quizdroid;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Steven on 2/16/15.
 */
public class QuizApp extends Application {
    private final static String TAG = "QuizApp";

    //Repository Info
    private static QuizApp instance;
    private TopicsRepo repository;

    //Alarm Intent Info
    private boolean settingsChanged = false;
    private PendingIntent pendingIntent; //Background intent for the alarm
    private boolean started; //Whether the alarm has be started
    private static final int INTENT_ID = 1;
    private ArrayList<String> preferences = new ArrayList<>(); //0->Frequency, 1->URL

    //Network Call Info
    private NetworkUtil network;


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

        //Create connection
        network = new NetworkUtil(this, QuizApp.getInstance());
        network.makeRequest();

        //Sets the default values - false means only on first use.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        preferences.add(sharedPref.getString("frequency", "5")); //Initialize the arr
        preferences.add(sharedPref.getString("downloadURL", "")); //Initialize the arr

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(QuizApp.this, INTENT_ID,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        start(); //Start the alarm on create with the preferred freq

        this.repository = new TopicsRepo();
    }

    public TopicsRepo getRepository() {
        return repository;
    }

    public void setAlarmStatus(boolean setting) {
        started = setting;
    }

    public boolean getAlarmStatus() {
        return started;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(int index, String value) {
        preferences.set(index, value);
    }

    public void setChanged(boolean changed) {
        this.settingsChanged = changed;
    }

    public boolean getChanged() {
        return settingsChanged;
    }

    public void start() {
        setAlarmStatus(true);
        int interval = Integer.parseInt(preferences.get(0)) * 1000;// * 60; //Converts min to milli;

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        //Log.i("hello", "I am starting");
    }

    public void cancel() {
        setAlarmStatus(false);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

}
