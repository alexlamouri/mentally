package com.pacman.MentAlly.ui.ptsd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.pacman.MentAlly.R;

public class ptsdActivity extends AppCompatActivity {

    private ptsdQuestionModel mQues = new ptsdQuestionModel();
    private TextView mQuesView;
    private int mQuesNum=0;
    private int mScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptsd_2);

        mQuesView = (TextView) findViewById(R.id.quesP);
        Button yesB = (Button) findViewById(R.id.yesP);
        Button noB = (Button) findViewById(R.id.noP);

        updateQuestion();

        yesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScore = mScore + 1;
                if (mQuesNum == mQues.getLength()) {
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        noB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuesNum == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });


    }

    private void updateQuestion(){
        mQuesView.setText(mQues.getQuestion(mQuesNum));
        mQuesNum++;
    }

    private void updateResult(){
        Intent i = new Intent(getApplicationContext(), ptsdResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("score",mScore);
        i.putExtras(b);
        ptsdActivity.this.finish();
        startActivity(i);
    }
}
