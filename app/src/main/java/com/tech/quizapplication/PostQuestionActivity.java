package com.tech.quizapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostQuestionActivity extends AppCompatActivity {

    long uid = 0;
//    private Firebase fbapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner categDropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(PostQuestionActivity.this, android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.categ));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categDropdown.setAdapter(myAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputEditText q = findViewById(R.id.que);
                TextInputEditText e = findViewById(R.id.exp);
                RadioGroup r = findViewById(R.id.opt);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference();

                uid = uid+1;

                myRef.child(Long.toString(uid)).child("question").setValue(q.getText().toString());
                myRef.child(Long.toString(uid)).child("explanation").setValue(e.getText().toString());

                Snackbar.make(view, "Successfully inserted. You can go ahead inserting another.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                q.setText("");
                e.setText("");
                r.clearCheck();
            }
        });
    }
}
