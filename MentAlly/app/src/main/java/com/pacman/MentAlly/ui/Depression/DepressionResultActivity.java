package com.pacman.MentAlly.ui.Depression;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.pacman.MentAlly.R;

public class DepressionResultActivity extends AppCompatActivity {

    private TextView mResult;
    private Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phq9_result);

        mResult = (TextView) findViewById(R.id.results);
        mRetry = (Button) findViewById(R.id.redo);

        Bundle b = getIntent().getExtras();
        int points = b.getInt("points");

        if (points >= 0 && points <= 4){
            mResult.setText("The level of your depression is None to Minimal");
        }
        if (points >= 5 && points <= 9){
            mResult.setText("The level of your depression is Mild");
        }
        if (points >= 10 && points <= 14){
            mResult.setText("The level of your depression is Moderate");
        }
        if (points >= 15 && points <= 19){
            mResult.setText("The level of your depression is Moderately severe");
        }
        if (points >= 20 && points <= 27){
            mResult.setText("The level of your depression is Severe");
        }

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DepressionActivity.class);
                startActivity(i);
            }
        });
    }
}
