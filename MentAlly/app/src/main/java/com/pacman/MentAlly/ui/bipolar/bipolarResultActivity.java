package com.pacman.MentAlly.ui.bipolar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pacman.MentAlly.R;

public class bipolarResultActivity extends AppCompatActivity {

    private TextView mResult;
    private Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhd_result);

        mResult = (TextView) findViewById(R.id.results);
        mRetry = (Button) findViewById(R.id.redo);

        Bundle b = getIntent().getExtras();
        int points = b.getInt("points");

        if (points >= 0 && points <= 15){
            mResult.setText("Your symptoms are not suggestive of bipolar disorder");
        }

        if (points >= 16 && points <= 24){
            mResult.setText("Your symptoms are suggestive of major depression or bipolar disorder");
        }

        if (points >= 25){
            mResult.setText("Your symptoms are suggestive of bipolar disorder");
        }

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), bipolarActivity.class);
                startActivity(i);
            }
        });
    }
}
