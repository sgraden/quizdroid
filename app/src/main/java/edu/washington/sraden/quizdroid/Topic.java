package edu.washington.sraden.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by steven on 2/2/15.
 */
public class Topic implements Serializable {
    private String description;
    private ArrayList<Question> questions;
    private int currQuestion;
    private int totalCorrect;

    public Topic() {
        this(null, null, 0, 0);
    }

    public Topic(String description, ArrayList<Question> questions,
                 int currQuestion, int totalCorrect) {
        this.description = description;
        this.questions = questions;
        this.currQuestion = currQuestion;
        //this.totalComplete = totalComplete;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
