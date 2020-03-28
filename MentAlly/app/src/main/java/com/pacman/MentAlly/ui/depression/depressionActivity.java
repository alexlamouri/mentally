package com.pacman.MentAlly.ui.depression;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

public class depressionActivity extends MainActivity {

    private depressionQuestionModel mQues = new depressionQuestionModel();
    private TextView mQuesView;
    private Button mChoice1;
    private Button mChoice2;
    private Button mChoice3;
    private Button mChoice4;
    private int mPoint = 0;
    private int mQuesNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_depression, contentFrameLayout);

        mQuesView = (TextView) findViewById(R.id.questions);
        mChoice1 = (Button) findViewById(R.id.choiceA);
        mChoice2 = (Button) findViewById(R.id.choiceB);
        mChoice3 = (Button) findViewById(R.id.choiceC);
        mChoice4 = (Button) findViewById(R.id.choiceD);

        updateQuestion();

        mChoice1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoice2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 1;
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoice3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 2;
                if (mQuesNumber == mQues.getLength()){
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoice4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 3;
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
        mChoice1.setText(mQues.getChoice1(mQuesNumber));
        mChoice2.setText(mQues.getChoice2(mQuesNumber));
        mChoice3.setText(mQues.getChoice3(mQuesNumber));
        mChoice4.setText(mQues.getChoice4(mQuesNumber));

        mQuesNumber++;
    }

    private void updateResult(){
        Intent i = new Intent(getApplicationContext(), depressionResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("points",mPoint);
        i.putExtras(b);
        depressionActivity.this.finish();
        startActivity(i);
    }
}
