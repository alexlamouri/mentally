package com.pacman.MentAlly.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pacman.MentAlly.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //set all button transparency levels
        Button profile_btn = findViewById(R.id.change_profile_pic);
        profile_btn.getBackground().setAlpha(65);
        Button first_name_btn = findViewById(R.id.edit_first_name);
        first_name_btn.getBackground().setAlpha(65);
        Button last_name_btn = findViewById(R.id.edit_last_name);
        last_name_btn.getBackground().setAlpha(65);
        Button email_btn = findViewById(R.id.edit_email);
        email_btn.getBackground().setAlpha(65);
        Button dob_btn = findViewById(R.id.edit_dob);
        dob_btn.getBackground().setAlpha(65);
        Button country_btn = findViewById(R.id.edit_country);
        country_btn.getBackground().setAlpha(65);
        Button password_btn = findViewById(R.id.edit_password);
        password_btn.getBackground().setAlpha(65);

        //set all textview transparency level
        TextView first_name_txt = findViewById(R.id.firstname_textview);
        first_name_txt.getBackground().setAlpha(75);
        TextView last_name_txt = findViewById(R.id.lastname_textview);
        last_name_txt.getBackground().setAlpha(75);
        TextView email_txt = findViewById(R.id.email_textview);
        email_txt.getBackground().setAlpha(75);
        TextView country_txt = findViewById(R.id.country_textview);
        country_txt.getBackground().setAlpha(75);
        TextView dob_txt = findViewById(R.id.dob_textview);
        dob_txt.getBackground().setAlpha(75);
        TextView password_txt = findViewById(R.id.password_textview);
        password_txt.getBackground().setAlpha(75);
    }
}
