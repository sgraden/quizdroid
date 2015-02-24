package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class TopicQuestionFragments extends ActionBarActivity {

    private final String TAG = "TopicQuestionFragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_question_fragments);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager(); //Get fragment Manager
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //Start transaction

            TopicOverviewFragment overview = new TopicOverviewFragment(); // Topic overview fragment

            fragmentTransaction.add(R.id.container, overview);
            fragmentTransaction.commit(); //Start fragment Topic overview

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_question_fragments, menu);
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
        // Display the fragment as the main content.
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(i, 1);
    }


    /**
     * A fragment for the Topic Overview.
     */
    public static class TopicOverviewFragment extends Fragment {

        public TopicOverviewFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_topic_overview, container, false);

            //Set the textview with the topic description
            TextView tvDescription = (TextView) rootView.findViewById(R.id.topic_description);
            tvDescription.setText(QuizApp.getInstance().getRepository().getLongDesc());

            // On button click Open Question activity
            Button bStartQuestions = (Button) rootView.findViewById(R.id.start_questions);
            bStartQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

                    QuestionFragment question = new QuestionFragment();

                    fragmentTransaction.replace(R.id.container, question);

                    fragmentTransaction.commit();
                }
            });
            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
        }

    }

    /**
     * A fragment for the Question.
     */
    public static class QuestionFragment extends Fragment {

        View rootView;
        private int chosenValue;
        private Question currQuestion;

        public QuestionFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_question, container, false);
            getActivity().setTitle(R.string.title_activity_question); //Set the title of the activity

            chosenValue = -1; //Initializes

            currQuestion = QuizApp.getInstance().getRepository().getCurrQuestion();//topic.getQuestions().get(topic.getCurrQuestion()); //Current question object

            //Sets question text
            TextView questionText = (TextView) rootView.findViewById(R.id.question_text); //Text for question
            questionText.setText(currQuestion.getQuestion()); //Sets question text

            //Sets Text and listeners for buttons
            setButtonText(currQuestion);

            Button bSubmit = (Button) rootView.findViewById(R.id.submit_question);
            bSubmit.setEnabled(false); //Sets submit enabled to false as default

            // On button click Open Question Summary activity
            bSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (chosenValue != -1) { //an answer has been chosen
                    if (chosenValue == currQuestion.getCorrectOption()) {
                        QuizApp.getInstance().getRepository().incrementTotalCorrect();
                    }

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

                    QuestionSummaryFragment questionSummary = new QuestionSummaryFragment();
                    Bundle questionBundle = new Bundle();
                    //questionBundle.putSerializable("topic", toSend);
                    questionBundle.putInt("userAnswer", chosenValue);
                    //Log.i("hello", "Sent: " + questionBundle.getInt("userAnswer"));

                    questionSummary.setArguments(questionBundle);

                    fragmentTransaction.replace(R.id.container, questionSummary);
                    fragmentTransaction.commit();
                }
                }
            });

            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        public void onRadioButtonClicked(View v) {
            chosenValue = Integer.parseInt(v.getTag().toString());
            ((Button) rootView.findViewById(R.id.submit_question)).setEnabled(true); //should enable submit
        }

        public void setButtonText(Question currQuestion) {
            Button b1 = (RadioButton) rootView.findViewById(R.id.button1);
            b1.setText(currQuestion.getOptions().get(0));
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v);
                }
            });

            Button b2 = (RadioButton) rootView.findViewById(R.id.button2);
            b2.setText(currQuestion.getOptions().get(1));
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v);
                }
            });

            Button b3 = (RadioButton) rootView.findViewById(R.id.button3);
            b3.setText(currQuestion.getOptions().get(2));
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v);
                }
            });

            Button b4 = (RadioButton) rootView.findViewById(R.id.button4);
            b4.setText(currQuestion.getOptions().get(3));
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v);
                }
            });
        }

    }

    /**
     * A fragment for the Question Summary.
     */
    public static class QuestionSummaryFragment extends Fragment {

        View rootView;
        private boolean noMoreQuestions;

        public QuestionSummaryFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_question_summary, container, false);
            getActivity().setTitle(R.string.title_activity_question_summary);



            noMoreQuestions = false; //intitalize noMoreQuestions
            int userAnswerInt = getArguments().getInt("userAnswer"); //Intents user answer

            QuizApp app = QuizApp.getInstance();
            ArrayList<Question> topicQuestionList = app.getRepository().getQuestions();

            Question currQuestion = app.getRepository().getCurrQuestion();
            ArrayList<String> currQuestionOptions = currQuestion.getOptions();

            String userAnswerTxt = currQuestionOptions.get(userAnswerInt);
            String correctAnswerTxt = currQuestionOptions.get(currQuestion.getCorrectOption());

            TextView userAnswer = (TextView) rootView.findViewById(R.id.user_answer);
            userAnswer.setText("You chose: " + userAnswerTxt);
            TextView correctAnswer = (TextView) rootView.findViewById(R.id.correct_answer);
            correctAnswer.setText("Correct answer: " + correctAnswerTxt);

            TextView userTotal = (TextView) rootView.findViewById(R.id.user_total);
            userTotal.setText("You have " + app.getRepository().getTotalCorrect() +
                    " out of " + (app.getRepository().getCurrQuestionNum() + 1) + " correct.");//, topic.getCurrQuestion()));

            //final Topic toSend = topic;
            Button bNextQuestion = (Button) rootView.findViewById(R.id.summary_next);
            if (topicQuestionList.size() - 1 == app.getRepository().getCurrQuestionNum()) {
                bNextQuestion.setText("Return to Home");
                noMoreQuestions = true;
            }

            bNextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (!noMoreQuestions) {
                    QuizApp.getInstance().getRepository().incrementCurrentQuestion();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
                    QuestionFragment question = new QuestionFragment();
                    fragmentTransaction.replace(R.id.container, question);

                    fragmentTransaction.commit();
                } else {
                    Intent nextActivity = new Intent(getActivity(), TopicActivity.class);
                    startActivity(nextActivity);
                    getActivity().finish(); // kill this instance self (this activity)
                }
                }
            });

            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
        }


    }
}
