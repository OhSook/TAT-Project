package com.example.tatproject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_loading);

        ImageView imageView = findViewById(R.id.logo);
        ImageView imageView1 = findViewById(R.id.logo_text);

        Animation left = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_left);
        Animation right = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_right);

        imageView.startAnimation(left);
        imageView1.startAnimation(right);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
