package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class QuestionSummaryActivity extends ActionBarActivity {

    private final String TAG = "QuestionSummaryActivity";
    private boolean noMoreQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_summary);

        noMoreQuestions = false; //intitalize noMoreQuestions

        Intent thisActivity = getIntent();
        int userAnswerInt = thisActivity.getIntExtra("userAnswer", 0); //Intents user answer
        Topic topic = (Topic) thisActivity.getSerializableExtra("topic"); //Grabs Topic from intent

        ArrayList<Question> topicQuestionList = topic.getQuestions();

        Question currQuestion = topicQuestionList.get(topic.getCurrQuestion());
        ArrayList<String> currQuestionOptions = currQuestion.getOptions();

        String userAnswerTxt = currQuestionOptions.get(userAnswerInt);
        String correctAnswerTxt = currQuestionOptions.get(currQuestion.getCorrectOption());

        TextView userAnswer = (TextView) findViewById(R.id.user_answer);
        userAnswer.setText("You chose " + userAnswerTxt +
                " for your answer. The correct answer is " + correctAnswerTxt + ".");//, correctAnswerTxt));

        TextView userTotal = (TextView) findViewById(R.id.user_total);
        userTotal.setText("You have " + topic.getTotalCorrect() +
                " out of " + (topic.getCurrQuestion() + 1) + " correct.");//, topic.getCurrQuestion()));

        final Topic toSend = topic;
        Button bNextQuestion = (Button) findViewById(R.id.summary_next);

        if (topicQuestionList.size() - 1 == topic.getCurrQuestion()) {
            bNextQuestion.setText("Return to Home");
            noMoreQuestions = true;
        }

        bNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noMoreQuestions) {
                    toSend.incrementCurrentQuestion();

                    // cannot use just this cuz this refers to the listener, not the outer this
                    Intent nextActivity = new Intent(QuestionSummaryActivity.this, QuestionActivity.class);

                    nextActivity.putExtra("topic", toSend);

                    startActivity(nextActivity);
                } else {
                    Intent nextActivity = new Intent(QuestionSummaryActivity.this, TopicActivity.class);
                    startActivity(nextActivity);
                }
                finish(); // kill this instance self (this activity)
            }
        });
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
