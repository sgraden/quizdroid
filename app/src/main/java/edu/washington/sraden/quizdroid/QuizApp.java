package edu.washington.sraden.quizdroid;


import android.app.Application;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Steven on 2/16/15.
 */
public class QuizApp extends Application {

    private final static String TAG = "QuizApp";
    private static QuizApp instance;
    private TopicsRepo repository;


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
        this.repository = new TopicsRepo();
        Log.i(TAG, "Holy crap it works!");
    }

    public TopicsRepo getRepository() {
        return repository;
    }

}
