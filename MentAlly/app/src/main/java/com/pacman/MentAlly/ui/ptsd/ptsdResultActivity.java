package com.pacman.MentAlly.ui.ptsd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

public class ptsdResultActivity extends MainActivity {

    private TextView mResults;
    private Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_ptsd_results_2, contentFrameLayout);

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
