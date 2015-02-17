package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class TopicOverviewActivity extends ActionBarActivity {
    private final String TAG = "TopicOverviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        Intent thisActivity = getIntent();
        Topic math = new Topic();
        Topic physics = new Topic();
        Topic marvel = new Topic();
        createTopics(math, physics, marvel);

        final Topic toSend;
        String oldTopic = getIntent().getStringExtra("topic");
        if (oldTopic.equals("math")) {
            toSend = math;
        } else if (oldTopic.equals("physics")) {
            toSend = physics;
        } else {
            toSend = marvel;
        }

        TextView tvDescription = (TextView) findViewById(R.id.topic_description);
        tvDescription.setText(toSend.getDescription());

        Button bStartQuestions = (Button) findViewById(R.id.start_questions);
        // On button click Open 2nd activity
        bStartQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cannot use just this cuz this refers to the listener, not the outer this
                Intent nextActivity = new Intent(TopicOverviewActivity.this, QuestionActivity.class);

                nextActivity.putExtra("topic", toSend);

                startActivity(nextActivity);
                //finish(); // kill this instance self (this activity)
            }
        });
    }

    public void createTopics(Topic math, Topic physics, Topic marvel) {
        ArrayList<Question> quest = new ArrayList<Question>();
        //math creation
        math.setDescription("Math is the study of topics such as quantity, structure, space, and change");
        quest.add(new Question("2 + 2 =",
                new ArrayList<>(Arrays.asList("4", "2", "22", "100")), 0));
        quest.add(new Question("7 x 7 =",
                new ArrayList<>(Arrays.asList("1000", "7", "49", "14")), 2));
        quest.add(new Question("3 * 3 * 3 * 3 =",
                new ArrayList<>(Arrays.asList("69", "61", "3333", "11")), 1));
        quest.add(new Question("4 / 2 =",
                new ArrayList<>(Arrays.asList(".666", ".792", "91", "2")), 3));
        math.setQuestions(quest);

        //physics creation
        quest = new ArrayList<>(); //Empties ArrayList
        physics.setDescription("Physics - About gravity and...shtuff");
        quest.add(new Question("Acceleration of an object due to gravity?",
                new ArrayList<>(Arrays.asList("9.8 m/s/s", "10 mi/s", "1 in/s", "Magic")), 0));
        quest.add(new Question("If you shoot something backwards the same rate you are moving " +
                "forwards, what happens to the object?",
                new ArrayList<>(Arrays.asList("It goes backwards twice as fast",
                        "It doesn't move!", "...shoots up", "Magic")), 1));
        quest.add(new Question("Feather and a bowling ball are dropped in a vacuum. " +
                "Which hits the ground first?",
                new ArrayList<>(Arrays.asList("neither hit!", "They both go up!",
                        "Hit at the same time.", "Magic")), 2));
        quest.add(new Question("Force = ",
                new ArrayList<>(Arrays.asList("Mass X Acceleration", "Jedi",
                        "Muscle", "Magic")), 0));
        physics.setQuestions(quest);

        //marvel creation
        quest = new ArrayList<>(); //Empties arraylist
        marvel.setDescription("Marvel Comics, better than DC");
        quest.add(new Question("Iron Man's real name?",
                new ArrayList<>(Arrays.asList("Abigail", "Banana", "Tony Stark", "Mark")), 2));
        quest.add(new Question("Name of Thor's hammer?",
                new ArrayList<>(Arrays.asList("Mjolnir", "Johnny", "Thor's Dad", "Yorik")), 0));
        quest.add(new Question("Who died in The Avengers?",
                new ArrayList<>(Arrays.asList("Captain America", "A rabbit", "Agent Colson", "Mark")), 2));
        quest.add(new Question("Best Marvel character?",
                new ArrayList<>(Arrays.asList("Iron Man", "Spider Man", "Agent Colson", "Steven")), 3));
        marvel.setQuestions(quest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_overview, menu);
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
