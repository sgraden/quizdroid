package edu.washington.sraden.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by steven on 2/2/15.
 */
public class Question implements Serializable {
    private String question;
    private ArrayList<String> options;
    private int correctOption;


    public Question(String question, ArrayList<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}
