package com.pacman.MentAlly.ui.breathing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.util.Log;

public class BreathingExerciseActivity extends MainActivity {

    private int[] timers = new int[4];
    private String[] instructionList = new String[]{"Inhale...", "Hold...", "Exhale..."};
    private int instructionCounter = 0;
    TextView instructionText;
    TextView timerText;
    TextView breathText;
    private CountDownTimer timer1, timer2, timer3;
    int counter;
    int breathsCounter = 0;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_breathing_exercise, contentFrameLayout);

        linearLayout = findViewById(R.id.breath_layout2);
        linearLayout.getBackground().setAlpha(50);

        timers[0] = getIntent().getIntExtra("inhale", 5);
        timers[1] = getIntent().getIntExtra("hold", 5);
        timers[2] = getIntent().getIntExtra("exhale", 5);
        timers[3] = getIntent().getIntExtra("breaths", 2);
        Log.i("NUMBER OF BREATHS", " "+timers[3]);

        instructionText = findViewById(R.id.instruction);
        timerText = findViewById(R.id.timer);
        breathText = findViewById(R.id.breathNumber);

        //create timer1
        counter=timers[instructionCounter];
        instructionText.setText(instructionList[instructionCounter]);
        breathText.setText("Breath "+(breathsCounter+1)+" out of "+timers[3]);

        timer1 = new CountDownTimer(timers[instructionCounter]*1000, 1000) {

            //onTick for timer1
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(counter));
                Log.i("counter", "counter is "+counter);
                int val = (counter*10)/timers[instructionCounter];
                counter--;
                //Log.i("counter", "value is "+counter);
            }

            //onFinish for timer1
            public void onFinish() {

                instructionCounter++;
                counter=timers[instructionCounter];
                instructionText.setText(instructionList[instructionCounter]);

                //create timer2
                timer2 = new CountDownTimer(timers[instructionCounter]*1000, 1000) {

                    //onTick for timer2
                    public void onTick(long millisUntilFinished) {
                        timerText.setText(String.valueOf(counter));
                        counter--;
                    }

                    //onFinish for timer2
                    public void onFinish() {

                        instructionCounter++;
                        counter=timers[instructionCounter];
                        instructionText.setText(instructionList[instructionCounter]);

                        //create timer3
                        timer3 = new CountDownTimer(timers[instructionCounter]*1000, 1000) {

                            //onTick for timer3
                            public void onTick(long millisUntilFinished) {
                                timerText.setText(String.valueOf(counter));
                                counter--;
                            }

                            //onFinish for timer3
                            public  void onFinish(){
                                instructionCounter=0;
                                counter=timers[instructionCounter];
                                breathsCounter++;

                                if (breathsCounter < timers[3]) {
                                    breathText.setText("Breath "+(breathsCounter+1)+" out of "+timers[3]);
                                    instructionText.setText(instructionList[instructionCounter]);
                                    timer1.cancel();
                                    timer1.start();
                                } else if (breathsCounter == timers[3]) {
                                    timerText.setText("");
                                    instructionText.setText("Good job ~");
                                }
                            }
                        };
                        timer3.start();
                    }
                };
                timer2.start();
            }
        };

        timer1.start();
    }
}


