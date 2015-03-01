package edu.washington.sraden.quizdroid;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class TopicActivity extends ActionBarActivity {

    private final String TAG = "TopicActivity";
    private TopicListAdapter topicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("activityspy", "Created");
        setContentView(R.layout.activity_topic);

        /*Initialize Application*/
        QuizApp app = QuizApp.getInstance();

        if (app.checkNetwork()) { //if network is available start alarm
            app.startAlarm();
        } else { //Otherwise alert user and check airplane mode etc.
            if (app.isAirplaneModeOn(this)) {
                //Give user option to shut off airplane
                alertAirplaneMode(this);
            } else {
                Toast.makeText(this, "No network connect detected", Toast.LENGTH_SHORT).show();
            }
        }
        //File should be downloaded by this point
        app.initializeTopics(); //regardless of everything we will try to load the file
        /*Initialize application*/

        createTopicsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("activityspy", "Resumed");

        QuizApp app = QuizApp.getInstance();
        //Grab the existing alarm based on ID and check if it is already made.
        Intent alarmIntent = new Intent(TopicActivity.this, AlarmReceiver.class);
        boolean started = (PendingIntent.getBroadcast(TopicActivity.this, 1, alarmIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        if (started) { //If alarm already exists
            Log.i("alarm", "Alarm already exists");
        } else {
            Log.i("alarm", "Alarm started");
            app.startAlarm();
        }

        modifyTopicList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("activityspy", "Destroyed");
        QuizApp.getInstance().cancelAlarm(); //Cancels alarm on close
    }

    //Navigate to airplane and back = paused -> restarted -> started -> resumed Need to refresh front screen in there

    @Override
    public void onPause() {
        super.onPause();
        Log.i("activityspy", "Paused");
        //QuizApp.getInstance().cancelAlarm();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("activityspy", "Started");
    }

    @Override
    public void onRestart() { //Reload screen
        super.onRestart();
        Log.i("activityspy", "Restarted");
        //QuizApp.getInstance().startAlarm();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("activityspy", "Stopped");
        //QuizApp.getInstance().cancelAlarm();
    }

    private void createTopicsList() {
        topicListAdapter =
                new TopicListAdapter(this, R.layout.topic_list_adapter_layout, QuizApp.getInstance().getRepository().getTopics());
        ListView myListView = (ListView) findViewById(R.id.topic_list);
        myListView.setAdapter(topicListAdapter);

        //item click listener override
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //Launches TopicQuestionFragments activity
                Intent nextActivity = new Intent(TopicActivity.this, TopicQuestionFragments.class);
                QuizApp.getInstance().getRepository().setCurrTopic(position); //Setup current topic
                startActivity(nextActivity);
            }
        };

        myListView.setOnItemClickListener(mMessageClickedHandler);
    }

    private void modifyTopicList() {
        //Toast.makeText(this, "Modified the list", Toast.LENGTH_SHORT).show();
        Log.i("info", "Modifying Topics List and updating Listview");
        QuizApp.getInstance().initializeTopics();
        topicListAdapter.clear();
        topicListAdapter.addAll(QuizApp.getInstance().getRepository().getTopics());
        topicListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                inflateSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item); //default returns false
        }
    }

    //Inflates the SettingsActivity Activity
    private void inflateSettings() {
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        QuizApp.getInstance().setChanged(true);
    }

    public void alertAirplaneMode(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Airplane Mode Active")
                .setMessage("Airplane mode is active. Cannot update file. Would you like to shut it off?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Context context = getApplicationContext();
                        startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //Context context = getApplicationContext();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
