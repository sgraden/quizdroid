package edu.washington.sraden.quizdroid;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by steven on 2/2/15.
 */
public class Topic implements Serializable {
    private String title;
    private String shortDesc;
    private String longDesc;
    private ArrayList<Question> questions;
    private int currQuestion;
    private int totalCorrect;

    public Topic() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Question getCurrQuestion() {
        return questions.get(currQuestion);
    }

    public int getCurrQuestionNum() {
        return currQuestion;
    }

    public void incrementCurrentQuestion() {
        this.currQuestion++;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void incrementTotalCorrect() {
        this.totalCorrect++;
    }
}
