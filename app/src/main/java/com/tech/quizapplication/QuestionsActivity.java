package com.tech.quizapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionsActivity extends AppCompatActivity {

    private ViewPager mViewPager; String[] categ; int category = MainActivity.position;

    String question; TextView textView; RadioButton rd;

    public void render_questions(JSONObject response, int each){
        try {
            JSONObject j = response.getJSONArray("results").getJSONObject(each);

            question = j.get("question").toString();
//            Log.e("input",Integer.toString(MainActivity.position));
//            Log.e("categ: ",j.get("category").toString());

            JSONArray options = j.getJSONArray("incorrect_answers");
            Log.e("Options: ",options.toString());

            String correct_option = j.get("correct_answer").toString();
            rd = findViewById(R.id.option0); rd.setText(options.get(0).toString());
            rd = findViewById(R.id.option1); rd.setText(options.get(1).toString());
            rd = findViewById(R.id.option2); rd.setText(options.get(2).toString());
            rd = findViewById(R.id.option3); rd.setText(correct_option);
            displayQuestion();

        } catch (JSONException e) {
            Log.e("ERROR Response",e.toString());
        }
        textView = findViewById(R.id.q);
        textView.setText(question);
    }

    public void fetch_questions(String difficulty){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://opentdb.com/api.php?amount=1&category="+category+"&difficulty="+difficulty+"&type=multiple";
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        for(int i = 0; i<10; i++) {
                            render_questions(response, i);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Response", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        TabLayout mTabLayout = findViewById(R.id.main_tab);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position==0)
                    fetch_questions("easy");
                if(position==1)
                    fetch_questions("medium");
                if(position==2)
                    fetch_questions("hard");
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//        Resources res = getResources();
//        categoryView= findViewById(R.id.easy);
//        categ = res.getStringArray(R.array.categ);
//        categoryView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_activity, categ));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayQuestion(){
        final TextView a = findViewById(R.id.a);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final DatabaseReference myRef2 = myRef.child("quiz");

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String name = dataSnapshot.child("explanation").getValue().toString();
                    a.setText(name);
                } catch (Exception e) {
                    a.setText(e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
