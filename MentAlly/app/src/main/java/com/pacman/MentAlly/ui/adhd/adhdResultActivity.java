package com.pacman.MentAlly.ui.adhd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

public class adhdResultActivity extends MainActivity {

    private TextView mResult;
    private Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_adhd_result, contentFrameLayout);

        mResult = (TextView) findViewById(R.id.results);
        mRetry = (Button) findViewById(R.id.redo);

        Bundle b = getIntent().getExtras();
        int points = b.getInt("points");

        if (points >= 0 && points < 4){
            mResult.setText("Your symptoms are not suggestive of ADHD");
        }
        if (points >= 4){
            mResult.setText("Your symptoms are suggestive of ADHD");
        }

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), adhdActivity.class);
                startActivity(i);
            }
        });
    }
}
