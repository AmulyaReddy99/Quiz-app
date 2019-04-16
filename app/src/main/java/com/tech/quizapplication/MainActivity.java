package com.tech.quizapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginActivity.BottomSheetListener {

    static int position;
    public static final int CAMERA_REQUEST = 9999;

    @Override
    public void onButtonClicked(String text) {
        visibleFeatures(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button resume = findViewById(R.id.resume);
        Button categories = findViewById(R.id.categories);
        Button post = findViewById(R.id.post);

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent QuestionsActivity = new Intent(getApplicationContext(), QuestionsActivity.class);
                startActivity(QuestionsActivity);
            }
        });
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if(!drawer.isDrawerOpen(GravityCompat.START)) drawer.openDrawer(Gravity.START);
                else drawer.closeDrawer(Gravity.END);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PostQuestionActivity = new Intent(getApplicationContext(), PostQuestionActivity.class);
                startActivity(PostQuestionActivity);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        final Menu menu = navigationView.getMenu();
        String[] categ = getResources().getStringArray(R.array.categ);
        for (int k = 0; k < categ.length; k++) {
            menu.add(categ[k]);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.create) {
            Intent PostQuestionActivity = new Intent(getApplicationContext(), PostQuestionActivity.class);
            startActivity(PostQuestionActivity);
        }
        if (id == R.id.account) {
            TextView email = findViewById(R.id.email);
            if(!email.getText().toString().contains("@")) {
                LoginActivity loginActivity = new LoginActivity();
                loginActivity.show(getSupportFragmentManager(), "Login bottom sheet");
            }else{
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, CAMERA_REQUEST);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        position = item.getOrder();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void visibleFeatures(String email){
        TextView emailTV = findViewById(R.id.email);
        emailTV.setVisibility(View.VISIBLE);
        emailTV.setText(email);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImageView avtar = findViewById(R.id.avtar);
            avtar.setImageBitmap(bitmap);
        }
    }
}
