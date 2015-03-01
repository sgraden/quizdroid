package edu.washington.sraden.quizdroid;

import java.util.ArrayList;

/**
 * Created by Steven on 2/16/15.
 */
public interface TopicRepository {

    public ArrayList<Question> getQuestions();

    public void setQuestions(ArrayList<Question> questions);

    public Question getCurrQuestion();

    public int getCurrQuestionNum();

    public void incrementCurrentQuestion();

    public int getTotalCorrect();

    public void incrementTotalCorrect();
}
