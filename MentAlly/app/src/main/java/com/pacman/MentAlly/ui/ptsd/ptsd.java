package com.pacman.MentAlly.ui.ptsd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.pacman.MentAlly.R;

public class ptsd extends AppCompatActivity {


    private Button yes_b;
    private Button no_b;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptsd_1);

        yes_b = (Button) findViewById(R.id.yes);
        no_b = (Button) findViewById(R.id.no);
        score = 0;

        yes_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ptsdActivity.class);
                startActivity(i);
            }
        });

        no_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ptsdResultActivity.class);
                Bundle b = new Bundle();
                b.putInt("score",score);
                i.putExtras(b);
                ptsd.this.finish();
                startActivity(i);
            }
        });

    }
}
