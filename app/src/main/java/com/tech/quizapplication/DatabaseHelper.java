package com.tech.quizapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.cert.TrustAnchor;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Quiz.db";
    public static final String TABLE_NAME = "questions";
    public static final String question = "question";
    public static final String Q_ID = "ID";
    public static final String option1 = "option1";
    public static final String option2 = "option2";
    public static final String option3 = "option3";
    public static final String option4 = "option4";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (Q_ID INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, option1 TEXT, option2 TEXT, option3 TEXT, option4 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String question, String option1, String option2, String option3, String option4){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.question, question);
        contentValues.put(this.option1, option1);
        contentValues.put(this.option2, option2);
        contentValues.put(this.option3, option3);
        contentValues.put(this.option4, option4);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result==-1) return false;
        else return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return res;
    }
}
