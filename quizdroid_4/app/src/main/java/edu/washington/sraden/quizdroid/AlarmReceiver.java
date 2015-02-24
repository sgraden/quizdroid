package edu.washington.sraden.quizdroid;

/**
 * Created by Steven on 2/19/15.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        QuizApp app = QuizApp.getInstance();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<String> preferences = app.getPreferences(); //Get pref cache

        if (app.getChanged()) { //If application menu has been opened/changed update
            app.setPreferences(0, sharedPref.getString("frequency", "5")); //Update pref cache
            app.setPreferences(1, sharedPref.getString("downloadURL", "")); //Update pref Cache
            app.setChanged(false);
            app.start(Integer.parseInt(preferences.get(0))); //Start the new alarm
        }

        //Grab the URL and the frequency and toast it.
        String message = preferences.get(1) + ": " + preferences.get(0);//intent.getStringExtra();

        // For our recurring task, we'll just display a message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}