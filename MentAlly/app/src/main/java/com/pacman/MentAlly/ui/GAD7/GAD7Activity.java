package com.pacman.MentAlly.ui.GAD7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.pacman.MentAlly.R;

public class GAD7Activity extends AppCompatActivity {

    private GAD7Questions mQues = new GAD7Questions();
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
        setContentView(R.layout.activity_gad7);

        mQuesView = (TextView) findViewById(R.id.question);
        mChoice1 = (Button) findViewById(R.id.choice1);
        mChoice2 = (Button) findViewById(R.id.choice2);
        mChoice3 = (Button) findViewById(R.id.choice3);
        mChoice4 = (Button) findViewById(R.id.choice4);

        updateQuestion();

        mChoice1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (mQuesNumber == mQues.getLength()){
                    Intent i = new Intent(getApplicationContext(),GAD7Results.class);
                    Bundle b = new Bundle();
                    b.putInt("points",mPoint);
                    i.putExtras(b);
                    GAD7Activity.this.finish();
                    startActivity(i);

                } else {
                    updateQuestion();
                }
            }
        });

        mChoice2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 1;
                if (mQuesNumber == mQues.getLength()){
                    Intent i = new Intent(getApplicationContext(),GAD7Results.class);
                    Bundle b = new Bundle();
                    b.putInt("points",mPoint);
                    i.putExtras(b);
                    GAD7Activity.this.finish();
                    startActivity(i);

                } else {
                    updateQuestion();
                }
            }
        });

        mChoice3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 2;
                if (mQuesNumber == mQues.getLength()){
                    Intent i = new Intent(getApplicationContext(),GAD7Results.class);
                    Bundle b = new Bundle();
                    b.putInt("points",mPoint);
                    i.putExtras(b);
                    GAD7Activity.this.finish();
                    startActivity(i);

                } else {
                    updateQuestion();
                }
            }
        });

        mChoice4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPoint = mPoint + 3;
                if (mQuesNumber == mQues.getLength()){
                    Intent i = new Intent(getApplicationContext(),GAD7Results.class);
                    Bundle b = new Bundle();
                    b.putInt("points",mPoint);
                    i.putExtras(b);
                    GAD7Activity.this.finish();
                    startActivity(i);

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

}
