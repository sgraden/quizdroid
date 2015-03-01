package edu.washington.sraden.quizdroid;

/**
 * Created by Steven on 2/19/15.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        QuizApp app = QuizApp.getInstance();
        NetworkUtil network = app.getNetwork();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<String> preferences = app.getPreferences(); //Get pref cache

        if (app.getChanged()) { //If application menu has been opened/changed update
            app.setPreferences(0, sharedPref.getString("frequency", "5")); //Update pref cache
            app.setPreferences(1, sharedPref.getString("downloadURL", "")); //Update pref Cache

            network.setUrlAddress(preferences.get(1));

            app.setChanged(false);
            app.cancelAlarm();
            app.startAlarm(); //Start the new alarm
        }

        network.makeRequest(); //Reach for the new data

        //Grab the URL and the frequency and toast it.
        //String message = preferences.get(1) + ": " + preferences.get(0) + "m";//intent.getStringExtra();
        Log.i("alarm", "Alarm Executed"); //Seems to be firing many times at start
    }

}