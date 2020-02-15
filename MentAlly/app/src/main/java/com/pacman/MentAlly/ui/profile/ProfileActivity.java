package com.pacman.MentAlly.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pacman.MentAlly.R;

public class ProfileActivity extends AppCompatActivity {
    private Button profile_btn;
    private Button first_name_btn;
    private Button last_name_btn;
    private Button email_btn;
    private Button dob_btn;
    private Button country_btn;
    private Button password_btn;

    private TextView first_name_txt;
    private TextView last_name_txt;
    private TextView email_txt;
    private TextView country_txt;
    private TextView dob_txt;
    private TextView password_txt;

    private FirebaseFirestore myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //set all button transparency levels
        profile_btn = findViewById(R.id.change_profile_pic);
        profile_btn.getBackground().setAlpha(65);
        first_name_btn = findViewById(R.id.edit_first_name);
        first_name_btn.getBackground().setAlpha(65);
        last_name_btn = findViewById(R.id.edit_last_name);
        last_name_btn.getBackground().setAlpha(65);
        email_btn = findViewById(R.id.edit_email);
        email_btn.getBackground().setAlpha(65);
        dob_btn = findViewById(R.id.edit_dob);
        dob_btn.getBackground().setAlpha(65);
        country_btn = findViewById(R.id.edit_country);
        country_btn.getBackground().setAlpha(65);
        password_btn = findViewById(R.id.edit_password);
        password_btn.getBackground().setAlpha(65);

        //set all textview transparency level
        first_name_txt = findViewById(R.id.firstname_textview);
        first_name_txt.getBackground().setAlpha(75);
        last_name_txt = findViewById(R.id.lastname_textview);
        last_name_txt.getBackground().setAlpha(75);
        email_txt = findViewById(R.id.email_textview);
        email_txt.getBackground().setAlpha(75);
        country_txt = findViewById(R.id.country_textview);
        country_txt.getBackground().setAlpha(75);
        dob_txt = findViewById(R.id.dob_textview);
        dob_txt.getBackground().setAlpha(75);
        password_txt = findViewById(R.id.password_textview);
        password_txt.getBackground().setAlpha(75);

//        first_name_txt.setText("Kavya");

        myDatabase = FirebaseFirestore.getInstance();
        Log.d("hi", "hello");

        myDatabase.collection("users").document("KRqVZg5IUrhVmbiyvi0A0YYUUjA3").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot docsnap = task.getResult();
                    String name = docsnap.getString("Name");

                    first_name_txt.setText(name);
                    System.out.println(name);

                } else {
                    //error handling
                    Log.d("oh", "no");
                }
            }
        });
    }
}
