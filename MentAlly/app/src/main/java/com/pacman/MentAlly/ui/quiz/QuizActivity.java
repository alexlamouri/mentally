package com.pacman.MentAlly.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.adhd.adhdActivity;
import com.pacman.MentAlly.ui.anxiety.anxietyActivity;
import com.pacman.MentAlly.ui.bipolar.bipolarActivity;
import com.pacman.MentAlly.ui.depression.depressionActivity;
import com.pacman.MentAlly.ui.ocd.ocdActivity;
import com.pacman.MentAlly.ui.ptsd.ptsdActivity;
import com.pacman.MentAlly.ui.home.MainActivity;

public class QuizActivity extends MainActivity {

    Button adhd;
    Button anxiety;
    Button depression;
    Button ocd;
    Button ptsd;
    Button bipolar;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_quiz, contentFrameLayout);

        linearLayout = findViewById(R.id.quiz_layout);
        linearLayout.getBackground().setAlpha(80);

        adhd = findViewById(R.id.button_adhd);
        adhd.getBackground().setAlpha(180);

        anxiety = findViewById(R.id.button_anxiety);
        anxiety.getBackground().setAlpha(180);

        depression = findViewById(R.id.button_depression);
        depression.getBackground().setAlpha(180);

        ocd = findViewById(R.id.button_ocd);
        ocd.getBackground().setAlpha(180);

        ptsd = findViewById(R.id.button_ptsd);
        ptsd.getBackground().setAlpha(180);

        bipolar = findViewById(R.id.button_bipolar);
        bipolar.getBackground().setAlpha(180);
    }

    public void adhd_test(View view){
        Intent i_adhd = new Intent(this, adhdActivity.class);
        startActivity(i_adhd);
    }

    public void anxiety_test(View view){
        Intent i_anxiety = new Intent(this, anxietyActivity.class);
        startActivity(i_anxiety);
    }

    public void bipolar_test(View view){
        Intent i_bipolar = new Intent(this, bipolarActivity.class);
        startActivity(i_bipolar);
    }

    public void depression_test(View view){
        Intent i_dep = new Intent(this, depressionActivity.class);
        startActivity(i_dep);
    }

    public void ocd_test(View view){
        Intent i_ocd = new Intent(this, ocdActivity.class);
        startActivity(i_ocd);
    }

    public void ptsd_test(View view){
        Intent i_ptsd = new Intent(this, ptsdActivity.class);
        startActivity(i_ptsd);
    }

}
