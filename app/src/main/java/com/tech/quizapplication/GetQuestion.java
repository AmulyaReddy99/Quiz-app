package com.tech.quizapplication;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class GetQuestion extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public String question;
    public String explanation;
    public int count=0;

    public GetQuestion() {
        count+=1;
    }

    public int getCount() {
        return(count);
    }

    public GetQuestion(String question, String explanation) {
        this.question = question;
        Log.e("Question got::",question);
        this.explanation = explanation;
    }
}
