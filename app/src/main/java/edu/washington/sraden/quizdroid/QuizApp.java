package edu.washington.sraden.quizdroid;


import android.app.Application;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Steven on 2/16/15.
 */
public class QuizApp extends Application implements Serializable {
    private static QuizApp instance;

    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            Log.e("QuizApp", "Created new QuizApp");
            throw new RuntimeException("Multiple app exception");
        }
    }

    public static QuizApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("hello", "Holy crap it works!");
    }
}
