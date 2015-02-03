package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Serializable;


public class QuestionActivity extends ActionBarActivity {
    private final String TAG = "QuestionActivity";
    private int chosenValue;
    private Question currQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        chosenValue = -1; //Initializes

        Intent thisActivity = getIntent();
        int currQuestionInt = thisActivity.getIntExtra("currQuestion", 0); //Current question int
        final Topic topic = (Topic) thisActivity.getSerializableExtra("topic"); //Topic object
        currQuestion = topic.getQuestions().get(currQuestionInt); //Current question object

        //Sets question text
        TextView questionText = (TextView) findViewById(R.id.question_text); //Text for question
        questionText.setText(currQuestion.getQuestion()); //Sets question text

        //Sets Text for buttons
        setButtonText(currQuestion);

        Button bSubmit = (Button) findViewById(R.id.submit_question);
        bSubmit.setEnabled(false);
        // On button click Open 2nd activity
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenValue != -1) { //an answer has been chosen
                    if (chosenValue == currQuestion.getCorrectOption()) {
                        topic.incrementTotalCorrect();
                    }
                    //topic.incrementCurrentQuestion();

                    // cannot use just this cuz this refers to the listener, not the outer this
                    Intent nextActivity = new Intent(QuestionActivity.this, QuestionSummaryActivity.class);

                    nextActivity.putExtra("questions", topic);
                    nextActivity.putExtra("userAnswer", chosenValue);

                    startActivity(nextActivity);
                    //finish(); // kill this instance self (this activity)
                }
            }
        });
    }

    public void onRadioButtonClicked(View v) {
        // Is the button now checked?
        //boolean checked = ((RadioButton) v).isChecked();

        // If button was checked and the buttons tag = currQuestions correct answer say passed

        //isCorrect = checked && ((RadioButton)v).getText().equals(
         //       currQuestion.getOptions().get(currQuestion.getCorrectOption()));

        chosenValue = Integer.parseInt(v.getTag().toString());
        ((Button) findViewById(R.id.submit_question)).setEnabled(true); //should enable submit
    }

    public void setButtonText(Question currQuestion) {
        Button b1 = (RadioButton) findViewById(R.id.button1);
        b1.setText(currQuestion.getOptions().get(0));

        Button b2 = (RadioButton) findViewById(R.id.button2);
        b2.setText(currQuestion.getOptions().get(1));

        Button b3 = (RadioButton) findViewById(R.id.button3);
        b3.setText(currQuestion.getOptions().get(2));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
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
