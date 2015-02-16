package edu.washington.sraden.quizdroid;

import java.util.ArrayList;

/**
 * Created by Steven on 2/16/15.
 */
public interface TopicRepository {

    public String getShortDesc();

    public void setShortDesc(String shortDesc);

    public String getLongDesc();

    public void setLongDesc(String longDesc);

    public ArrayList<Question> getQuestions();

    public void setQuestions(ArrayList<Question> questions);

    public int getCurrQuestion();

    public void incrementCurrentQuestion();

    public int getTotalCorrect();

    public void incrementTotalCorrect();
}
