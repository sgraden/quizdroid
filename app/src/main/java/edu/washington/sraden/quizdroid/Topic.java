package edu.washington.sraden.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by steven on 2/2/15.
 */
public class Topic implements Serializable {
    private String shortDesc;
    private String longDesc;
    private ArrayList<Question> questions;
    private int currQuestion;
    private int totalCorrect;

    public Topic() {
        this(null, null, 0, 0);
    }

    public Topic(String description, ArrayList<Question> questions,
                 int currQuestion, int totalCorrect) {
        this.shortDesc = description;
        this.questions = questions;
        this.currQuestion = currQuestion;
        this.totalCorrect = totalCorrect;
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

    public int getCurrQuestion() {
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
