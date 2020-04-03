package com.pacman.MentAlly.ui.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    Animation top, bottom;
    ImageView image;
    TextView t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startlogin);

        top = AnimationUtils.loadAnimation(this, R.anim.topanimation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottomanimation);

        image = findViewById(R.id.splashlogo);
        t = findViewById(R.id.sub);

        image.setAnimation(top);
        t.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair<View,String>(image, "logoimage");
                ActivityOptions o = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, p);
                startActivity(i, o.toBundle());
            }

        }, SPLASH_SCREEN);
    }

}
