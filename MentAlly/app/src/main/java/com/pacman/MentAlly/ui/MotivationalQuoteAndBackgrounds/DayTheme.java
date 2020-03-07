package com.pacman.MentAlly.ui.MotivationalQuoteAndBackgrounds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.pacman.MentAlly.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Random;

public class DayTheme extends AppCompatActivity {

    private QuotesModel mQuotes = new QuotesModel();
    private TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_theme);

        //trial

        quote = (TextView) findViewById(R.id.motivational_quote_day);

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
