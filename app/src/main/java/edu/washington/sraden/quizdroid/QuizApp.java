package edu.washington.sraden.quizdroid;


import android.app.Application;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Steven on 2/16/15.
 */
public class QuizApp extends Application implements TopicRepository {

    private final static String TAG = "QuizApp";
    private static QuizApp instance;
    private ArrayList<Topic> topicList;
    private Integer currTopic;

    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            Log.e(TAG, "Created new QuizApp");
            throw new RuntimeException("Multiple app exception");
        }
    }

    public static QuizApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.topicList = createQuestions(); //Create topics with their questions
        Log.i(TAG, "Holy crap it works!");
    }

    public void setCurrTopic(int currTopic) {
        this.currTopic = currTopic;
    }

    public ArrayList<Topic> getTopics() {
        return topicList;
    }

    private ArrayList<Topic> createQuestions() {
        Topic math = setMathTopic();
        Topic physics = setPhysicsTopic();
        Topic marvel = setMarvelTopic();

        return new ArrayList<Topic>(Arrays.asList(math, physics, marvel));
    }

    private Topic setMathTopic() {
        Topic topic = new Topic();
        Question question = new Question();
        ArrayList<Question> questionList = new ArrayList<>();

        //math creation
        topic.setTitle("Math");
        topic.setLongDesc("Math is the study of topics such as quantity, structure, space, and change");
        topic.setShortDesc("A math quiz");
        //Q1
        question.setQuestion("2 + 2 =");
        question.setOptions(new ArrayList<>(Arrays.asList("4", "2", "22", "100")));
        question.setCorrectOption(0);
        questionList.add(question);
        question = new Question();
        //Q2
        question.setQuestion("7 X 7 =");
        question.setOptions(new ArrayList<>(Arrays.asList("1000", "7", "49", "14")));
        question.setCorrectOption(2);
        questionList.add(question);
        question = new Question();
        //Q3
        question.setQuestion("3 * 3 * 3 * 3 =");
        question.setOptions(new ArrayList<>(Arrays.asList("69", "81", "3333", "11")));
        question.setCorrectOption(1);
        questionList.add(question);
        question = new Question();
        //Q4
        topic.setQuestions(questionList);
        question.setQuestion("4 / 2 =");
        question.setOptions(new ArrayList<>(Arrays.asList(".666", ".792", "91", "2")));
        question.setCorrectOption(3);
        questionList.add(question);
        question = new Question();

        return topic;
    }

    private Topic setPhysicsTopic() {
        Topic topic = new Topic();
        Question question = new Question();
        ArrayList<Question> questionList = new ArrayList<>();

        //Physics Creation
        topic.setTitle("Physics");
        topic.setLongDesc("Physics - About gravity and...shtuff");
        topic.setShortDesc("A physics quiz");
        //Q1
        question.setQuestion("Acceleration of an object due to gravity?");
        question.setOptions(new ArrayList<>(Arrays.asList("9.8 m/s/s", "10 mi/s", "1 in/s", "Magic")));
        question.setCorrectOption(0);
        questionList.add(question);
        question = new Question();
        //Q2
        question.setQuestion("If you shoot something backwards the same rate you are moving forwards, what happens to the object?");
        question.setOptions(new ArrayList<>(Arrays.asList("It goes backwards twice as fast", "It doesn't move!", "...shoots up", "Magic")));
        question.setCorrectOption(1);
        questionList.add(question);
        question = new Question();
        //Q3
        question.setQuestion("Feather and a bowling ball are dropped in a vacuum. Which hits the ground first?");
        question.setOptions(new ArrayList<>(Arrays.asList("neither hit!", "They both go up!", "Hit at the same time.", "Magic")));
        question.setCorrectOption(2);
        questionList.add(question);
        question = new Question();
        //Q4
        topic.setQuestions(questionList);
        question.setQuestion("Force =");
        question.setOptions(new ArrayList<>(Arrays.asList("Mass X Acceleration", "Jedi", "Muscle", "Magic")));
        question.setCorrectOption(0);
        questionList.add(question);

        return topic;
    }

    private Topic setMarvelTopic() {
        Topic topic = new Topic();
        Question question = new Question();
        ArrayList<Question> questionList = new ArrayList<>();

        //Marvel Creation
        topic.setTitle("Marvel Super Heroes");
        topic.setLongDesc("Marvel Comics, better than DC");
        topic.setShortDesc("A marvel quiz");
        //Q1
        question.setQuestion("Iron Man's real name?");
        question.setOptions(new ArrayList<>(Arrays.asList("Abigail", "Banana", "Tony Stark", "Mark")));
        question.setCorrectOption(2);
        questionList.add(question);
        question = new Question();
        //Q2
        question.setQuestion("Name of Thor's hammer?");
        question.setOptions(new ArrayList<>(Arrays.asList("Mjolnir", "Johnny", "Thor's Dad", "Yorik")));
        question.setCorrectOption(0);
        questionList.add(question);
        question = new Question();
        //Q3
        question.setQuestion("Who died in The Avengers?");
        question.setOptions(new ArrayList<>(Arrays.asList("Captain America", "A rabbit", "Agent Coulson", "Mark")));
        question.setCorrectOption(2);
        questionList.add(question);
        question = new Question();
        //Q4
        topic.setQuestions(questionList);
        question.setQuestion("Best Marvel character?");
        question.setOptions(new ArrayList<>(Arrays.asList("Iron Man", "Spider Man", "Agent Coulson", "Steven")));
        question.setCorrectOption(3);
        questionList.add(question);

        return topic;
    }

    /*BEGIN TOPICREPOSITORY INTERFACE*/
    public String getTitle() {
        return topicList.get(currTopic).getTitle();
    }

    public void setTitle(String title) {
        topicList.get(currTopic).setTitle(title);
    }

    public String getShortDesc() {
        return topicList.get(currTopic).getShortDesc();
    }

    public void setShortDesc(String shortDesc) {
        topicList.get(currTopic).setShortDesc(shortDesc);
    }

    public String getLongDesc() {
        return topicList.get(currTopic).getLongDesc();
    }

    public void setLongDesc(String longDesc) {
        topicList.get(currTopic).setLongDesc(longDesc);
    }

    public ArrayList<Question> getQuestions() {
        return topicList.get(currTopic).getQuestions();
    }

    public void setQuestions(ArrayList<Question> questions) {
        topicList.get(currTopic).setQuestions(questions);
    }

    public Question getCurrQuestion() {
        return topicList.get(currTopic).getCurrQuestion();
    }

    public int getCurrQuestionNum() {
        return topicList.get(currTopic).getCurrQuestionNum();
    }

    public void incrementCurrentQuestion() {
        topicList.get(currTopic).incrementCurrentQuestion();
    }

    public int getTotalCorrect() {
        return topicList.get(currTopic).getTotalCorrect();
    }

    public void incrementTotalCorrect() {
        topicList.get(currTopic).incrementTotalCorrect();
    }
    /*END TOPICREPOSITORY INTERFACE*/


}
