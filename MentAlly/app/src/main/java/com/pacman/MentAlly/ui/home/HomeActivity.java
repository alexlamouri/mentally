package com.pacman.MentAlly.ui.home;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.menu.WallpaperFragment;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends MainActivity {
    private int theme;
    Button d;

    private TextView quote;
    private QuotesModel mQuotes = new QuotesModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        d = findViewById(R.id.def);

        WallpaperFragment w = new WallpaperFragment();
        this.theme = w.theme;
        getLayoutInflater().inflate(R.layout.wallpaperlayout, contentFrameLayout);

        quoteAnimation();
        if (this.theme != -1) {
            getLayoutInflater().inflate(R.layout.wallpaperlayout, contentFrameLayout);
            AppCompatImageView view = findViewById(R.id.imageView);
            view.setImageResource(this.theme);
        }
        else{
            getLayoutInflater().inflate(R.layout.defaultwallpaperlayout, contentFrameLayout);

        }



    }
    public void quoteAnimation(){
        quote = (TextView) findViewById(R.id.motivational_quote);
        setQuote();
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        quote.startAnimation(aniFade);
    }

    public void setQuote(){
        Random num = new Random();
        int quoteNum = num.nextInt(mQuotes.getLength());
        quote.setText(mQuotes.getQuote(quoteNum));
    }
}
