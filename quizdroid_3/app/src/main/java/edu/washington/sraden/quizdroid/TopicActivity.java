package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
