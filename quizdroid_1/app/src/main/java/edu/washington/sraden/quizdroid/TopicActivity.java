package edu.washington.sraden.quizdroid;

import android.content.Context;
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
import android.widget.TextView;


public class TopicActivity extends ActionBarActivity {

    private final String TAG = "TopicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        String[] myStringArray = new String[] {"Math", "Physics", "Marvel Super Heroes"};
        //ArrayAdapter context, TextView layout, String[]
        ArrayAdapter<String> arrAdapter =
                new ArrayAdapter<String>(this, R.layout.topic_text_view, myStringArray);
        ListView myListView = (ListView) findViewById(R.id.topic_list);
        myListView.setAdapter(arrAdapter);

        //item click listener override
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Log.i(TAG, "" + position);
                //String clickedtext = (TextView) v.getText();
                Intent nextActivity = new Intent(TopicActivity.this, TopicOverviewActivity.class);
                if (position == 0) { //Math
                    nextActivity.putExtra("topic", "math");
                } else if (position == 1) { //Physics
                    nextActivity.putExtra("topic", "physics");
                } else if (position == 2) { //Marvel
                    nextActivity.putExtra("topic", "marvel");
                }
                startActivity(nextActivity);
                //finish();
            }
        };

        myListView.setOnItemClickListener(mMessageClickedHandler);
    }

//    private class myArrayAdapter extends ArrayAdapter<String> {
//        private Context cont;
//        private int textViewID;
//        private String[] stringArr;
//
//        public myArrayAdapter(Context cont, int textViewID, String[] stringArr) {
//            super(cont, textViewID, stringArr);
//
//            this.cont = cont;
//            this.textViewID = textViewID;
//            this.stringArr = stringArr;
//        }
//    }

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
