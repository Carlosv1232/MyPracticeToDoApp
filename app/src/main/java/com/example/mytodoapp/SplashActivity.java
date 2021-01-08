package com.example.mytodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        final Intent i = new Intent(SplashActivity.this, MainActivity.class);//launch main activity from the splash
        new Handler().postDelayed(new Runnable() {  //this will run the splash screen
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        },1000);    //this will delay the next activity by 1 sec

    }
}