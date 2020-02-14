package com.pacman.MentAlly.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pacman.MentAlly.R;

public class ProfileActivity extends AppCompatActivity {
    Button profile_btn;
    Button first_name_btn;
    Button last_name_btn;
    Button email_btn;
    Button dob_btn;
    Button country_btn;
    Button password_btn;

    TextView first_name_txt;
    TextView last_name_txt;
    TextView email_txt;
    TextView country_txt;
    TextView dob_txt;
    TextView password_txt;



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
    }
}
