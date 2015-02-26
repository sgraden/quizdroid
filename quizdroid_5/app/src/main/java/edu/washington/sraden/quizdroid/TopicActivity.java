package edu.washington.sraden.quizdroid;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class TopicActivity extends ActionBarActivity {

    private final String TAG = "TopicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        TopicListAdapter topicListAdapter =
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

    @Override
    public void onResume() {
        super.onResume();
        QuizApp app = QuizApp.getInstance();
        //Grab the existing alarm based on ID and check if it is already made.
        Intent alarmIntent = new Intent(TopicActivity.this, AlarmReceiver.class);
        boolean started = (PendingIntent.getBroadcast(TopicActivity.this, 1, alarmIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        if (started) { //If alarm already exists
            Toast.makeText(TopicActivity.this, "Alarm Exists", Toast.LENGTH_SHORT).show();
        } else {
            app.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QuizApp.getInstance().cancel(); //Cancels alarm on close
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
}
