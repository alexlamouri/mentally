package com.pacman.MentAlly.ui.Mood;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.pacman.MentAlly.R;

import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AddMoodActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String user = FirebaseAuth.getInstance().getUid();
    TextView title;
    TextView subtitle;
    TextView quote;
    TextView selectdate;
    TextView felt;
    TextView because;
    TextView descbox;
    ImageView calendar;

    Button happy;
    Button sad;
    Button cool;
    Button excited;
    Button depressed;
    Button scared;
    Button lovely;
    Button addmood;

    int moodid = MoodActivity.getmoodid()+1;

    String moodtype;


    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmood);


        // get access to all the variables
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        quote = findViewById(R.id.titlequote);

        selectdate = findViewById(R.id.selectdate);
        felt = findViewById(R.id.felt);
        because = findViewById(R.id.because);
        calendar = findViewById(R.id.calendar);
        descbox = findViewById(R.id.descbox);

        happy = findViewById(R.id.happy);
        sad = findViewById(R.id.sad);
        cool = findViewById(R.id.cool);
        excited = findViewById(R.id.excited);
        scared = findViewById(R.id.scared);
        lovely = findViewById(R.id.lovely);
        depressed = findViewById(R.id.depressed);

        addmood = findViewById(R.id.addmood);


        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(
                        AddMoodActivity.this,
                        android.R.style.Theme,
                        dateSetListener,
                        year, month, day);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                selectdate.setText(dayOfMonth + "/" + month + "/" + year);
                Log.d("AddMoodActivity", "date:" + dayOfMonth + "/" + month + "/" + year);
            }
        };


        happy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "happy";

            }
        });
        sad.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "sad";

            }
        });
        excited.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "excited";

            }
        });
        cool.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "cool";

            }
        });
        depressed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "depressed";

            }
        });
        lovely.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "lovely";

            }
        });

        scared.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                moodtype = "scared";

            }
        });



        addmood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.addmood) {
                    createMood(user, selectdate.getText().toString(), descbox.getText().toString(), moodtype, moodid);
                    moodid++;

                    Intent j = new Intent(getApplicationContext(),MoodActivity.class);
                    startActivity(j);
                }
            }
        });

    }


    public void createMood(String UID, String date, String desc, String mood, final Integer moodnum) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", moodnum);
        user.put("date", date);
        user.put("description", desc);
        user.put("moodtype", mood);


        db.collection("users").document(UID).collection("moodlog").document().set(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.w("SUCCESS", "DocumentSnapshot added with ID:");
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AddingMood Failed", "Error adding document", e);
            }
        });
    }
}







