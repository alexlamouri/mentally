package com.pacman.MentAlly.ui.ptsd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.pacman.MentAlly.R;

public class ptsdResultActivity extends AppCompatActivity {

    private TextView mResults;
    private Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptsd_results_2);

        mResults = (TextView) findViewById(R.id.resultP);
        mRetry = (Button) findViewById(R.id.retryP);

        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");

        if (score >= 3){
            mResults.setText("You are likely to be experiencing ptsd");
        } else {
            mResults.setText("You are not likely to be experiencing ptsd");
        }

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ptsd.class);
                startActivity(i);
            }
        });
    }
}
