package com.pacman.MentAlly.ui.breathing;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.os.Bundle;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import android.content.Intent;


public class BreathingActivity extends MainActivity {

    private Button startButton;
    private Spinner inhale;
    private Spinner hold;
    private Spinner exhale;
    private Spinner breaths;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_breathing, contentFrameLayout);

        linearLayout = findViewById(R.id.breath_layout);
        linearLayout.getBackground().setAlpha(50);

        //inhale
        inhale = findViewById(R.id.inhale);
        String[] inhaleList = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> inhaleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, inhaleList);
        inhale.setAdapter(inhaleAdapter);
        
        //hold
        hold = findViewById(R.id.hold);
        String[] holdList = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> holdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, holdList);
        hold.setAdapter(holdAdapter);

        //exhale
        exhale = findViewById(R.id.exhale);
        String[] exhaleList = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> exhaleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exhaleList);
        exhale.setAdapter(exhaleAdapter);

        //breaths
        breaths = findViewById(R.id.breaths);
        String[] breathsList = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> breathsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, breathsList);
        breaths.setAdapter(breathsAdapter);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openBreathingExerciseActivity();
            }


        });


    }

    public void openBreathingExerciseActivity() {
        Intent intent = new Intent(this, BreathingExerciseActivity.class);
        intent.putExtra("inhale", Integer.parseInt(inhale.getSelectedItem().toString()));
        intent.putExtra("hold", Integer.parseInt(hold.getSelectedItem().toString()));
        intent.putExtra("exhale", Integer.parseInt(exhale.getSelectedItem().toString()));
        intent.putExtra("breaths", Integer.parseInt(breaths.getSelectedItem().toString()));
        startActivity(intent);
    }

}
