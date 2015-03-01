package edu.washington.sraden.quizdroid;

//Currently not displaying anything on front page????
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Steven on 2/16/15.
 */
public class QuizApp extends Application {
    //Repository Info
    private static QuizApp instance;
    private TopicsRepo repository;
    private Context context;

    //Alarm Intent Info
    private boolean settingsChanged = false;
    private PendingIntent pendingAlarmIntent; //Background intent for the alarm
    private boolean started; //Whether the alarm has be started
    private ArrayList<String> preferences = new ArrayList<>(); //0->Frequency, 1->URL

    //Network Call Info
    private NetworkUtil network;


    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            Log.e("exception", "Error: Created new QuizApp");
            throw new RuntimeException("Multiple app exception");
        }

    }

    public static QuizApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /*Creating intents for creating the alarm*/
        Intent alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);
        pendingAlarmIntent = PendingIntent.getBroadcast(QuizApp.this, 1,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Sets the default values - false means only on first use.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        preferences.add(sharedPref.getString("frequency", "5")); //Initialize the arr
        preferences.add(sharedPref.getString("downloadURL", "")); //Initialize the arr
        network = new NetworkUtil(this); //Initialize

    }

    /*START OF GETTER/SETTER*/
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

    public NetworkUtil getNetwork() {
        return network;
    }
    /*END OF GETTER/SETTER*/

    /**
     * Initializes the Topics repository based on which file is chosen
     */
    public void initializeTopics() {
        File filePath = new File(this.getFilesDir().getAbsolutePath() + "/quizdata.json");
        //Toast.makeText(this, "Initializing topics: " + doesFileExist(filePath), Toast.LENGTH_SHORT).show();

        if (doesFileExist(filePath)) { //file does not exist
            Log.i("info", "File does exist");
            try {
                this.repository = new TopicsRepo(readJSONFile(true));
            } catch (Exception e) {
                Log.e("exception", "Initialize repo based on file failed: " + e.toString());
            }
        } else {
            Log.i("info", "No file found. Loading from assets");
            //Load from default file
            try {
                this.repository = new TopicsRepo(readJSONFile(false));
            } catch (Exception e) {
                Log.e("exception", "Initialize repo based on file failed: " + e.toString());
            }
        }
    }

    /**
     * Starts the alarm intent with a time in minutes from the preferences
     */
    public void startAlarm() {
        setAlarmStatus(true);
        int interval = Integer.parseInt(preferences.get(0)) * 1000;// * 60; //Converts min to milli;

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingAlarmIntent);
        Log.i("alarm", "Alarm Started");
    }

    /**
     * Cancels the one alarm intent
     */
    public void cancelAlarm() {
        setAlarmStatus(false);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingAlarmIntent);
        pendingAlarmIntent.cancel();
        Log.i("alarm", "Alarm Canceled");
    }

    /**
     * Reads data from either default_quiz.json or quizdata.json and returns JSONArray
     *
     * @Return JSONArray -> data from file in JSONArray
     */
    private JSONArray readJSONFile() {
        return readJSONFile(false);
    }

    /**
     * Reads data from either default_quiz.json or quizdata.json and returns JSONArray
     *
     * @param useFile -> Whether the asset or the quizdata should be used
     * @return JSONArray -> data from file in JSONArray
     */
    private JSONArray readJSONFile (boolean useFile) {
        JSONArray topicsJSONArray = new JSONArray();
        try {
            //Log.i("info", "Reading path: " + filePath);
            InputStream inputStream;
            if (useFile) { //Use downloaded file
                Log.i("info", "Initialized with Quizdata");
                String filePath = QuizApp.this.getFilesDir().getAbsolutePath() + "/quizdata.json";
                inputStream = new FileInputStream(filePath);
            } else {
                Log.i("info", "Initialized with Assets");
                AssetManager am = getAssets();
                inputStream = am.open("default_quiz.json");
            }

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            topicsJSONArray = new JSONArray(new String(buffer, "UTF-8"));
        } catch (IOException e) {
            Log.e("exception", "File read failed: " + e.toString());
        } catch (JSONException e) {
            Log.e("exception", "JSON Array conversion failed: " + e.toString());
        }
        return topicsJSONArray;
    }

    public boolean checkNetwork() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo(); //Return null if no network info

        return netInfo != null && netInfo.isConnected(); //If it is connected and can make calls
    }

    //Checks if airplane mode is active. Different calls for different OS
    public boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) { //If prior to JB
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    private boolean doesFileExist(File filePath) {
        return filePath.exists() && !filePath.isDirectory();
    }

}
