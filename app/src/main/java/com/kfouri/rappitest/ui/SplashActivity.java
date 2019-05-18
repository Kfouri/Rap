package com.kfouri.rappitest.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kfouri.rappitest.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;
    private static final int ROTATION_ANGLE = 18;
    private static final int ROTATION_DURATION = 1000;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);

        mImageView = findViewById(R.id.imageViewSplash);

        setHandler();

        animateLogo();
    }

    private void animateLogo() {

        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_splash);
        mImageView.startAnimation(fadeAnimation);

        mImageView.animate().rotation(ROTATION_ANGLE).setDuration(ROTATION_DURATION);
    }

    void setHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
