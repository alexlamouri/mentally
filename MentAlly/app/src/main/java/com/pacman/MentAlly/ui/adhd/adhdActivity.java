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

public class adhdActivity extends MainActivity {

    private adhdQuestionModel mQues = new adhdQuestionModel();
    private TextView mQuesView;
    private Button mChoiceA;
    private Button mChoiceB;
    private Button mChoiceC;
    private Button mChoiceD;
    private Button mChoiceE;
    private int mPoint = 0;
    private int mQuesNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_adhd, contentFrameLayout);

        mQuesView = (TextView) findViewById(R.id.questions);
        mChoiceA = (Button) findViewById(R.id.choiceA);
        mChoiceB = (Button) findViewById(R.id.choiceB);
        mChoiceC = (Button) findViewById(R.id.choiceC);
        mChoiceD = (Button) findViewById(R.id.choiceD);
        mChoiceE = (Button) findViewById(R.id.choiceE);

        updateQuestion();

        mChoiceA.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 1;
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceC.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 2;
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceD.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 3;
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceE.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 4;
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

    }

    private void updateQuestion(){
        mQuesView.setText(mQues.getQuestion(mQuesNumber));
        mChoiceA.setText(mQues.getChoiceA(mQuesNumber));
        mChoiceB.setText(mQues.getChoiceB(mQuesNumber));
        mChoiceC.setText(mQues.getChoiceC(mQuesNumber));
        mChoiceD.setText(mQues.getChoiceD(mQuesNumber));
        mChoiceE.setText(mQues.getChoiceE(mQuesNumber));

        mQuesNumber++;
    }

    private void updateResult(){
        Intent i = new Intent(getApplicationContext(), adhdResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("points",mPoint);
        i.putExtras(b);
        com.pacman.MentAlly.ui.adhd.adhdActivity.this.finish();
        startActivity(i);
    }
}
