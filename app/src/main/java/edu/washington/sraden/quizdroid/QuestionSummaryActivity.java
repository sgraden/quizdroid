package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class QuestionSummaryActivity extends ActionBarActivity {

    private final String TAG = "QuestionSummaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_summary);

        Intent thisActivity = getIntent();
        int userAnswerInt = thisActivity.getIntExtra("userAnswer", 0);
        Topic topic = (Topic) thisActivity.getSerializableExtra("topic");
        //final Topic topic = (Topic) thisActivity.getSerializableExtra("topic"); //Topic object

        Question currQuestion = topic.getQuestions().get(topic.getCurrQuestion());
        ArrayList<String> currQuestionOptions = currQuestion.getOptions();

        String userAnswerTxt = currQuestionOptions.get(userAnswerInt);
        String correctAnswerTxt = currQuestionOptions.get(currQuestion.getCorrectOption());

        TextView userAnswer = (TextView) findViewById(R.id.user_answer);
        userAnswer.setText(String.format(getResources().getString(R.string.user_answer),
                userAnswerTxt));//, correctAnswerTxt));

        TextView userTotal = (TextView) findViewById(R.id.user_total);
        userTotal.setText(String.format(getResources().getString(R.string.user_total),
                topic.getTotalCorrect()));//, topic.getCurrQuestion()));

        topic.incrementCurrentQuestion(); //Increments to next question
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_summary, menu);
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
