package edu.washington.sraden.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
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

        Intent thisActivity = getIntent(); //Grab intent from Topics activity

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager(); //Get fragment Manager
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //Start transaction

            TopicOverviewFragment overview = new TopicOverviewFragment(); // Topic overview fragment

            Bundle overviewBundle = new Bundle(); //Bundle to send to overview fragment
            overviewBundle.putString("topic", thisActivity.getStringExtra("topic")); //add the topic

            overview.setArguments(overviewBundle); //Assign to fragment transaction

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            Topic math = new Topic();
            Topic physics = new Topic();
            Topic marvel = new Topic();
            createTopics(math, physics, marvel);

            final Topic toSend;
            String oldTopic = getArguments().getString("topic");
            if (oldTopic.equals("math")) {
                toSend = math;
            } else if (oldTopic.equals("physics")) {
                toSend = physics;
            } else {
                toSend = marvel;
            }

            TextView tvDescription = (TextView) rootView.findViewById(R.id.topic_description);
            tvDescription.setText(toSend.getDescription());

            Button bStartQuestions = (Button) rootView.findViewById(R.id.start_questions);
            // On button click Open 2nd activity
            bStartQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

                    QuestionFragment question = new QuestionFragment();
                    Bundle questionBundle = new Bundle();
                    questionBundle.putSerializable("topic", toSend);

                    question.setArguments(questionBundle);

                    fragmentTransaction.replace(R.id.container, question);
                    //fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                }
            });
            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
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
            getActivity().setTitle(R.string.title_activity_question);

            chosenValue = -1; //Initializes

            //Intent thisActivity = getIntent();
            Topic topic = (Topic) getArguments().getSerializable("topic"); //Topic object
            currQuestion = topic.getQuestions().get(topic.getCurrQuestion()); //Current question object

            //Sets question text
            TextView questionText = (TextView) rootView.findViewById(R.id.question_text); //Text for question
            questionText.setText(currQuestion.getQuestion()); //Sets question text

            //Sets Text and listeners for buttons
            setButtonText(currQuestion);

            Button bSubmit = (Button) rootView.findViewById(R.id.submit_question);
            bSubmit.setEnabled(false); //Sets submit enabled to false
            final Topic toSend = topic;

            // On button click Open Question Summary activity
            bSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (chosenValue != -1) { //an answer has been chosen
                    if (chosenValue == currQuestion.getCorrectOption()) {
                        toSend.incrementTotalCorrect();
                    }

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

                    QuestionSummaryFragment questionSummary = new QuestionSummaryFragment();
                    Bundle questionBundle = new Bundle();
                    questionBundle.putSerializable("topic", toSend);
                    questionBundle.putInt("userAnswer", chosenValue);

                    questionSummary.setArguments(questionBundle);

                    fragmentTransaction.replace(R.id.container, questionSummary);
                    //fragmentTransaction.addToBackStack(null);

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

            int userAnswerInt = getArguments().getInt("userAnswer", 0); //Intents user answer
            Topic topic = (Topic) getArguments().getSerializable("topic"); //Grabs Topic from intent

            ArrayList<Question> topicQuestionList = topic.getQuestions();

            Question currQuestion = topicQuestionList.get(topic.getCurrQuestion());
            ArrayList<String> currQuestionOptions = currQuestion.getOptions();

            String userAnswerTxt = currQuestionOptions.get(userAnswerInt);
            String correctAnswerTxt = currQuestionOptions.get(currQuestion.getCorrectOption());

            TextView userAnswer = (TextView) rootView.findViewById(R.id.user_answer);
            userAnswer.setText("You chose " + userAnswerTxt +
                    " for your answer. The correct answer is " + correctAnswerTxt + ".");//, correctAnswerTxt));

            TextView userTotal = (TextView) rootView.findViewById(R.id.user_total);
            userTotal.setText("You have " + topic.getTotalCorrect() +
                    " out of " + (topic.getCurrQuestion() + 1) + " correct.");//, topic.getCurrQuestion()));

            final Topic toSend = topic;
            Button bNextQuestion = (Button) rootView.findViewById(R.id.summary_next);

            if (topicQuestionList.size() - 1 == topic.getCurrQuestion()) {
                bNextQuestion.setText("Return to Home");
                noMoreQuestions = true;
            }

            bNextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (!noMoreQuestions) {
                    toSend.incrementCurrentQuestion();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);

                    QuestionFragment question = new QuestionFragment();
                    Bundle questionBundle = new Bundle();
                    questionBundle.putSerializable("topic", toSend);

                    question.setArguments(questionBundle);

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
