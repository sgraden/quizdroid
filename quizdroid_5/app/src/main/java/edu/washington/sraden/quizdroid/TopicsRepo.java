package edu.washington.sraden.quizdroid;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Steven on 2/17/15.
 */
public class TopicsRepo implements TopicRepository {

    private ArrayList<Topic> topicList;
    private Integer currTopic;

    public TopicsRepo(JSONArray jsonArr) {
        Log.i("info", "Initializing Topics Repo: " + jsonArr.toString());
        this.topicList = createTopics(jsonArr); //Create topics with their questions
    }

    private ArrayList<Topic> createTopics (JSONArray jsonArray) {
        //Try to read the JSONArray into a topic
        ArrayList<Topic> topicList = new ArrayList<>(10);
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonTopic = jsonArray.getJSONObject(i);
                Topic topic = new Topic();
                topic.setTitle(jsonTopic.getString("title"));   //Get topic title
                topic.setLongDesc(jsonTopic.getString("desc")); //Get topic Long Desc
                topic.setQuestions(createQuestions(jsonTopic)); //Set questions

                topicList.add(topic);
            }
        } catch (JSONException e) {
            Log.d("exception", "Topics creation failed: " + e.toString());
        }
        return topicList;
    }

    private ArrayList<Question> createQuestions (JSONObject jsonTopic) {
        ArrayList<Question> questionList = new ArrayList<>(5);
        try {
            //Grab the only object within the questions object
            JSONObject jsonQuestionsInfo =
                    new JSONArray(jsonTopic.getString("questions")).getJSONObject(0);
            Question question = new Question();
            question.setQuestion(jsonQuestionsInfo.getString("text")); //Get the question text
            question.setCorrectOption(Integer.parseInt(jsonQuestionsInfo.getString("answer")) - 1); //Parse the correct answer

            //Create the list of question options
            JSONArray questionOptions = jsonQuestionsInfo.getJSONArray("answers");
            ArrayList<String> optionsList = new ArrayList<>(5);
            for (int i = 0; i < questionOptions.length(); i++) {
                optionsList.add(questionOptions.getString(i));
            }
            question.setOptions(optionsList); //Set the question options

            questionList.add(question);

        } catch (JSONException e) {
            Log.d("exception", "Questions creation failed: " + e.toString());

        }
        return questionList;
    }

    public void setCurrTopic (int currTopic) {
        this.currTopic = currTopic;
    }

    public ArrayList<Topic> getTopics () {
        return topicList;
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

    /*
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
*/

}
